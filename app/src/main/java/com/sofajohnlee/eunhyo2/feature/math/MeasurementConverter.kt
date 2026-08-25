package com.sofajohnlee.eunhyo2.feature.math

object MeasurementConverter {
    fun metersToCentimeters(meters: Int): Int = meters * 100
    fun centimetersToMetersAndCentimeters(centimeters: Int): Pair<Int, Int> =
        centimeters / 100 to centimeters % 100

    fun hoursToMinutes(hours: Int): Int = hours * 60
    fun minutesToHoursAndMinutes(minutes: Int): Pair<Int, Int> =
        minutes / 60 to minutes % 60

    fun daysToHours(days: Int): Int = days * 24
    fun hoursToDaysAndHours(hours: Int): Pair<Int, Int> = hours / 24 to hours % 24

    fun weeksToDays(weeks: Int): Int = weeks * 7
    fun daysToWeeksAndDays(days: Int): Pair<Int, Int> = days / 7 to days % 7

    fun yearsToMonths(years: Int): Int = years * 12
    fun monthsToYearsAndMonths(months: Int): Pair<Int, Int> = months / 12 to months % 12

    fun addLength(meters1: Int, centimeters1: Int, meters2: Int, centimeters2: Int): Pair<Int, Int> =
        centimetersToMetersAndCentimeters(
            metersToCentimeters(meters1) + centimeters1 + metersToCentimeters(meters2) + centimeters2,
        )

    fun addTime(hours1: Int, minutes1: Int, hours2: Int, minutes2: Int): Pair<Int, Int> =
        minutesToHoursAndMinutes(hoursToMinutes(hours1) + minutes1 + hoursToMinutes(hours2) + minutes2)
}
