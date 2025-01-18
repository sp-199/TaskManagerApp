package com.example.taskmanager


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController, userViewModel: UserViewModel, taskViewModel: TaskViewModel){
    val email = userViewModel.signedInEmail.collectAsState().value
    var name by remember{ mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var age by remember{ mutableStateOf(0) }
    var phoneNumber by remember { mutableStateOf("") }
    LaunchedEffect(email) {
        name = userViewModel.getName(email.toString())?: ""
        lastname = userViewModel.getLastname(email.toString()) ?: ""
        age = userViewModel.getAge(email.toString()) ?: 0
        phoneNumber = userViewModel.getPhoneNumber(email.toString()) ?: ""
        name = name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
        lastname = lastname.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
    Box(modifier = Modifier
        .height(250.dp)
        .background(Color(0xFF018F83))
        .fillMaxWidth())
    Button(
        onClick = {navController.navigate("dashboard")},
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(start = 5.dp),
        colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White)
    ) {
        Text("Back", fontWeight = FontWeight.Bold)
    }
    Button(
        onClick = {navController.navigate("editProfile/{email}")},
        modifier = Modifier
            .padding(top = 40.dp,start = 290.dp),
        colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White),
    ){
        Text("Edit Profile", fontWeight = FontWeight.Bold)
    }
    Image(
        painter = painterResource(R.drawable.baseline_arrow_drop_down_circle_24),
        contentDescription = "filler1",
        modifier = Modifier
            .padding(top = 176.dp)
            .padding(start = 106.dp)
            .size(199.dp)
            .padding(bottom = 50.dp)
    )
    Image(
        painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
        contentDescription = "filler2",
        modifier = Modifier
            .padding(top = 175.dp)
            .padding(start = 106.dp)
            .size(200.dp)
            .padding(bottom = 50.dp)
    )
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.baseline_account_circle_24),
            contentDescription = "basic profile icon",
            modifier = Modifier
                .padding(top = 150.dp)
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "$name $lastname",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Name: $name",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Text(
            text = "Lastname: $lastname",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Text(
            text = "Age: $age",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Text(
            text = "Phone: $phoneNumber",
            modifier = Modifier.padding(bottom = 20.dp),
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
    }
    val showDialog = remember { mutableStateOf(false) }
    Button(
        onClick = {
            showDialog.value = true
        },
        modifier = Modifier
            .padding(top = 850.dp, start = 135.dp),
        colors = ButtonColors(Color(0xFFe74c3c), Color.White, Color(0xFFe74c3c), Color.White),
    ){
        Text("Delete Account", fontWeight = FontWeight.ExtraBold)
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Delete Account") },
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    email?.let { userViewModel.deleteUserByEmail(it) }
                    email?.let{taskViewModel.deleteALLTasks(it)}
                    navController.navigate("login")
                    showDialog.value = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}