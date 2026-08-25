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

        binding.buttonMetersToCm.setOnClickListener { unary { "${MeasurementConverter.metersToCentimeters(it)} cm" } }
        binding.buttonCmToMeters.setOnClickListener {
            unary { value -> MeasurementConverter.centimetersToMetersAndCentimeters(value).let { (m, cm) -> "${m} m ${cm} cm" } }
        }
        binding.buttonHoursToMinutes.setOnClickListener { unary { "${MeasurementConverter.hoursToMinutes(it)} 분" } }
        binding.buttonMinutesToHours.setOnClickListener {
            unary { value -> MeasurementConverter.minutesToHoursAndMinutes(value).let { (h, min) -> "${h}시간 ${min}분" } }
        }
        binding.buttonDaysToHours.setOnClickListener { unary { "${MeasurementConverter.daysToHours(it)} 시간" } }
        binding.buttonHoursToDays.setOnClickListener {
            unary { value -> MeasurementConverter.hoursToDaysAndHours(value).let { (d, h) -> "${d}일 ${h}시간" } }
        }
        binding.buttonWeeksToDays.setOnClickListener { unary { "${MeasurementConverter.weeksToDays(it)} 일" } }
        binding.buttonDaysToWeeks.setOnClickListener {
            unary { value -> MeasurementConverter.daysToWeeksAndDays(value).let { (w, d) -> "${w}주 ${d}일" } }
        }
        binding.buttonYearsToMonths.setOnClickListener { unary { "${MeasurementConverter.yearsToMonths(it)} 개월" } }
        binding.buttonMonthsToYears.setOnClickListener {
            unary { value -> MeasurementConverter.monthsToYearsAndMonths(value).let { (y, m) -> "${y}년 ${m}개월" } }
        }
    }

    private fun unary(transform: (Int) -> String) {
        val value = input() ?: return
        binding.textResult.text = transform(value)
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
