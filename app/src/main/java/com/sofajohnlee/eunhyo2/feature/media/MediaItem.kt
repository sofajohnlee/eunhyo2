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
        MediaItem("친구들과 놀기", "vP5Be3Aq6ls"),
        MediaItem("안나 그리기", "Vb6-xmXVpU0"),
        MediaItem("울라프 그리기", "WQk6dJ7I59A"),
        MediaItem("크리스토프 그리기", "Ab8meURjPyw"),
        MediaItem("스벤 그리기", "JEgbzY2inqo"),
        MediaItem("Flynn Rider 그리기", "vTBZCtct6Rw"),
        MediaItem("울면 안 돼", "mxtL7LILVyQ"),
        MediaItem("중국어 배우기", "QtaBQvv8u3c"),
        MediaItem("일본어 배우기", "PQrg_4_BKBQ"),
        MediaItem("스페인어 배우기", "fmWjDL2Spvw"),
        MediaItem("좋은 친구란", "avHdx18pi_U"),
        MediaItem("자신감 갖기", "id6N8rWdW5U"),
        MediaItem("선인장 꽃", "I8W4LyIXINE"),
        MediaItem("인어공주", "n5DYFGE8UZ8"),
        MediaItem("Cat on Cactus", "pazgV96q9P8"),
        MediaItem("Santa Claus Is Coming to Town", "HWv72L4wgCc"),
        MediaItem("We Wish You a Merry Christmas", "g-OF7KGyDis"),
        MediaItem("Christmas Video", "0jHsq36_NTU"),
    )

    val christmasVideos = listOf(
        MediaItem("징글벨", "3CWJNqyub3o"),
        MediaItem("기쁘다 구주 오셨네", "30OaM6b48k8"),
        MediaItem("고요한 밤", "nEH7_2c644Q"),
        MediaItem("노엘", "D5uud2fjtoo"),
        MediaItem("눈사람", "KhjfskHJf1o"),
        MediaItem("We Wish You a Merry Christmas", "aIcggASU7jk"),
        MediaItem("창밖을 보라", "o-YK-QavJe8"),
        MediaItem("루돌프", "0byH9h1ClBY"),
        MediaItem("Santa Claus Is Coming to Town", "HWv72L4wgCc"),
        MediaItem("White Christmas", "UioEvXY7xoM"),
        MediaItem("징글벨 2", "Cyhj18pgiXQ"),
        MediaItem("기쁘다 구주 오셨네 2", "ucX67QPT8EA"),
        MediaItem("고요한 밤 2", "Nse047Y9tzc"),
        MediaItem("노엘 2", "gJgWKOD7JBk"),
        MediaItem("창밖을 보라 2", "PnLlZCakfgg"),
        MediaItem("루돌프 2", "jat_kPWEoAU"),
        MediaItem("울면 안 돼 2", "vdyTWHY5Eso"),
        MediaItem("We Wish You a Merry Christmas 2", "Lg8cI0B5xF4"),
        MediaItem("실버벨", "FI0kKtySvKE"),
        MediaItem("White Christmas 2", "YOm3nq4iohg"),
        MediaItem("탄일종", "R5Q2bmIMIjo"),
    )

    val additionalVideos = listOf(
        MediaItem("Wreck-It Ralph", "3posPWuA9Ss"),
        MediaItem("국어 문장 영상", "NG2aBtqazkY"),
        MediaItem("Marie / Snow 영상", "wKF-nyaBDiw"),
    )

    val allVideos: List<MediaItem> = courageVideos + christmasVideos + additionalVideos
}
