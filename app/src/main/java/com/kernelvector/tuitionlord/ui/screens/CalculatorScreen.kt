package com.kernelvector.tuitionlord.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kernelvector.tuitionlord.core.Calculator
import com.kernelvector.tuitionlord.ui.theme.TuitionLordTheme

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var numberOne by remember { mutableStateOf("") }
    var numberTwo by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Calculator Demo", fontSize = 24.sp)
        
        // Input fields
        OutlinedTextField(
            value = numberOne,
            onValueChange = { numberOne = it },
            label = { Text("First Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = numberTwo,
            onValueChange = { numberTwo = it },
            label = { Text("Second Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Operation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    try {
                        val a = numberOne.toInt()
                        val b = numberTwo.toInt()
                        result = "Result: ${Calculator.add(a, b)}"
                        error = ""
                    } catch (e: Exception) {
                        error = "Invalid input"
                        result = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add")
            }

            Button(
                onClick = {
                    try {
                        val a = numberOne.toInt()
                        val b = numberTwo.toInt()
                        result = "Result: ${Calculator.subtract(a, b)}"
                        error = ""
                    } catch (e: Exception) {
                        error = "Invalid input"
                        result = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sub")
            }

            Button(
                onClick = {
                    try {
                        val a = numberOne.toInt()
                        val b = numberTwo.toInt()
                        result = "Result: ${Calculator.multiply(a, b)}"
                        error = ""
                    } catch (e: Exception) {
                        error = "Invalid input"
                        result = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Mul")
            }

            Button(
                onClick = {
                    try {
                        val a = numberOne.toInt()
                        val b = numberTwo.toInt()
                        result = "Result: ${Calculator.divide(a, b)}"
                        error = ""
                    } catch (e: Exception) {
                        error = e.message ?: "Invalid input"
                        result = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Div")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Result display
        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors()
            ) {
                Text(
                    text = result,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // Error display
        if (error.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors()
            ) {
                Text(
                    text = "Error: $error",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(12.dp),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error
                )
            }
        }

        // Clear button
        Button(
            onClick = {
                numberOne = ""
                numberTwo = ""
                result = ""
                error = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    TuitionLordTheme {
        CalculatorScreen()
    }
}
