package com.sadxlab.movielist.utils

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest {

    @Test
    fun isSearchInputIsValid() {
        val searchInput = "Marvel"
        val result = Validator.searchInputIsValid(searchInput)
        assertTrue(result)
    }
}