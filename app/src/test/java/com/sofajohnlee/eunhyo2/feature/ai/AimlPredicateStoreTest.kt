package com.sofajohnlee.eunhyo2.feature.ai

import java.io.ByteArrayInputStream
import org.junit.Assert.assertEquals
import org.junit.Test

class AimlPredicateStoreTest {
    @Test
    fun loadsDefaultsAndUpdatesSessionValues() {
        val source = "name:Friend\ntopic:unknown\nfavoritecolor:what\n"
        val store = AimlPredicateStore.parse(ByteArrayInputStream(source.toByteArray()))

        assertEquals("Friend", store.get("name"))
        assertEquals("unknown", store.topic())
        assertEquals("Blue", store.set("favoritecolor", " Blue "))
        assertEquals("Blue", store.get("FAVORITECOLOR"))
    }
}
