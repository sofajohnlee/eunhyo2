package com.sofajohnlee.eunhyo2.feature.math

import kotlin.random.Random

class MathProblemGenerator(
    private val random: Random = Random.Default,
) {
    fun generate(operation: MathOperation, max: Int = 20): MathProblem {
        require(max >= 2)
        return when (operation) {
            MathOperation.ADD -> MathProblem(random.nextInt(0, max), random.nextInt(0, max), operation)
            MathOperation.SUBTRACT -> {
                val a = random.nextInt(0, max)
                val b = random.nextInt(0, max)
                MathProblem(maxOf(a, b), minOf(a, b), operation)
            }
            MathOperation.MULTIPLY -> MathProblem(random.nextInt(0, 13), random.nextInt(0, 13), operation)
            MathOperation.DIVIDE -> {
                val divisor = random.nextInt(1, 13)
                val quotient = random.nextInt(0, 13)
                MathProblem(divisor * quotient, divisor, operation)
            }
        }
    }
}
