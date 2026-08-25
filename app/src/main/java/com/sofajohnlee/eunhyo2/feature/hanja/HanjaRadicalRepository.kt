package com.sofajohnlee.eunhyo2.feature.hanja

/**
 * Modern replacement for the legacy hbusu1.csv ... hbusu14.csv absolute-file flow.
 * Built-in starter data keeps the feature usable without external storage; additional
 * legacy CSV data can later be imported through the same HanjaRepository parser.
 */
object HanjaRadicalRepository {
    val groups: List<RadicalGroup> = listOf(
        RadicalGroup(1, "사람·몸", listOf(
            HanjaEntry("人", "인", "사람"), HanjaEntry("口", "구", "입"),
            HanjaEntry("心", "심", "마음"), HanjaEntry("手", "수", "손")
        )),
        RadicalGroup(2, "자연", listOf(
            HanjaEntry("日", "일", "해"), HanjaEntry("月", "월", "달"),
            HanjaEntry("山", "산", "산"), HanjaEntry("水", "수", "물")
        )),
        RadicalGroup(3, "생활", listOf(
            HanjaEntry("木", "목", "나무"), HanjaEntry("火", "화", "불"),
            HanjaEntry("田", "전", "밭"), HanjaEntry("門", "문", "문")
        )),
        RadicalGroup(4, "방향·수", listOf(
            HanjaEntry("上", "상", "위"), HanjaEntry("下", "하", "아래"),
            HanjaEntry("一", "일", "하나"), HanjaEntry("十", "십", "열")
        )),
    )

    fun group(id: Int): RadicalGroup = groups.firstOrNull { it.id == id } ?: groups.first()
}
