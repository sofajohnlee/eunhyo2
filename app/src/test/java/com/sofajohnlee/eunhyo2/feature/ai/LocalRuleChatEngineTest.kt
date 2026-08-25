package com.sofajohnlee.eunhyo2.feature.ai

import org.junit.Assert.assertTrue
import org.junit.Test

class LocalRuleChatEngineTest {
    private val engine = LocalRuleChatEngine()

    @Test
    fun greetingReturnsAssistantGreeting() {
        assertTrue(engine.respond("안녕").contains("안녕하세요"))
    }

    @Test
    fun mathQuestionRoutesToMathStudyHint() {
        assertTrue(engine.respond("수학 공부").contains("수학 메뉴"))
    }
}
