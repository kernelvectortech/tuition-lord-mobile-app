package com.kernelvector.tuitionlord.core

/**
 * Simple Calculator — Demo module for arithmetic operations
 * 
 * This is a boilerplate example for getting started with domain logic in the core/ package.
 * Add more operations (subtract, multiply, divide) as needed.
 * 
 * NO ANDROID IMPORTS — this module is pure Kotlin, ready for KMP extraction.
 */
object Calculator {
    
    /**
     * Add two numbers
     * 
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    fun add(a: Int, b: Int): Int {
        return a + b
    }
    
    /**
     * Add two decimal numbers
     * 
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    fun add(a: Double, b: Double): Double {
        return a + b
    }
    
    /**
     * Subtract two numbers
     */
    fun subtract(a: Int, b: Int): Int {
        return a - b
    }
    
    /**
     * Multiply two numbers
     */
    fun multiply(a: Int, b: Int): Int {
        return a * b
    }
    
    /**
     * Divide two numbers
     * 
     * @throws IllegalArgumentException if divisor is zero
     */
    fun divide(a: Int, b: Int): Int {
        if (b == 0) {
            throw IllegalArgumentException("Cannot divide by zero")
        }
        return a / b
    }
}
