package com.sofajohnlee.eunhyo2.feature.ai

data class ChatMessage(
    val text: String,
    val fromUser: Boolean,
)

interface ChatEngine {
    fun respond(message: String): String
}

class LocalRuleChatEngine : ChatEngine {
    override fun respond(message: String): String {
        val normalized = message.trim()
        if (normalized.isBlank()) return "무엇이 궁금한지 입력해 주세요."
        return when {
            normalized.contains("안녕") -> "안녕하세요. 은효 학습 도우미입니다."
            normalized.contains("이름") -> "저는 은효 학습 도우미입니다."
            normalized.contains("수학") -> "수학 메뉴에서 자연수, 소수, 분수 문제를 연습할 수 있어요."
            normalized.contains("영어") -> "영어 학습 메뉴에서 다국어 카드와 발음을 연습할 수 있어요."
            else -> "입력한 내용을 확인했습니다: $normalized"
        }
    }
}
