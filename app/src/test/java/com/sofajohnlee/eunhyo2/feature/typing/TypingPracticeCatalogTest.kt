package com.sofajohnlee.eunhyo2.feature.typing

import org.junit.Assert.assertEquals
import org.junit.Test

class TypingPracticeCatalogTest {
    @Test
    fun accuracyUsesTargetLengthAndMatchingPositions() {
        assertEquals(100, TypingPracticeCatalog.accuracy("abcd", "abcd"))
        assertEquals(50, TypingPracticeCatalog.accuracy("abcd", "abxy"))
        assertEquals(0, TypingPracticeCatalog.accuracy("abcd", "wxyz"))
    }
}
