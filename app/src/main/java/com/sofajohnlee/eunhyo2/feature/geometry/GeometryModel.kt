package com.sofajohnlee.eunhyo2.feature.geometry

enum class GeometryCategory(val label: String) {
    CIRCLE("원"),
    TRIANGLE("삼각형"),
    RECTANGLE("사각형"),
    SOLID("입체도형"),
}

data class GeometryItem(
    val title: String,
    val description: String,
)

object GeometryCatalog {
    private val items = mapOf(
        GeometryCategory.CIRCLE to listOf(
            GeometryItem("원", "중심에서 같은 거리에 있는 점들의 모임"),
            GeometryItem("반지름", "원의 중심에서 원 위의 한 점까지의 거리"),
            GeometryItem("지름", "원의 중심을 지나 원 위의 두 점을 잇는 선분"),
        ),
        GeometryCategory.TRIANGLE to listOf(
            GeometryItem("정삼각형", "세 변의 길이가 같은 삼각형"),
            GeometryItem("이등변삼각형", "두 변의 길이가 같은 삼각형"),
            GeometryItem("직각삼각형", "한 각이 90도인 삼각형"),
        ),
        GeometryCategory.RECTANGLE to listOf(
            GeometryItem("정사각형", "네 변의 길이가 같고 네 각이 직각인 사각형"),
            GeometryItem("직사각형", "네 각이 모두 직각인 사각형"),
            GeometryItem("평행사변형", "두 쌍의 대변이 각각 평행한 사각형"),
        ),
        GeometryCategory.SOLID to listOf(
            GeometryItem("정육면체", "모든 면이 합동인 정사각형인 입체도형"),
            GeometryItem("직육면체", "모든 면이 직사각형인 입체도형"),
            GeometryItem("원기둥", "서로 평행한 두 원을 밑면으로 하는 입체도형"),
        ),
    )

    fun items(category: GeometryCategory): List<GeometryItem> = items.getValue(category)
}
