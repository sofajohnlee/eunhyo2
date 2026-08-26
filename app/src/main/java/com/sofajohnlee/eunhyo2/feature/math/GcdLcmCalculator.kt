package com.sofajohnlee.eunhyo2.feature.math

import kotlin.math.abs

object GcdLcmCalculator {
    fun gcd(a: Int, b: Int): Int {
        var x = abs(a)
        var y = abs(b)
        while (y != 0) {
            val r = x % y
            x = y
            y = r
        }
        return x
    }

    fun lcm(a: Int, b: Int): Int {
        if (a == 0 || b == 0) return 0
        return abs((a / gcd(a, b)) * b)
    }
}
