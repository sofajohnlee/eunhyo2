package com.sofajohnlee.eunhyo2.feature.math

import kotlin.random.Random
import org.junit.Assert.assertTrue
import org.junit.Test

class AdvancedMathProblemGeneratorTest {
    @Test
    fun generatesSolvableExercisesForEveryMode() {
        val generator = AdvancedMathProblemGenerator(Random(7))
        NumberMode.values().forEach { mode ->
            MathOperation.values().forEach { operation ->
                val exercise = generator.generate(mode, operation, MathDifficulty.INTERMEDIATE)
                assertTrue(exercise.expression.isNotBlank())
                assertTrue(exercise.expectedAnswer.isNotBlank())
                assertTrue(exercise.matches(exercise.expectedAnswer))
            }
        }
    }
}
