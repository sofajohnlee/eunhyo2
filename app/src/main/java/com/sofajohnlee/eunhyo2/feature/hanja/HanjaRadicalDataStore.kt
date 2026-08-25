package com.sofajohnlee.eunhyo2.feature.hanja

import android.content.Context

class HanjaRadicalDataStore(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(groups: List<RadicalGroup>) {
        val text = buildString {
            groups.forEach { group ->
                group.entries.forEach { entry ->
                    append(group.id).append('\t')
                    append(group.title.replace("\t", " ")).append('\t')
                    append(entry.character.replace("\t", " ")).append('\t')
                    append(entry.reading.replace("\t", " ")).append('\t')
                    append(entry.meaning.replace("\t", " ")).append('\n')
                }
            }
        }
        prefs.edit().putString(KEY_DATA, text).apply()
    }

    fun load(): List<RadicalGroup> {
        val text = prefs.getString(KEY_DATA, null).orEmpty()
        if (text.isBlank()) return emptyList()
        return text.lineSequence()
            .mapNotNull { line ->
                val columns = line.split('\t')
                if (columns.size < 5) null
                else StoredEntry(
                    groupId = columns[0].toIntOrNull() ?: return@mapNotNull null,
                    title = columns[1],
                    entry = HanjaEntry(columns[2], columns[3], columns[4]),
                )
            }
            .groupBy { it.groupId }
            .toSortedMap()
            .map { (id, values) -> RadicalGroup(id, values.first().title, values.map { it.entry }) }
    }

    fun clear() {
        prefs.edit().remove(KEY_DATA).apply()
    }

    private data class StoredEntry(val groupId: Int, val title: String, val entry: HanjaEntry)

    companion object {
        private const val PREFS_NAME = "hanja_radical_data"
        private const val KEY_DATA = "groups"
    }
}
