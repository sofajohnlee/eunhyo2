package com.sofajohnlee.eunhyo2.feature.goldenbell

data class GoldenBellQuestion(
    val text: String,
    val answer: Boolean,
)

object GoldenBellRepository {
    val questions: List<GoldenBellQuestion> = listOf(
        GoldenBellQuestion("세계에서 가장 넓은 나라는 러시아이다.", true),
        GoldenBellQuestion("세계에서 인구가 10억 명이 넘는 나라는 중국과 인도이다.", true),
        GoldenBellQuestion("세계에서 가장 작은 나라는 바티칸시티이다.", true),
        GoldenBellQuestion("8 더하기 4 는 12이다.", true),
        GoldenBellQuestion("9 더하기 9 는 8이다.", false),
        GoldenBellQuestion("7 더하기 5 는 13이다.", false),
        GoldenBellQuestion("지구는 태양 주위를 돈다.", true),
        GoldenBellQuestion("지구는 달의 주위를 돈다.", false),
        GoldenBellQuestion("지구는 스스로 회전한다.", true),
        GoldenBellQuestion("블랙홀 이론으로 유명한 스티븐 호킹은 2018년에 사망했다.", true),
        GoldenBellQuestion("아인슈타인은 지식보다 상상력이 중요하다고 생각했다.", true),
        GoldenBellQuestion("여성 최초의 노벨상 수상자는 마리 퀴리이다.", true),
        GoldenBellQuestion("세계에서 가장 높은 산은 에베레스트이다.", true),
        GoldenBellQuestion("100미터 달리기 세계기록 보유자는 우사인 볼트이다.", true),
        GoldenBellQuestion("세계에서 가장 많이 사용되는 언어는 영어이다.", false),
        GoldenBellQuestion("레미제라블의 주인공은 장발장이다.", true),
        GoldenBellQuestion("장화 신은 고양이는 샤를 페로의 작품이다.", true),
        GoldenBellQuestion("신데렐라는 안데르센이 만들었다.", false),
        GoldenBellQuestion("미국의 초대 대통령은 조지 워싱턴이다.", true),
        GoldenBellQuestion("미국은 55개의 주로 이루어졌다.", false),
        GoldenBellQuestion("독일의 수도는 파리이다.", false),
        GoldenBellQuestion("일본의 수도는 도쿄이다.", true),
        GoldenBellQuestion("중국의 수도는 베이징이다.", true),
    )
}
