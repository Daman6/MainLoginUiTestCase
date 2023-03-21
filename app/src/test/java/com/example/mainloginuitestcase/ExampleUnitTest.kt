package com.example.mainloginuitestcase

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testGetAge() {
        assertEquals(30, getAge(1993, Calendar.JANUARY, 26))
        assertEquals(-1, getAge(2024, Calendar.MARCH, 17))
        assertEquals(101, getAge(1922, Calendar.MARCH, 17))
        val now = Calendar.getInstance()
        assertEquals(0, getAge(now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH]))
        assertEquals(26, getAge(1997, Calendar.DECEMBER, 31))
    }

    @Test
    fun valid_year() {
        assertEquals(true, getValidYear("2023"))
        assertEquals(true, getValidYear("1993"))
        assertEquals(true, getValidYear("2099"))
    }

    @Test
    fun invalid_year_putting_space() {
        assertEquals(false, getValidYear("  "))
    }

    @Test
    fun invalid_year_putting_letter() {
        assertEquals(false, getValidYear("year"))
    }

    @Test
    fun invalid_year_putting_symbol_with_number() {
        assertEquals(false, getValidYear("2000-"))
        assertEquals(false, getValidYear("2000@"))
    }

    @Test
    fun invalid_year_putting_extra_numbers() {
        assertEquals(false, getValidYear("20023"))
        assertEquals(false, getValidYear("19000"))
    }

    @Test
    fun invalid_year_putting_previous_years() {
        assertEquals(false, getValidYear("199"))
        assertEquals(false, getValidYear("200"))
    }

    @Test
    fun valid_month() {
        assertEquals(true, getValidMonth("01"))
        assertEquals(true, getValidMonth("06"))
        assertEquals(true, getValidMonth("6"))
        assertEquals(true, getValidMonth("8"))
        assertEquals(true, getValidMonth("12"))
    }

    @Test
    fun invalid_month() {
        assertEquals(false, getValidMonth("0"))
        assertEquals(false, getValidMonth("15"))
        assertEquals(false, getValidMonth("13"))
    }

    @Test
    fun invalid_month_putting_letter() {
        assertEquals(false, getValidMonth("jan"))
        assertEquals(false, getValidMonth("DEC"))
        assertEquals(false, getValidMonth("sep"))
    }
    @Test
    fun invalid_month_putting_emptyspace() {
        assertEquals(false, getValidMonth("  "))
    }

    @Test
    fun invalid_month_putting_symbols() {
        assertEquals(false, getValidMonth("01/"))
        assertEquals(false, getValidMonth("12-"))
        assertEquals(false, getValidMonth("/12/"))
    }

    @Test
    fun valid_day() {
        assertEquals(true, getValidDay("1"))
        assertEquals(true, getValidDay("15"))
        assertEquals(true, getValidDay("31"))
    }

    @Test
    fun invalid_day() {
        assertEquals(false, getValidDay("0"))
        assertEquals(false, getValidDay("ten"))
        assertEquals(false, getValidDay("35"))
        assertEquals(false, getValidDay("-1"))
        assertEquals(false, getValidDay(" "))
        assertEquals(false, getValidDay(" 2"))
    }

    @Test
    fun valid_mobileNumber() {
        assertEquals(true, isValidMobile("9953595635"))
        assertEquals(true, isValidMobile("111111 1111"))
        assertEquals(true, isValidMobile(" 1111111111"))
        assertEquals(true, isValidMobile("99535#9635"))
        assertEquals(true, isValidMobile("99535#9635aa"))

    }

    @Test
    fun invalid_mobileNumber() {
        assertEquals(false, isValidMobile("  "))
    }

    @Test
    fun valid_mobieleNumber_puttinsg_letter() {
        assertEquals(true, isValid_USA_And_India_PhoneNumber("(456) 123-1234"))
        assertEquals(true, isValid_USA_And_India_PhoneNumber("9953595635"))
        assertEquals(true, isValid_USA_And_India_PhoneNumber("+918880344456"))
        assertEquals(true, isValid_USA_And_India_PhoneNumber("+91 8880344456"))
    }
    @Test
    fun invalid_mobileNumber_exceeding_limit() {
        assertEquals(false, isValid_USA_And_India_PhoneNumber("99535612616217629635"))
    }
    @Test
    fun invalid_mobileNumber_putting_space() {
        assertEquals(false, isValid_USA_And_India_PhoneNumber(" "))
        assertEquals(false, isValid_USA_And_India_PhoneNumber(" 9953595635"))
    }


}