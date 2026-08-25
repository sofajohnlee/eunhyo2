package com.sofajohnlee.eunhyo2.feature.math

data class MathProblem(
    val left: Int,
    val right: Int,
    val operation: MathOperation,
) {
    val answer: Int
        get() = when (operation) {
            MathOperation.ADD -> left + right
            MathOperation.SUBTRACT -> left - right
            MathOperation.MULTIPLY -> left * right
            MathOperation.DIVIDE -> if (right == 0) 0 else left / right
        }

    val expression: String
        get() = "$left ${operation.symbol} $right"
}

enum class MathOperation(val symbol: String) {
    ADD("+"),
    SUBTRACT("−"),
    MULTIPLY("×"),
    DIVIDE("÷"),
}
