package com.sofajohnlee.eunhyo2.feature.sports

data class SportsVideo(
    val title: String,
    val videoId: String,
)

object SportsCatalog {
    val videos = listOf(
        SportsVideo("줄넘기", "vVctfW2OCyQ"),
        SportsVideo("배드민턴", "hFf6P-mXEG4"),
        SportsVideo("탁구", "XcVOUkNzhVg"),
    )
}
