package com.kernelvector.tuitionlord

import org.junit.Test
import org.junit.Assert.*
import com.kernelvector.tuitionlord.core.Calculator

/**
 * Unit tests for Calculator module
 * 
 * Run with: ./gradlew test
 */
class CalculatorTest {
    
    @Test
    fun testAddIntegers() {
        // Arrange
        val a = 10
        val b = 20
        val expected = 30
        
        // Act
        val result = Calculator.add(a, b)
        
        // Assert
        assertEquals(expected, result)
    }
    
    @Test
    fun testAddDoubles() {
        val a = 10.5
        val b = 20.3
        val expected = 30.8
        
        val result = Calculator.add(a, b)
        
        assertEquals(expected, result, 0.01)  // 0.01 tolerance for floating point
    }
    
    @Test
    fun testSubtract() {
        assertEquals(5, Calculator.subtract(15, 10))
        assertEquals(0, Calculator.subtract(10, 10))
        assertEquals(-5, Calculator.subtract(5, 10))
    }
    
    @Test
    fun testMultiply() {
        assertEquals(30, Calculator.multiply(3, 10))
        assertEquals(0, Calculator.multiply(0, 100))
        assertEquals(-15, Calculator.multiply(-3, 5))
    }
    
    @Test
    fun testDivide() {
        assertEquals(5, Calculator.divide(20, 4))
        assertEquals(1, Calculator.divide(10, 10))
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun testDivideByZeroThrowsException() {
        Calculator.divide(10, 0)
    }
    
    @Test
    fun testConstantExamples() {
        // Example: Using constants for test data
        val HOURLY_RATE = 500  // INR per hour (tuition context)
        val HOURS_WORKED = 2
        
        val totalFee = Calculator.multiply(HOURLY_RATE, HOURS_WORKED)
        
        assertEquals(1000, totalFee)
    }
}
