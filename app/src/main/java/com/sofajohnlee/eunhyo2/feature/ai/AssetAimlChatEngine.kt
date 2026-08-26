package com.sofajohnlee.eunhyo2.feature.ai

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Node

/**
 * Asset-backed AIML adapter for the modernized app.
 *
 * Supported semantics include exact/wildcard patterns, star capture,
 * normal/person/person2 substitutions, SRAI, random lists, session predicates,
 * set/get, simple condition, that/topic matching, profile tags and a bounded
 * in-memory subset of learnf/eval runtime learning.
 */
class AssetAimlChatEngine(
    context: Context,
    private val fallback: ChatEngine = LocalRuleChatEngine(),
) : ChatEngine {
    private data class Category(
        val pattern: String,
        val that: String?,
        val topic: String?,
        val template: Node,
    )

    private val appContext = context.applicationContext
    private val normalTable = loadSubstitution("Hari/config/normal.txt")
    private val personTable = loadSubstitution("Hari/config/person.txt")
    private val person2Table = loadSubstitution("Hari/config/person2.txt")
    private val predicates = loadPredicates()
    private val botProperties = loadProperties()
    private val categories: List<Category> = load()
    private val learnedCategories = mutableListOf<Category>()
    private val inputHistory = ArrayDeque<String>()
    private val responseHistory = ArrayDeque<String>()
    private var lastResponse: String = ""

    override fun respond(message: String): String {
        inputHistory.addFirst(message)
        while (inputHistory.size > HISTORY_LIMIT) inputHistory.removeLast()

        val response = respondInternal(message, depth = 0).ifBlank { fallback.respond(message) }
        lastResponse = response
        responseHistory.addFirst(response)
        while (responseHistory.size > HISTORY_LIMIT) responseHistory.removeLast()
        return response
    }

    private fun respondInternal(message: String, depth: Int): String {
        if (depth > MAX_RECURSION_DEPTH) return fallback.respond(message)
        val key = normalize(message)
        val thatKey = normalize(lastResponse)
        val topicKey = normalize(predicates.topic())
        val allCategories = learnedCategories + categories

        val exact = allCategories.firstOrNull { category ->
            !category.pattern.contains('*') &&
                !category.pattern.contains('_') &&
                category.pattern == key &&
                contextMatches(category, thatKey, topicKey)
        }
        if (exact != null) {
            return renderTemplate(exact.template, emptyList(), depth)
        }

        for (category in allCategories) {
            if (!category.pattern.contains('*') && !category.pattern.contains('_')) continue
            if (!contextMatches(category, thatKey, topicKey)) continue
            val match = AimlPatternMatcher.match(category.pattern, key) ?: continue
            return renderTemplate(category.template, match.stars, depth)
        }
        return fallback.respond(message)
    }

    private fun contextMatches(category: Category, thatKey: String, topicKey: String): Boolean {
        val thatMatches = category.that?.let { pattern -> AimlPatternMatcher.match(pattern, thatKey) != null } ?: true
        val topicMatches = category.topic?.let { pattern -> AimlPatternMatcher.match(pattern, topicKey) != null } ?: true
        return thatMatches && topicMatches
    }

    private fun load(): List<Category> {
        val result = mutableListOf<Category>()
        val root = "Hari/aiml"
        val files = appContext.assets.list(root).orEmpty()
            .filter { it.endsWith(".aiml", ignoreCase = true) }

        for (name in files) {
            runCatching {
                appContext.assets.open("$root/$name").use { input ->
                    val factory = DocumentBuilderFactory.newInstance().apply {
                        isNamespaceAware = false
                        setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
                        setFeature("http://xml.org/sax/features/external-general-entities", false)
                        setFeature("http://xml.org/sax/features/external-parameter-entities", false)
                    }
                    val document = factory.newDocumentBuilder().parse(input)
                    val nodes = document.getElementsByTagName("category")
                    for (index in 0 until nodes.length) {
                        val node = nodes.item(index)
                        val parent = node.parentNode ?: continue
                        val parentName = parent.nodeName.lowercase(Locale.US)
                        if (parentName != "aiml" && parentName != "topic") continue

                        val parsed = parseCategory(node) ?: continue
                        result += parsed
                    }
                }
            }
        }
        return result
    }

    private fun parseCategory(node: Node, dynamicStars: List<String> = emptyList(), depth: Int = 0): Category? {
        val children = node.childNodes
        var pattern: String? = null
        var that: String? = null
        var templateNode: Node? = null
        for (childIndex in 0 until children.length) {
            val child = children.item(childIndex)
            when (child.nodeName.lowercase(Locale.US)) {
                "pattern" -> pattern = if (dynamicStars.isEmpty()) child.textContent else renderTemplate(child, dynamicStars, depth)
                "that" -> that = if (dynamicStars.isEmpty()) child.textContent else renderTemplate(child, dynamicStars, depth)
                "template" -> templateNode = child
            }
        }
        val topicNode = node.parentNode?.takeIf { it.nodeName.equals("topic", true) }
        val topic = topicNode?.attributes?.getNamedItem("name")?.nodeValue
        val normalizedPattern = pattern?.let(::normalize)
        if (normalizedPattern.isNullOrBlank() || templateNode == null) return null
        return Category(
            pattern = normalizedPattern,
            that = that?.let(::normalize)?.takeIf { it.isNotBlank() },
            topic = topic?.let(::normalize)?.takeIf { it.isNotBlank() },
            template = templateNode,
        )
    }

    private fun renderTemplate(node: Node, stars: List<String>, depth: Int): String {
        val output = StringBuilder()
        val children = node.childNodes
        for (index in 0 until children.length) {
            val child = children.item(index)
            when (child.nodeType) {
                Node.TEXT_NODE, Node.CDATA_SECTION_NODE -> output.append(child.nodeValue.orEmpty())
                Node.ELEMENT_NODE -> when (child.nodeName.lowercase(Locale.US)) {
                    "think" -> {
                        renderTemplate(child, stars, depth)
                        Unit
                    }
                    "star" -> {
                        val starIndex = child.attributes?.getNamedItem("index")?.nodeValue?.toIntOrNull() ?: 1
                        output.append(stars.getOrNull(starIndex - 1).orEmpty())
                    }
                    "person" -> output.append(personTable.apply(renderTemplate(child, stars, depth)))
                    "person2" -> output.append(person2Table.apply(renderTemplate(child, stars, depth)))
                    "set" -> {
                        val name = predicateName(child)
                        val value = renderTemplate(child, stars, depth)
                        output.append(predicates.set(name, value))
                    }
                    "get" -> output.append(predicates.get(predicateName(child)))
                    "condition" -> output.append(renderCondition(child, stars, depth))
                    "srai" -> {
                        val redirected = renderTemplate(child, stars, depth).trim()
                        if (redirected.isNotBlank()) output.append(respondInternal(redirected, depth + 1))
                    }
                    "random" -> {
                        val options = (0 until child.childNodes.length)
                            .map { child.childNodes.item(it) }
                            .filter { it.nodeType == Node.ELEMENT_NODE && it.nodeName.equals("li", true) }
                        if (options.isNotEmpty()) output.append(renderTemplate(options.random(), stars, depth))
                    }
                    "uppercase" -> output.append(renderTemplate(child, stars, depth).uppercase(Locale.US))
                    "lowercase" -> output.append(renderTemplate(child, stars, depth).lowercase(Locale.US))
                    "formal" -> output.append(
                        renderTemplate(child, stars, depth)
                            .split(Regex("\\s+"))
                            .joinToString(" ") { token -> token.lowercase(Locale.US).replaceFirstChar { it.uppercase(Locale.US) } }
                    )
                    "sentence" -> {
                        val value = renderTemplate(child, stars, depth).lowercase(Locale.US)
                        output.append(value.replaceFirstChar { it.uppercase(Locale.US) })
                    }
                    "bot" -> {
                        val name = child.attributes?.getNamedItem("name")?.nodeValue.orEmpty().lowercase(Locale.US)
                        output.append(botProperties[name].orEmpty())
                    }
                    "size" -> output.append(categories.size + learnedCategories.size)
                    "vocabulary" -> output.append(vocabularySize())
                    "date" -> output.append(renderDate(child))
                    "eval" -> output.append(renderTemplate(child, stars, depth))
                    "normalize" -> output.append(normalize(renderTemplate(child, stars, depth)))
                    "input" -> {
                        val historyIndex = child.attributes?.getNamedItem("index")?.nodeValue?.toIntOrNull() ?: 1
                        output.append(inputHistory.elementAtOrNull(historyIndex - 1).orEmpty())
                    }
                    "response" -> {
                        val historyIndex = child.attributes?.getNamedItem("index")?.nodeValue?.toIntOrNull() ?: 1
                        output.append(responseHistory.elementAtOrNull(historyIndex - 1).orEmpty())
                    }
                    "learnf", "learn" -> learnFromNode(child, stars, depth)
                    "br" -> output.append('\n')
                    else -> output.append(renderTemplate(child, stars, depth))
                }
            }
        }
        return output.toString().replace(Regex("[ \\t]+"), " ").trim()
    }

    private fun learnFromNode(node: Node, stars: List<String>, depth: Int) {
        val categoryNodes = (0 until node.childNodes.length)
            .map { node.childNodes.item(it) }
            .filter { it.nodeType == Node.ELEMENT_NODE && it.nodeName.equals("category", true) }
        for (categoryNode in categoryNodes) {
            val category = parseCategory(categoryNode, stars, depth) ?: continue
            learnedCategories.removeAll {
                it.pattern == category.pattern && it.that == category.that && it.topic == category.topic
            }
            learnedCategories.add(0, category)
            if (learnedCategories.size > MAX_LEARNED_CATEGORIES) {
                learnedCategories.removeAt(learnedCategories.lastIndex)
            }
        }
    }

    private fun renderCondition(node: Node, stars: List<String>, depth: Int): String {
        val name = predicateName(node)
        val directValue = node.attributes?.getNamedItem("value")?.nodeValue
        if (name.isNotBlank() && directValue != null) {
            return if (predicates.get(name).equals(directValue, ignoreCase = true)) {
                renderTemplate(node, stars, depth)
            } else ""
        }

        val predicateValue = predicates.get(name)
        var defaultNode: Node? = null
        for (index in 0 until node.childNodes.length) {
            val child = node.childNodes.item(index)
            if (child.nodeType != Node.ELEMENT_NODE || !child.nodeName.equals("li", true)) continue
            val value = child.attributes?.getNamedItem("value")?.nodeValue
            if (value == null) {
                defaultNode = child
            } else if (predicateValue.equals(value, ignoreCase = true)) {
                return renderTemplate(child, stars, depth)
            }
        }
        return defaultNode?.let { renderTemplate(it, stars, depth) }.orEmpty()
    }

    private fun predicateName(node: Node): String =
        node.attributes?.getNamedItem("name")?.nodeValue
            ?: node.attributes?.getNamedItem("var")?.nodeValue
            ?: ""

    private fun renderDate(node: Node): String {
        val javaFormat = node.attributes?.getNamedItem("jformat")?.nodeValue
            ?: node.attributes?.getNamedItem("format")?.nodeValue
            ?: "yyyy-MM-dd HH:mm"
        val safeFormat = when {
            node.attributes?.getNamedItem("jformat") != null -> javaFormat
            else -> javaFormat
                .replace("%Y", "yyyy")
                .replace("%B", "MMMM")
                .replace("%d", "dd")
                .replace("%A", "EEEE")
                .replace("%I", "hh")
                .replace("%M", "mm")
                .replace("%p", "a")
        }
        return runCatching { SimpleDateFormat(safeFormat, Locale.getDefault()).format(Date()) }
            .getOrDefault(Date().toString())
    }

    private fun vocabularySize(): Int = (categories + learnedCategories)
        .flatMap { it.pattern.split(' ') }
        .filter { it.isNotBlank() && it != "*" && it != "_" }
        .toSet()
        .size

    private fun loadProperties(): Map<String, String> = runCatching {
        appContext.assets.open("Hari/config/properties.txt").bufferedReader().useLines { lines ->
            lines.mapNotNull { raw ->
                val line = raw.trim()
                if (line.isBlank() || line.startsWith("#")) return@mapNotNull null
                val separator = line.indexOf(':')
                if (separator <= 0) return@mapNotNull null
                line.substring(0, separator).trim().lowercase(Locale.US) to line.substring(separator + 1).trim()
            }.toMap()
        }
    }.getOrDefault(emptyMap())

    private fun loadPredicates(): AimlPredicateStore = runCatching {
        appContext.assets.open("Hari/config/predicates.txt").use(AimlPredicateStore::parse)
    }.getOrDefault(AimlPredicateStore.empty())

    private fun loadSubstitution(path: String): AimlSubstitutionTable =
        runCatching {
            appContext.assets.open(path).use(AimlSubstitutionTable::parse)
        }.getOrDefault(AimlSubstitutionTable.EMPTY)

    private fun normalize(value: String): String = normalTable.apply(value)
        .trim()
        .uppercase(Locale.US)
        .replace(Regex("\\s+"), " ")

    companion object {
        private const val MAX_RECURSION_DEPTH = 8
        private const val MAX_LEARNED_CATEGORIES = 256
        private const val HISTORY_LIMIT = 10
    }
}
