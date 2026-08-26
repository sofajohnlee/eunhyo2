package com.sofajohnlee.eunhyo2.feature.ai

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Locale

/** Session-scoped AIML predicate state with Program AB-style defaults. */
class AimlPredicateStore private constructor(
    private val values: MutableMap<String, String>,
) {
    fun get(name: String): String = values[normalizeName(name)].orEmpty()

    fun set(name: String, value: String): String {
        val cleaned = value.trim()
        values[normalizeName(name)] = cleaned
        return cleaned
    }

    fun topic(): String = get("topic").ifBlank { "unknown" }

    companion object {
        fun parse(input: InputStream): AimlPredicateStore {
            val values = linkedMapOf<String, String>()
            BufferedReader(InputStreamReader(input, Charsets.UTF_8)).useLines { lines ->
                lines.forEach { raw ->
                    val line = raw.trim()
                    if (line.isBlank() || line.startsWith("#")) return@forEach
                    val separator = line.indexOf(':')
                    if (separator <= 0) return@forEach
                    val name = line.substring(0, separator).trim().lowercase(Locale.US)
                    val value = line.substring(separator + 1).trim()
                    values[name] = value
                }
            }
            return AimlPredicateStore(values)
        }

        fun empty(): AimlPredicateStore = AimlPredicateStore(linkedMapOf())

        private fun normalizeName(name: String): String = name.trim().lowercase(Locale.US)
    }

    private fun normalizeName(name: String): String = name.trim().lowercase(Locale.US)
}
