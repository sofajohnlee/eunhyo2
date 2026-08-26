package com.sofajohnlee.eunhyo2.feature.english

import org.junit.Assert.assertEquals
import org.junit.Test

class EnglishWordCatalogTest {
    @Test
    fun exposesAlphabetWordSetAndCaseModes() {
        assertEquals(26, EnglishWordCatalog.all().size)
        assertEquals("Ask", EnglishWordCatalog.display("ask", true))
        assertEquals("ask", EnglishWordCatalog.display("ASK", false))
    }
}
