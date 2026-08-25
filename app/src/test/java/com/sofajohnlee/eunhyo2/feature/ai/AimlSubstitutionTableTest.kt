package com.sofajohnlee.eunhyo2.feature.ai

import java.io.ByteArrayInputStream
import org.junit.Assert.assertEquals
import org.junit.Test

class AimlSubstitutionTableTest {
    @Test
    fun appliesProgramAbStyleSubstitutions() {
        val source = "\" can't \",\" can not \"\n\" i'm \",\" I am \"\n"
        val table = AimlSubstitutionTable.parse(ByteArrayInputStream(source.toByteArray()))

        assertEquals("I can not go", table.apply("I can't go"))
        assertEquals("I am ready", table.apply("I'm ready"))
    }

    @Test
    fun appliesPersonSubstitutionWithoutBreakingWhitespace() {
        val source = "\" you \",\" me \"\n\" your \",\" my \"\n"
        val table = AimlSubstitutionTable.parse(ByteArrayInputStream(source.toByteArray()))

        assertEquals("me and my book", table.apply("you and your book"))
    }
}
