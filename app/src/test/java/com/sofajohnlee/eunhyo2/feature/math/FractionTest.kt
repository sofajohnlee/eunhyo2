package com.sofajohnlee.eunhyo2.feature.math

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FractionTest {
    @Test
    fun reducesFraction() {
        assertEquals("1/2", Fraction.of(2, 4).toString())
    }

    @Test
    fun addsAndSubtractsFractions() {
        assertEquals("5/6", (Fraction.of(1, 2) + Fraction.of(1, 3)).toString())
        assertEquals("1/6", (Fraction.of(1, 2) - Fraction.of(1, 3)).toString())
    }

    @Test
    fun acceptsEquivalentFractionAnswer() {
        assertTrue(MathExercise("1/2 + 0", "1/2").matches("2/4"))
    }
}
