package com.sofajohnlee.eunhyo2.feature.math

import kotlin.math.abs

/** Reduced immutable fraction used by migrated fraction exercises. */
data class Fraction private constructor(
    val numerator: Int,
    val denominator: Int,
) {
    init {
        require(denominator > 0)
    }

    operator fun plus(other: Fraction): Fraction = of(
        numerator * other.denominator + other.numerator * denominator,
        denominator * other.denominator,
    )

    operator fun minus(other: Fraction): Fraction = of(
        numerator * other.denominator - other.numerator * denominator,
        denominator * other.denominator,
    )

    override fun toString(): String = "$numerator/$denominator"

    companion object {
        fun of(numerator: Int, denominator: Int): Fraction {
            require(denominator != 0)
            val sign = if (denominator < 0) -1 else 1
            val normalizedNumerator = numerator * sign
            val normalizedDenominator = abs(denominator)
            val divisor = gcd(abs(normalizedNumerator), normalizedDenominator)
            return Fraction(normalizedNumerator / divisor, normalizedDenominator / divisor)
        }

        private tailrec fun gcd(a: Int, b: Int): Int =
            if (b == 0) maxOf(a, 1) else gcd(b, a % b)
    }
}
