package com.sofajohnlee.eunhyo2.feature.ai

import android.content.Context
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Node

/**
 * Lightweight AIML asset reader used as the safe default adapter.
 *
 * Supported semantics now include exact/wildcard patterns, star capture,
 * normal/person/person2 substitutions, simple SRAI recursion and random lists.
 * Stateful predicates/condition/that/topic semantics remain isolated for later expansion.
 */
class AssetAimlChatEngine(
    context: Context,
    private val fallback: ChatEngine = LocalRuleChatEngine(),
) : ChatEngine {
    private data class Category(val pattern: String, val template: Node)

    private val appContext = context.applicationContext
    private val normalTable = loadSubstitution("Hari/config/normal.txt")
    private val personTable = loadSubstitution("Hari/config/person.txt")
    private val person2Table = loadSubstitution("Hari/config/person2.txt")
    private val categories: List<Category> = load()
    private val exactCategories: Map<String, Category> = categories
        .filterNot { it.pattern.contains('*') || it.pattern.contains('_') }
        .associateBy { it.pattern }
    private val wildcardCategories: List<Category> = categories
        .filter { it.pattern.contains('*') || it.pattern.contains('_') }

    override fun respond(message: String): String = respondInternal(message, depth = 0)

    private fun respondInternal(message: String, depth: Int): String {
        if (depth > MAX_RECURSION_DEPTH) return fallback.respond(message)
        val key = normalize(message)

        exactCategories[key]?.let { category ->
            return renderTemplate(category.template, emptyList(), depth).ifBlank { fallback.respond(message) }
        }

        for (category in wildcardCategories) {
            val match = AimlPatternMatcher.match(category.pattern, key) ?: continue
            return renderTemplate(category.template, match.stars, depth).ifBlank { fallback.respond(message) }
        }
        return fallback.respond(message)
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
                        val children = node.childNodes
                        var pattern: String? = null
                        var templateNode: Node? = null
                        for (childIndex in 0 until children.length) {
                            val child = children.item(childIndex)
                            when (child.nodeName.lowercase(Locale.US)) {
                                "pattern" -> pattern = child.textContent
                                "template" -> templateNode = child
                            }
                        }
                        val normalizedPattern = pattern?.let(::normalize)
                        if (!normalizedPattern.isNullOrBlank() && templateNode != null) {
                            result += Category(normalizedPattern, templateNode!!)
                        }
                    }
                }
            }
        }
        return result
    }

    private fun renderTemplate(node: Node, stars: List<String>, depth: Int): String {
        val output = StringBuilder()
        val children = node.childNodes
        for (index in 0 until children.length) {
            val child = children.item(index)
            when (child.nodeType) {
                Node.TEXT_NODE, Node.CDATA_SECTION_NODE -> output.append(child.nodeValue.orEmpty())
                Node.ELEMENT_NODE -> when (child.nodeName.lowercase(Locale.US)) {
                    "think" -> Unit
                    "star" -> {
                        val starIndex = child.attributes?.getNamedItem("index")?.nodeValue?.toIntOrNull() ?: 1
                        output.append(stars.getOrNull(starIndex - 1).orEmpty())
                    }
                    "person" -> output.append(personTable.apply(renderTemplate(child, stars, depth)))
                    "person2" -> output.append(person2Table.apply(renderTemplate(child, stars, depth)))
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
                    "br" -> output.append('\n')
                    else -> output.append(renderTemplate(child, stars, depth))
                }
            }
        }
        return output.toString().replace(Regex("[ \\t]+"), " ").trim()
    }

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
    }
}
