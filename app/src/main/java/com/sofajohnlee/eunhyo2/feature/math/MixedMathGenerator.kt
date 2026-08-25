package com.sofajohnlee.eunhyo2.feature.math

import kotlin.random.Random

class MixedMathGenerator(
    private val random: Random = Random.Default,
    private val generator: AdvancedMathProblemGenerator = AdvancedMathProblemGenerator(random),
) {
    data class MixedExercise(
        val mode: NumberMode,
        val operation: MathOperation,
        val exercise: MathExercise,
    )

    fun generate(difficulty: MathDifficulty): MixedExercise {
        val mode = NumberMode.entries[random.nextInt(NumberMode.entries.size)]
        val operation = MathOperation.entries[random.nextInt(MathOperation.entries.size)]
        return MixedExercise(
            mode = mode,
            operation = operation,
            exercise = generator.generate(mode, operation, difficulty),
        )
    }
}
