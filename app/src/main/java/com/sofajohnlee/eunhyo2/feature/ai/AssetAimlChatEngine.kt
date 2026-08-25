package com.sofajohnlee.eunhyo2.feature.ai

import android.content.Context
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Node

/**
 * Lightweight AIML asset reader used as the safe default adapter.
 *
 * It supports exact-match categories plus Program AB substitution tables used by
 * normal/person/person2. Complex wildcard, SRAI, condition and predicate semantics
 * remain behind the ChatEngine boundary so they can be expanded independently.
 */
class AssetAimlChatEngine(
    context: Context,
    private val fallback: ChatEngine = LocalRuleChatEngine(),
) : ChatEngine {
    private val appContext = context.applicationContext
    private val normalTable = loadSubstitution("Hari/config/normal.txt")
    private val personTable = loadSubstitution("Hari/config/person.txt")
    private val person2Table = loadSubstitution("Hari/config/person2.txt")
    private val responses: Map<String, String> = load()

    override fun respond(message: String): String {
        val key = normalize(message)
        return responses[key]?.takeIf { it.isNotBlank() } ?: fallback.respond(message)
    }

    private fun load(): Map<String, String> {
        val result = linkedMapOf<String, String>()
        val root = "Hari/aiml"
        val files = appContext.assets.list(root).orEmpty()
            .filter { it.endsWith(".aiml", ignoreCase = true) }

        for (name in files) {
            runCatching {
                appContext.assets.open("$root/$name").use { input ->
                    val factory = DocumentBuilderFactory.newInstance().apply {
                        isNamespaceAware = false
                        setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
                    }
                    val document = factory.newDocumentBuilder().parse(input)
                    val categories = document.getElementsByTagName("category")
                    for (index in 0 until categories.length) {
                        val node = categories.item(index)
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
                            // Wildcard/SRAI categories are intentionally left to the next engine layer.
                            if (!normalizedPattern.contains('*') && !normalizedPattern.contains('_')) {
                                val rendered = renderTemplate(templateNode!!).trim()
                                if (rendered.isNotBlank()) {
                                    result.putIfAbsent(normalizedPattern, rendered)
                                }
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    private fun renderTemplate(node: Node): String {
        val output = StringBuilder()
        val children = node.childNodes
        for (index in 0 until children.length) {
            val child = children.item(index)
            when (child.nodeType) {
                Node.TEXT_NODE, Node.CDATA_SECTION_NODE -> output.append(child.nodeValue.orEmpty())
                Node.ELEMENT_NODE -> when (child.nodeName.lowercase(Locale.US)) {
                    "think" -> Unit
                    "person" -> output.append(personTable.apply(child.textContent.orEmpty()))
                    "person2" -> output.append(person2Table.apply(child.textContent.orEmpty()))
                    "br" -> output.append('\n')
                    else -> output.append(renderTemplate(child))
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
}
