package com.example.taskmanager

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController, userViewModel: UserViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_background), // Replace with your image resource
            contentDescription = "background1",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                setToScale(1.1f, 1.1f, 1.2f, 1f) // Adjust brightness (values > 1 increase brightness, < 1 decrease)
            })
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
        ) {
            Spacer(
                modifier = Modifier.height(125.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.task_manager_app_icon),
                contentDescription = "Description of the image",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )
            Text(
                "Welcome to Your Task Manager!",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                color = Color(0xFFFF4040)
            )
            Spacer(modifier = Modifier.height(16.dp))
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var result by remember { mutableStateOf("") }
            var emailColor by remember { mutableStateOf(Color.Black) }
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter Your Email") },
                label = { Text("Email") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .border(width = 2.dp, color = Color(0xFFEA6F5C)),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = emailColor,
                    disabledTextColor = emailColor,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedLabelColor = Color(0xFFEA6F5C),
                    focusedLabelColor = Color(0xFFEA6F5C),
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                )
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
                    .border(width = 2.dp, color = Color(0xFFEA6F5C)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedLabelColor = Color(0xFFEA6F5C),
                    focusedLabelColor = Color(0xFFEA6F5C),
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
            )
            Spacer(modifier = Modifier.height(38.dp))
            Button(
                onClick = {
                    try {
                        userViewModel.getPassword(email.lowercase()) { pass ->
                            if (pass != null && password == pass) {
                                result = "Log in successful!"
                                navController.navigate("dashboard")
                            } else {
                                result = "Incorrect email or password."
                                emailColor = Color.Red
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("LoginScreen", "Error during login", e)
                        result = "An unexpected error occurred."
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(height = 45.dp, width = 100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF36A99D)
                )
            ) {
                Text("Log in", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
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
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text("Don't have an account? Create one", style = TextStyle(color = Color.Blue))
            }
        }
    }
}

@Composable
fun SignupScreen(navController: NavHostController, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Button(
            onClick = {navController.navigate("login")},
            modifier = Modifier
                .padding(top = 40.dp,start = 5.dp, bottom = 40.dp),
            colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White)
        ) {
            Text("Back")
        }
        Text(
            "Create Your Account",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            color = Color(0xFF00468C)
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
            placeholder = { Text("Enter Your First Name", color = Color.LightGray) },
            label = { Text("First Name", color = Color.DarkGray) },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = { Text("Enter Your Last Name", color = Color.LightGray) },
            label = { Text("Last Name", color = Color.DarkGray) },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        TextField(
            value = age,
            onValueChange = { age = it },
            placeholder = { Text("Enter Your Age", color = Color.LightGray) },
            label = { Text("Age", color = Color.DarkGray) },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = { Text("Enter Your Phone Number", color = Color.LightGray) },
            label = { Text("Phone Number", color = Color.DarkGray) },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter Your Email", color = Color.LightGray) },
            label = { Text("Email", color = Color.DarkGray) },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter Your Password", color = Color.LightGray) },
            label = { Text("Password", color = Color.DarkGray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm Your Password", color = Color.LightGray) },
            label = { Text("Confirm Password", color = Color.DarkGray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2196F3))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
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
                        navController.navigate("login")
                    }
                    navController.navigate("login")
                } else {
                    errorMessage = "Passwords do not match."
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(height = 65.dp, width = 110.dp)
                .padding(top = 15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00468C)
            )
        ) {
            Text("Sign up", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
        }
    }
}