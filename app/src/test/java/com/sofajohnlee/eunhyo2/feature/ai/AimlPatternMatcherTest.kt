package com.sofajohnlee.eunhyo2.feature.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class AimlPatternMatcherTest {
    @Test
    fun capturesStarWildcard() {
        val match = AimlPatternMatcher.match("HELLO *", "HELLO BRIGHT WORLD")
        assertNotNull(match)
        assertEquals("BRIGHT WORLD", match!!.stars.single())
    }

    @Test
    fun underscoreRequiresAtLeastOneToken() {
        assertNull(AimlPatternMatcher.match("HELLO _", "HELLO"))
        assertEquals("THERE", AimlPatternMatcher.match("HELLO _", "HELLO THERE")!!.stars.single())
    }

    @Test
    fun supportsMultipleWildcards() {
        val match = AimlPatternMatcher.match("I * YOU *", "I REALLY LIKE YOU VERY MUCH")
        assertNotNull(match)
        assertEquals(listOf("REALLY LIKE", "VERY MUCH"), match!!.stars)
    }
}
