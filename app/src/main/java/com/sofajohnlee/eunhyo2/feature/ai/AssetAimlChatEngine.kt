package com.sofajohnlee.eunhyo2.feature.ai

import android.content.Context
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Lightweight AIML asset reader used as the safe default adapter.
 *
 * It intentionally supports only simple exact-match <pattern>/<template>
 * categories. The adapter boundary allows a full AIML engine to be plugged in
 * later without coupling the Activity/ViewModel to an unmaintained JAR.
 */
class AssetAimlChatEngine(
    context: Context,
    private val fallback: ChatEngine = LocalRuleChatEngine(),
) : ChatEngine {
    private val responses: Map<String, String> = load(context.applicationContext)

    override fun respond(message: String): String {
        val key = normalize(message)
        return responses[key]?.takeIf { it.isNotBlank() } ?: fallback.respond(message)
    }

    private fun load(context: Context): Map<String, String> {
        val result = linkedMapOf<String, String>()
        val root = "Hari/aiml"
        val files = context.assets.list(root).orEmpty()
            .filter { it.endsWith(".aiml", ignoreCase = true) }

        for (name in files) {
            runCatching {
                context.assets.open("$root/$name").use { input ->
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
                        var template: String? = null
                        for (childIndex in 0 until children.length) {
                            val child = children.item(childIndex)
                            when (child.nodeName.lowercase(Locale.US)) {
                                "pattern" -> pattern = child.textContent
                                "template" -> template = child.textContent
                            }
                        }
                        val normalizedPattern = pattern?.let(::normalize)
                        if (!normalizedPattern.isNullOrBlank() && !template.isNullOrBlank()) {
                            // Full Program AB wildcard/SRAI semantics are deliberately not emulated here.
                            if (!normalizedPattern.contains('*') && !normalizedPattern.contains('_')) {
                                result.putIfAbsent(normalizedPattern, template.trim())
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    private fun normalize(value: String): String = value
        .trim()
        .uppercase(Locale.US)
        .replace(Regex("\\s+"), " ")
}
