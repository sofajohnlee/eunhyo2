package com.sofajohnlee.eunhyo2.feature.hanja

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream

class HanjaRadicalImporterTest {
    @Test
    fun mapsLegacyHbusuFileNamesToGroupNumbers() {
        val importer = HanjaRadicalImporter()
        val groups = importer.import(
            listOf(
                HanjaRadicalImporter.Source(
                    "hbusu14.csv",
                    ByteArrayInputStream("1,龍,룡,용\n".toByteArray()),
                ),
                HanjaRadicalImporter.Source(
                    "hbusu2.csv",
                    ByteArrayInputStream("1,水,수,물\n".toByteArray()),
                ),
            ),
        )

        assertEquals(listOf(2, 14), groups.map { it.id })
        assertEquals("水", groups.first().entries.single().character)
        assertEquals("龍", groups.last().entries.single().character)
    }
}
