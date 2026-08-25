package com.sofajohnlee.eunhyo2.feature.math

import kotlin.random.Random

class MathProblemGenerator(
    private val random: Random = Random.Default,
) {
    fun generate(
        operation: MathOperation,
        difficulty: MathDifficulty = MathDifficulty.INTERMEDIATE,
    ): MathProblem {
        val max = difficulty.maxOperand
        return when (operation) {
            MathOperation.ADD -> MathProblem(random.nextInt(0, max), random.nextInt(0, max), operation)
            MathOperation.SUBTRACT -> {
                val a = random.nextInt(0, max)
                val b = random.nextInt(0, max)
                MathProblem(maxOf(a, b), minOf(a, b), operation)
            }
            MathOperation.MULTIPLY -> {
                val factorMax = when (difficulty) {
                    MathDifficulty.BEGINNER -> 6
                    MathDifficulty.INTERMEDIATE -> 13
                    MathDifficulty.ADVANCED -> 20
                }
                MathProblem(random.nextInt(0, factorMax), random.nextInt(0, factorMax), operation)
            }
            MathOperation.DIVIDE -> {
                val divisorMax = when (difficulty) {
                    MathDifficulty.BEGINNER -> 6
                    MathDifficulty.INTERMEDIATE -> 13
                    MathDifficulty.ADVANCED -> 20
                }
                val divisor = random.nextInt(1, divisorMax)
                val quotient = random.nextInt(0, divisorMax)
                MathProblem(divisor * quotient, divisor, operation)
            }
        }
    }
}
