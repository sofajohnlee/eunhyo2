package com.sofajohnlee.eunhyo2.feature.typing

enum class TypingLanguage(val label: String) {
    KOREAN("한글"),
    ENGLISH("영문"),
}

data class TypingLesson(
    val title: String,
    val target: String,
)

object TypingPracticeCatalog {
    private val korean = listOf(
        TypingLesson("자리연습 1", "ㅁㅓㅏㅓㄴㅓㅣㅇㄹㅣㅇㅇ;;ㅁ;ㅇㅣ;ㄴ"),
        TypingLesson("자리연습 2", "ㅂㄱㄷㅂㅂㄷㅈㅈㄷㅂㅈㅈㄷㄷㅂㅂㄱㅈ"),
        TypingLesson("자리연습 3", "ㅎㅅㅛㅛㅎㅜㅠㅅㅜㅗㅅㅛㅎㅛㅅㅛㅜㅎ"),
        TypingLesson("자리연습 4", "ㅔㅕㅑㅑㅕㅐㅔㅐㅑㅕㅐㅐㅑㅑㅔㅐㅑㅑ"),
        TypingLesson("낱말연습", "어머나 멀미 만남 엉망 미나리 이날 너머 말머리 얼마나 아이 엄마 어린이"),
        TypingLesson("문장연습", "바른 자세로 천천히 정확하게 입력해 보세요."),
    )

    private val english = listOf(
        TypingLesson("Home row", "JJDSAFKSALFD;;DAFS;"),
        TypingLesson("QWER", "WEWWQQERQQEQEERE"),
        TypingLesson("TYGHBN", "YNTHHBGNBHBBHYYN"),
        TypingLesson("UIOP", "OPPOIPOOIOIOIOUPOIPO"),
        TypingLesson("ZXCV", "VXXXCZCXCVZZVCCCZZXV"),
        TypingLesson("Words", "ask book come day egg fine go hi it jump kick look make name open play"),
        TypingLesson("Sentence", "Practice slowly and type each word accurately."),
    )

    fun lessons(language: TypingLanguage): List<TypingLesson> = when (language) {
        TypingLanguage.KOREAN -> korean
        TypingLanguage.ENGLISH -> english
    }

    fun accuracy(target: String, typed: String): Int {
        if (target.isEmpty()) return 100
        val same = target.indices.count { index -> typed.getOrNull(index) == target[index] }
        return ((same.toDouble() / target.length) * 100).toInt().coerceIn(0, 100)
    }
}
