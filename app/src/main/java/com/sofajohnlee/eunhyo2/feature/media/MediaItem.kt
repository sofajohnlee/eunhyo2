package com.sofajohnlee.eunhyo2.feature.media

data class MediaItem(
    val title: String,
    val videoId: String,
)

object MediaCatalog {
    val courageVideos = listOf(
        MediaItem("Cactus", "rlwfd1ZaDJ4"),
        MediaItem("Drawing", "rw_qPB7PV8k"),
        MediaItem("Dream Song", "kGsUHbq3yUY"),
        MediaItem("세계의 인사", "GpTR1wF4M6k"),
        MediaItem("안나 그리기", "Vb6-xmXVpU0"),
        MediaItem("친구들과 놀기", "vP5Be3Aq6ls"),
        MediaItem("울라프 그리기", "WQk6dJ7I59A"),
        MediaItem("크리스토프 그리기", "Ab8meURjPyw"),
        MediaItem("스벤 그리기", "JEgbzY2inqo"),
        MediaItem("Flynn Rider 그리기", "vTBZCtct6Rw"),
    )
}
