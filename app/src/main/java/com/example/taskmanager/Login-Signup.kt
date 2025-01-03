package com.example.taskmanager

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController, userViewModel: UserViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
    ) {
        Text(
            "Welcome to Your Task Manager!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter Your Email") },
            label = { Text("Email") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        userViewModel.setSignedInEmail(email)
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter Your Password") },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                try {
                    userViewModel.getPassword(email.lowercase()) { pass ->
                        if (pass != null && password == pass) {
                            result = "Log in successful!"
                            navController.navigate("dashboard")
                        } else {
                            result = "Incorrect email or password."
                        }
                    }
                } catch (e: Exception) {
                    Log.e("LoginScreen", "Error during login", e)
                    result = "An unexpected error occurred."
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Log in")
        }
        if (result.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = result,
                color = if (result == "Log in successful!") MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        TextButton(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Don't have an account? Create one")
        }
    }
}

@Composable
fun SignupScreen(navController: NavHostController, userViewModel: UserViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
    ) {
        Button(
            onClick = {navController.navigate("login")},
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(start = 5.dp),
            colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White)
        ) {
            Text("Back")
        }
        Text(
            "Create Your Account",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var phoneNumber by remember{ mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        var result by remember { mutableStateOf("") }

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = { Text("Enter Your First Name") },
            label = { Text("First Name") },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = { Text("Enter Your Last Name") },
            label = { Text("Last Name") },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = age,
            onValueChange = { age = it },
            placeholder = { Text("Enter Your Age") },
            label = { Text("Age") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = { Text("Enter Your Phone Number") },
            label = { Text("Phone Number") },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter Your Email") },
            label = { Text("Email") },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter Your Password") },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm Your Password") },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Button(
            onClick = {
                if (password == confirmPassword) {
                    scope.launch {
                        val user = User( email.lowercase(), firstName, lastName, age.toInt(), phoneNumber, password)
                        userViewModel.insertUser(user)
                        result = "User saved successfully"
                    }
                    navController.navigate("login")
                } else {
                    errorMessage = "Passwords do not match."
                }
                navController.navigate("login")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sign up")
        }
    }
}