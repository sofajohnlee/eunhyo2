package com.sofajohnlee.eunhyo2.feature.hanja

import java.io.InputStream

class HanjaRadicalImporter(
    private val hanjaRepository: HanjaRepository = HanjaRepository(),
) {
    data class Source(val displayName: String, val input: InputStream)

    fun import(sources: List<Source>): List<RadicalGroup> = sources
        .mapIndexedNotNull { index, source ->
            val entries = source.input.use(hanjaRepository::parseCsv)
            if (entries.isEmpty()) return@mapIndexedNotNull null
            val parsedNumber = Regex("hbusu(\\d+)", RegexOption.IGNORE_CASE)
                .find(source.displayName)
                ?.groupValues
                ?.getOrNull(1)
                ?.toIntOrNull()
            val id = parsedNumber ?: (index + 1)
            RadicalGroup(id, "부수 $id", entries)
        }
        .sortedBy { it.id }
}
