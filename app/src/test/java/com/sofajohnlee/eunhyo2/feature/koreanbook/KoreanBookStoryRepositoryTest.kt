package com.sofajohnlee.eunhyo2.feature.koreanbook

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class KoreanBookStoryRepositoryTest {
    private val repository = KoreanBookStoryRepository()

    @Test
    fun preservesLegacyCharacterRange() {
        val story = repository.story(KoreanBookSettings(character = 99, plot = 1, language = 1))
        assertEquals(13, story.characterId)
    }

    @Test
    fun resolvesKoreanHappyStory() {
        val story = repository.story(KoreanBookSettings(character = 1, plot = 1, language = 1))
        assertEquals(StoryPlot.HAPPY, story.plot)
        assertEquals(StoryLanguage.KOREAN, story.language)
        assertTrue(story.text.contains("유진"))
    }

    @Test
    fun resolvesEnglishStory() {
        val story = repository.story(KoreanBookSettings(character = 3, plot = 2, language = 2))
        assertEquals(StoryPlot.FUNNY, story.plot)
        assertEquals(StoryLanguage.ENGLISH, story.language)
        assertTrue(story.text.contains("Aurora Princess"))
    }
}
