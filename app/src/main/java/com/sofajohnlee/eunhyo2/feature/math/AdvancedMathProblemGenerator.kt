package com.sofajohnlee.eunhyo2.feature.math

import java.math.BigDecimal
import kotlin.random.Random

class AdvancedMathProblemGenerator(
    private val random: Random = Random.Default,
) {
    fun generate(mode: NumberMode, operation: MathOperation, difficulty: MathDifficulty): MathExercise = when (mode) {
        NumberMode.NATURAL -> natural(operation, difficulty)
        NumberMode.DECIMAL -> decimal(operation, difficulty)
        NumberMode.FRACTION -> fraction(operation, difficulty)
    }

    private fun natural(operation: MathOperation, difficulty: MathDifficulty): MathExercise {
        val problem = MathProblemGenerator(random).generate(operation, difficulty)
        return MathExercise(problem.expression, problem.answer.toString())
    }

    private fun decimal(operation: MathOperation, difficulty: MathDifficulty): MathExercise {
        val scale = if (difficulty == MathDifficulty.BEGINNER) 1 else 2
        val bound = when (difficulty) {
            MathDifficulty.BEGINNER -> 100
            MathDifficulty.INTERMEDIATE -> 1000
            MathDifficulty.ADVANCED -> 10000
        }
        val divisor = BigDecimal.TEN.pow(scale)
        val left = BigDecimal(random.nextInt(1, bound)).divide(divisor)
        val right = BigDecimal(random.nextInt(1, bound)).divide(divisor)
        val (a, b) = if (operation == MathOperation.SUBTRACT && left < right) right to left else left to right
        val answer = when (operation) {
            MathOperation.ADD -> a + b
            MathOperation.SUBTRACT -> a - b
            MathOperation.MULTIPLY -> a * b
            MathOperation.DIVIDE -> {
                val q = BigDecimal(random.nextInt(1, 20))
                val d = BigDecimal(random.nextInt(1, 20))
                return MathExercise("${q * d} ÷ $d", q.stripTrailingZeros().toPlainString())
            }
        }
        return MathExercise("$a ${operation.symbol} $b", answer.stripTrailingZeros().toPlainString())
    }

    private fun fraction(operation: MathOperation, difficulty: MathDifficulty): MathExercise {
        val maxDenominator = when (difficulty) {
            MathDifficulty.BEGINNER -> 6
            MathDifficulty.INTERMEDIATE -> 12
            MathDifficulty.ADVANCED -> 20
        }
        val a = Fraction.of(random.nextInt(1, maxDenominator), random.nextInt(2, maxDenominator + 1))
        val b = Fraction.of(random.nextInt(1, maxDenominator), random.nextInt(2, maxDenominator + 1))
        return when (operation) {
            MathOperation.ADD -> MathExercise("$a + $b", (a + b).toString())
            MathOperation.SUBTRACT -> {
                val result = a - b
                if (result.numerator >= 0) MathExercise("$a − $b", result.toString())
                else MathExercise("$b − $a", (b - a).toString())
            }
            MathOperation.MULTIPLY -> MathExercise(
                "$a × $b",
                Fraction.of(a.numerator * b.numerator, a.denominator * b.denominator).toString(),
            )
            MathOperation.DIVIDE -> MathExercise(
                "$a ÷ $b",
                Fraction.of(a.numerator * b.denominator, a.denominator * b.numerator).toString(),
            )
        }
    }
}
