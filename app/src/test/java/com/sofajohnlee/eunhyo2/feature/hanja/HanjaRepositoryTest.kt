package com.sofajohnlee.eunhyo2.feature.hanja

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream

class HanjaRepositoryTest {
    private val repository = HanjaRepository()

    @Test
    fun builtInDataIsAvailableWithoutExternalStorage() {
        val entries = repository.builtIn()
        assertTrue(entries.isNotEmpty())
        assertEquals("人", entries.first().character)
    }

    @Test
    fun parsesLegacyFourColumnCsvShape() {
        val csv = "1,人,인,사람\n2,大,대,큰\n"
        val entries = repository.parseCsv(ByteArrayInputStream(csv.toByteArray()))
        assertEquals(HanjaEntry("人", "인", "사람"), entries[0])
        assertEquals(HanjaEntry("大", "대", "큰"), entries[1])
    }

    @Test
    fun parsesQuotedCsvFields() {
        val csv = "1,學,학,\"배울, 익힐\"\n"
        val entries = repository.parseCsv(ByteArrayInputStream(csv.toByteArray()))
        assertEquals("배울, 익힐", entries.single().meaning)
    }
}
