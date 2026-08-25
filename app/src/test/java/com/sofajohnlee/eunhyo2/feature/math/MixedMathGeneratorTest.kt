package com.sofajohnlee.eunhyo2.feature.math

import kotlin.random.Random
import org.junit.Assert.assertTrue
import org.junit.Test

class MixedMathGeneratorTest {
    @Test
    fun generatedExercisesAreAnswerableAcrossModes() {
        val generator = MixedMathGenerator(Random(42))
        repeat(50) {
            val item = generator.generate(MathDifficulty.INTERMEDIATE)
            assertTrue(item.exercise.matches(item.exercise.answer))
        }
    }
}
