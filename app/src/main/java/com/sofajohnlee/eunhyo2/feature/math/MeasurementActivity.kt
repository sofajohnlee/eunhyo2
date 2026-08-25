package com.sofajohnlee.eunhyo2.feature.math

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMeasurementBinding

class MeasurementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeasurementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMetersToCm.setOnClickListener {
            val value = input() ?: return@setOnClickListener
            binding.textResult.text = "${MeasurementConverter.metersToCentimeters(value)} cm"
        }
        binding.buttonCmToMeters.setOnClickListener {
            val value = input() ?: return@setOnClickListener
            val (m, cm) = MeasurementConverter.centimetersToMetersAndCentimeters(value)
            binding.textResult.text = "${m} m ${cm} cm"
        }
        binding.buttonHoursToMinutes.setOnClickListener {
            val value = input() ?: return@setOnClickListener
            binding.textResult.text = "${MeasurementConverter.hoursToMinutes(value)} 분"
        }
        binding.buttonMinutesToHours.setOnClickListener {
            val value = input() ?: return@setOnClickListener
            val (h, min) = MeasurementConverter.minutesToHoursAndMinutes(value)
            binding.textResult.text = "${h}시간 ${min}분"
        }
    }

    private fun input(): Int? {
        val value = binding.editValue.text?.toString()?.trim()?.toIntOrNull()
        if (value == null || value < 0) {
            binding.textResult.text = "0 이상의 정수를 입력하세요."
            return null
        }
        return value
    }
}
