package com.example.taskmanager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier

@Composable
fun EditProfileScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val email = userViewModel.signedInEmail.collectAsState().value
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(email) {
        name = email?.let { userViewModel.getName(it) } ?: ""
        lastname = email?.let { userViewModel.getLastname(it) } ?: ""
        age = email?.let { userViewModel.getAge(it)?.toString() } ?: ""
        phoneNumber = email?.let { userViewModel.getPhoneNumber(it) } ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
    ) {
        Button(
            onClick = {navController.navigate("profile/{email}")},
            modifier = Modifier
                .padding(top = 40.dp,start = 5.dp, bottom =100.dp),
            colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White)
        ) {
            Text("Back")
        }
        Text(
            text = "Edit Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(bottom = 25.dp)
                .align(Alignment.CenterHorizontally),
            color = Color(0xFF006C62)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color(0xFF018F83)),
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        TextField(
            value = lastname,
            onValueChange = { lastname = it },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color(0xFF018F83)),
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color(0xFF018F83)),
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White

            )
        )

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color(0xFF018F83)),
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White

            )
        )

        Button(
            onClick = {
                isSaved = true
                email?.let { userViewModel.updateName(name, it) }
                email?.let { userViewModel.updateLastname(lastname, it) }
                email?.let { userViewModel.updateAge(age.toInt(), it) }
                email?.let { userViewModel.updateName(name, it) }
                navController.navigate("profile/{email}")
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006C62)
            )
        ) {
            Text("Save Changes", fontWeight = FontWeight.Bold)
        }

        if (isSaved) {
            Text(
                text = "Profile updated successfully!",
                color = Color.Green,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
