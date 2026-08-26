package com.sofajohnlee.eunhyo2.feature.math

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityGcdLcmBinding

class GcdLcmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGcdLcmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGcdLcmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener {
            val a = binding.inputA.text?.toString()?.toIntOrNull()
            val b = binding.inputB.text?.toString()?.toIntOrNull()
            if (a == null || b == null) {
                binding.textResult.text = "두 정수를 입력하세요."
                return@setOnClickListener
            }
            binding.textResult.text = buildString {
                append("최대공약수: ")
                append(GcdLcmCalculator.gcd(a, b))
                append("\n최소공배수: ")
                append(GcdLcmCalculator.lcm(a, b))
            }
        }
    }
}
