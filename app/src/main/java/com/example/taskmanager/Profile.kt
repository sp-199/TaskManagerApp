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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
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
import com.example.taskmanager.R
import com.example.taskmanager.UserViewModel

@Composable
//fun ProfileScreen(){
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
            .padding(top = 30.dp)
            .padding(start = 5.dp),
        colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White)
    ) {
        Text("Back")
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
            //text = "John Doe",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Name: $name",
            //text = "Name: John",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Text(
            text = "Lastname: $lastname",
//            text = "Lastname: Doe",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Text(
            text = "Age: $age",
            //text = "Age: 19",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Text(
            text = "Phone: $phoneNumber",
            //text = "Phone Number: 596577555",
            modifier = Modifier.padding(bottom = 20.dp),
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray
        )
        Button(
            onClick = {},
            modifier = Modifier.padding(start = 25.dp),
            colors = ButtonColors(Color.DarkGray, Color.White, Color.DarkGray, Color.White),
        ){
            Text("Edit Profile", fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = {
                email?.let { userViewModel.deleteUserByEmail(it) }
                email?.let{taskViewModel.deleteALLTasks(it)}
                navController.navigate("login")
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonColors(Color(0xFFe74c3c), Color.White, Color(0xFFe74c3c), Color.White),
        ){
            Text("Delete Account", fontWeight = FontWeight.Bold)
        }
    }
}