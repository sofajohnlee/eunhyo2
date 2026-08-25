package com.sofajohnlee.eunhyo2.feature.korean

data class KoreanPronunciationCase(
    val example: String,
    val category: String,
)

class KoreanPronunciationRepository {
    val finalConsonantGuide = "ㄱ(ㄱ,ㄲ,ㄳ,ㄺ,ㅋ), ㄴ(ㄴ,ㄵ,ㄶ)\nㄷ(ㄷ,ㅅ,ㅆ,ㅈ,ㅊ,ㅌ,ㅎ), ㄹ(ㄹ,ㄼ,ㄽ,ㄾ,ㅀ)\nㅁ(ㅁ,ㄻ), ㅂ(ㄿ,ㅂ,ㅄ,ㅍ), ㅇ(ㅇ)"

    val cases: List<KoreanPronunciationCase> = listOf(
        KoreanPronunciationCase("각", "받침 ㄱ"),
        KoreanPronunciationCase("밖", "받침 ㄲ"),
        KoreanPronunciationCase("몫", "받침 ㄳ"),
        KoreanPronunciationCase("맑다/맑고/맑은", "받침 ㄺ"),
        KoreanPronunciationCase("부엌", "받침 ㅋ"),
        KoreanPronunciationCase("간", "받침 ㄴ"),
        KoreanPronunciationCase("앉다/앉고/앉는", "받침 ㄵ"),
        KoreanPronunciationCase("않다/않고/않은", "받침 ㄶ"),
        KoreanPronunciationCase("놓다/놓고/놓는", "받침 ㅎ"),
        KoreanPronunciationCase("닫다/닫고/닫는", "받침 ㄷ"),
        KoreanPronunciationCase("옷", "받침 ㅅ"),
        KoreanPronunciationCase("있었다", "받침 ㅆ"),
        KoreanPronunciationCase("잊다/잊고/잊는", "받침 ㅈ"),
        KoreanPronunciationCase("꽃", "받침 ㅊ"),
        KoreanPronunciationCase("뱉다/뱉고/뱉는", "받침 ㅌ"),
        KoreanPronunciationCase("말", "받침 ㄹ"),
        KoreanPronunciationCase("넓다/넓고/넓은", "받침 ㄼ"),
        KoreanPronunciationCase("여덟", "받침 ㄼ"),
        KoreanPronunciationCase("얇다/얇고/얇은", "받침 ㄼ"),
        KoreanPronunciationCase("훑다/훑고/훑는", "받침 ㄾ"),
        KoreanPronunciationCase("앓다/앓고/앓는", "받침 ㅀ"),
        KoreanPronunciationCase("싫증", "받침 ㅀ"),
        KoreanPronunciationCase("감", "받침 ㅁ"),
        KoreanPronunciationCase("젊다/젊고/젊은", "받침 ㄻ"),
        KoreanPronunciationCase("밟다/밟고/밟는", "받침 ㄼ"),
        KoreanPronunciationCase("읊다/읊고/읊는", "받침 ㄿ"),
        KoreanPronunciationCase("갑", "받침 ㅂ"),
        KoreanPronunciationCase("값", "받침 ㅄ"),
        KoreanPronunciationCase("숲", "받침 ㅍ"),
        KoreanPronunciationCase("강", "받침 ㅇ"),
        KoreanPronunciationCase("히읗", "ㅎ 받침"),
        KoreanPronunciationCase("긁다/긁고/긁는", "겹받침"),
        KoreanPronunciationCase("꿇다/꿇고/꿇는", "겹받침"),
        KoreanPronunciationCase("끊다/끊고/끊는", "겹받침"),
        KoreanPronunciationCase("닦다/닦고/닦는", "겹받침"),
        KoreanPronunciationCase("읽다/읽고/읽는", "겹받침"),
        KoreanPronunciationCase("옮기다/옮기고/옮기는", "겹받침"),
        KoreanPronunciationCase("삶다/삶고/삶는", "겹받침"),
        KoreanPronunciationCase("늙다/늙고/늙는", "겹받침"),
        KoreanPronunciationCase("잃다/잃고/잃는", "겹받침"),
        KoreanPronunciationCase("먹는/닫는/없는", "비음화"),
        KoreanPronunciationCase("침략/대통령/협력/사용률", "유음화"),
        KoreanPronunciationCase("줄넘기/권력/공권력", "유음화"),
        KoreanPronunciationCase("색연필", "연음·음운 변동"),
        KoreanPronunciationCase("오랜만에", "연음·음운 변동"),
    )
}
