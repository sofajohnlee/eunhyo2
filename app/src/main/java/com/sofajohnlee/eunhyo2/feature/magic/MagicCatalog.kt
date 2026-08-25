package com.sofajohnlee.eunhyo2.feature.magic

data class MagicVideo(
    val title: String,
    val videoId: String,
)

object MagicCatalog {
    val videos: List<MagicVideo> = listOf(
        MagicVideo("동전상자", "m1YJfwayYe0"),
        MagicVideo("그림액자", "IFaHpDhY6gc"),
        MagicVideo("체인지백", "u3E90wZVFME"),
        MagicVideo("우유컵", "uLoA8PzR9TU"),
        MagicVideo("요술상자", "URSYZpbyEV0"),
        MagicVideo("카드", "karU105_Z7c"),
        MagicVideo("덥립", "6W85Wwqjyss"),
        MagicVideo("지팡이", "IXqPRj_dfcg"),
        MagicVideo("딜라이트", "WftXZiMKoFM"),
        MagicVideo("로프", "MWQwDYP3NHs"),
    )
}
