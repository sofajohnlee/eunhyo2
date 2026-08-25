package com.sofajohnlee.eunhyo2.feature.math

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MathProblemGeneratorTest {
    private val generator = MathProblemGenerator()

    @Test
    fun subtractionNeverProducesNegativeAnswer() {
        repeat(100) {
            val problem = generator.generate(MathOperation.SUBTRACT, MathDifficulty.ADVANCED)
            assertTrue(problem.left >= problem.right)
            assertTrue(problem.answer >= 0)
        }
    }

    @Test
    fun divisionProducesWholeNumberAnswer() {
        repeat(100) {
            val problem = generator.generate(MathOperation.DIVIDE, MathDifficulty.ADVANCED)
            assertTrue(problem.right > 0)
            assertEquals(0, problem.left % problem.right)
        }
    }

    @Test
    fun beginnerAdditionStaysWithinConfiguredRange() {
        repeat(100) {
            val problem = generator.generate(MathOperation.ADD, MathDifficulty.BEGINNER)
            assertTrue(problem.left in 0 until MathDifficulty.BEGINNER.maxOperand)
            assertTrue(problem.right in 0 until MathDifficulty.BEGINNER.maxOperand)
        }
    }
}
