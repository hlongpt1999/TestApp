package com.example.testandroid.function

import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class UtilsTest {

    @Before
    fun setUp() {

    }

    @Test
    fun testCorrectValue() {
        val result = Utils.isCorrectEmail("admin")
        Assert.assertEquals(true, result)
    }

    @Test
    fun testIncorrectValue() {
        Assert.assertEquals(false, Utils.isCorrectEmail("long"))
        Assert.assertEquals(false, Utils.isCorrectEmail("long1"))
        assertFalse(Utils.isCorrectEmail("long1344"))
    }
}