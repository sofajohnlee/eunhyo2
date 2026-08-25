package com.sofajohnlee.eunhyo2.feature.math

import org.junit.Assert.assertEquals
import org.junit.Test

class MeasurementConverterTest {
    @Test
    fun lengthConversionsAreNormalized() {
        assertEquals(250, MeasurementConverter.metersToCentimeters(2) + 50)
        assertEquals(2 to 50, MeasurementConverter.centimetersToMetersAndCentimeters(250))
        assertEquals(4 to 0, MeasurementConverter.addLength(1, 50, 2, 50))
    }

    @Test
    fun timeConversionsAreNormalized() {
        assertEquals(120, MeasurementConverter.hoursToMinutes(2))
        assertEquals(2 to 5, MeasurementConverter.minutesToHoursAndMinutes(125))
        assertEquals(2 to 30, MeasurementConverter.addTime(1, 45, 0, 45))
    }
}
