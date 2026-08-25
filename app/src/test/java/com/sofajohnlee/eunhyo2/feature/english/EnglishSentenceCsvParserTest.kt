package com.sofajohnlee.eunhyo2.feature.english

import org.junit.Assert.assertEquals
import org.junit.Test

class EnglishSentenceCsvParserTest {
    @Test
    fun parsesQuotedCsvColumns() {
        val csv = "1,\"I like apples.\",\"나는 사과를 좋아한다.\",\"basic\"\n"
        val result = EnglishSentenceCsvParser().parse(csv.byteInputStream())

        assertEquals(1, result.size)
        assertEquals("I like apples.", result[0].sentence)
        assertEquals("나는 사과를 좋아한다.", result[0].meaning)
        assertEquals("basic", result[0].note)
    }
}
