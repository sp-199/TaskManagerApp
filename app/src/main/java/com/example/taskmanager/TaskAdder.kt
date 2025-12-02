package com.example.taskmanager

import android.media.Image
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date

@Composable
fun TaskAdderScreen(
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel,
    navController: NavHostController
) {
    val email = userViewModel.signedInEmail.collectAsState().value
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val dateRegex = Regex("""\d{2}-\d{2}-\d{4}""")

    fun isValidDate(date: String): Boolean {
        if (!date.matches(dateRegex)) return false
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MM-yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return try {
            val parsedDate = LocalDate.parse(date, formatter)
            parsedDate.format(formatter) == date
        } catch (e: DateTimeParseException) {
            false
        }
    }

    Box() {
        Image(
            painter = painterResource(R.drawable.welcome_background),
            contentDescription = "background2",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                setToScale(1.1f, 1.1f, 1.2f, 1f)
            })
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("dashboard") },
                modifier = Modifier
                    .padding(top = 40.dp, start = 5.dp, bottom = 170.dp)
                    .align(Alignment.Start),
                colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White)
            ) {
                Text("Back")
            }
            Text(
                text = "Add a New Task",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color(0xFFe74c3c),
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Task Description") },
                placeholder = { Text("Enter Description", color = Color.LightGray) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .border(width = 2.dp, color = Color(0xFFEA6F5C)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray,
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = dueDate,
                onValueChange = {
                    dueDate = it
                    errorMessage = if (isValidDate(it)) {
                        ""
                    } else {
                        "Please enter a valid date in DD-MM-YYYY format"
                    }
                },
                label = { Text("Due Date") },
                placeholder = { Text("DD-MM-YYYY", color = Color.LightGray) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .border(width = 2.dp, color = Color(0xFFEA6F5C)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray,
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Start).padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (description.isBlank()) {
                        errorMessage = "Description cannot be empty"
                    } else if (!isValidDate(dueDate) && dueDate.isNotBlank()) {
                        errorMessage = "Please enter a valid date in DD-MM-YYYY format"
                    } else {
                        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        val parsedDate = if (dueDate.isNotBlank()) {
                            LocalDate.parse(dueDate, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()
                        } else null

                        val newTask = Task(
                            email = email.toString(),
                            description = description,
                            dueDate = parsedDate?.let { Date.from(it) },
                            status = false
                        )
                        taskViewModel.addTask(newTask)

                        description = ""
                        dueDate = ""
                        showSuccessMessage = true
                        errorMessage = ""
                        navController.navigate("dashboard")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(Color(0xFFe74c3c), Color.White, Color(0xFFe74c3c), Color.White)
            ) {
                Text("Add Task", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold))
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (showSuccessMessage) {
                Text(
                    text = "Task added successfully!",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

