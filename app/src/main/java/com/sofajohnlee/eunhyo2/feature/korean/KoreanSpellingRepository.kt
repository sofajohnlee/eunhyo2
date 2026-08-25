package com.sofajohnlee.eunhyo2.feature.korean

data class KoreanSpellingRule(
    val prompt: String,
    val answer: String,
    val explanation: String,
)

object KoreanSpellingRepository {
    val rules: List<KoreanSpellingRule> = listOf(
        KoreanSpellingRule("꽃에/꼬체", "꽃에", "표준어의 발음 형태대로 적되, 뜻을 빠르고 쉽게 파악하기 위해 낱말의 각 부분에 대해 본모양을 밝히어 적습니다."),
        KoreanSpellingRule("해돋이/해도지/해도디", "해돋이", "'ㄷ,ㅌ'받침 뒤에 모음 'ㅣ'가 올 경우에는 'ㅈ,ㅊ'으로 소리 나더라도 'ㄷ,ㅌ'으로 적습니다."),
        KoreanSpellingRule("여자/녀자", "여자", "한자음 '녀,뇨,뉴,니'가 단어 첫머리에 올 경우에는 두음 법칙에 따라 '여,요,유,이'로 적습니다."),
        KoreanSpellingRule("역사/력사", "역사", "한자음 '랴,려,례,료,류,리'가 단어 첫머리에 올 경우에는 두음 법칙에 따라 '야,여,예,요,유,이'로 적습니다."),
        KoreanSpellingRule("노인/로인", "노인", "한자음 '라,래,로,뢰,루,르'가 단어 첫머리에 올 경우에는 두음 법칙에 따라 '나,내,노,뇌,누,느'로 적습니다."),
        KoreanSpellingRule("백분율/백분률", "백분율", "모음이나 'ㄴ'받침 뒤에 이어지는 '렬,률'은 '열,율'로 적습니다."),
        KoreanSpellingRule("사용률/사용율", "사용률", "모음이나 'ㄴ'받침이 아닌 'ㄱ,ㅇ' 등이 뒤에 이어지는 '렬,률'은 '렬,률'로 적습니다."),
    )
}
