package com.example.testandroid.function

import org.junit.Assert.*

import org.junit.Test

class UtilsTest {

    @Test
    fun isCorrectEmail_fail() {
        var utils = Utils()
        val result = utils.isCorrectEmail("long")
        assertEquals(false, result)
    }

    @Test
    fun isCorrectEmail_correct() {
        var utils = Utils()
        val result = utils.isCorrectEmail("admin")
        assertEquals(true, result)
    }
}