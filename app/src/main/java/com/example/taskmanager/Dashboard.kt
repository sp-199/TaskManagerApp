package com.example.taskmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import java.sql.Date

@Composable
fun DashboardScreen(navController: NavHostController, userViewModel: UserViewModel, taskViewModel: TaskViewModel){
    val email = userViewModel.signedInEmail.collectAsState().value
    val tasks by taskViewModel.tasks.collectAsState()
    val totalTasks by taskViewModel.totalTasks.collectAsState()
    val completedTasks by taskViewModel.completedTasks.collectAsState()
    LaunchedEffect(email) {
        if (!email.isNullOrEmpty()) {
            email.let { taskViewModel.fetchTaskCounts(it) }
        }
    }


    LaunchedEffect(email) {
        if (!email.isNullOrEmpty()) {
            email.let { taskViewModel.loadTasks(it) }
        }
    }
    Column (modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
//                .background(Color(0xFF9DD9F3))
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,

            ){
            Text(
                modifier = Modifier
                    .padding(top=35.dp),
                text = "Dashboard",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
                .height(185.dp)
        ){
            Button(
                onClick = {navController.navigate("profile/{email}")},
                shape = RoundedCornerShape(40.dp),
                colors = ButtonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.White, disabledContentColor = Color.DarkGray),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxHeight()
                    .width(185.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(40.dp))
                    .border(width = 2.dp, shape = RoundedCornerShape(40.dp), color = Color.Cyan)

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_account_circle_24),
                        contentDescription = "basic profile icon",
                        modifier = Modifier
                            .size(height = 80.dp, width = 100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Profile",
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(40.dp),
                colors = ButtonColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.White, disabledContentColor = Color.DarkGray),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxHeight()
                    .width(185.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(40.dp))
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(40.dp),
                        color = Color(0xFFFF8700)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Progress",
                        color = Color(0xFFFF8700),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,

                        )
                    Text(
                        text = "$completedTasks / $totalTasks",
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }
            }
        }
        if (tasks.isEmpty()) {
            Text("No tasks found", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                itemsIndexed(tasks) { index, task ->
                    TaskItem(task = task, index = index + 1) { updatedTask ->
                        taskViewModel.updateTask(updatedTask) // Call to update the task in the database
                    }
                }
            }
        }
        Button(
            onClick={navController.navigate("taskAdder/$email")},
            colors = ButtonColors(Color(0xFFe74c3c), Color.White, Color(0xFFe74c3c), Color.White),
            modifier = Modifier.padding(20.dp)
        ){
            Row{
                Image(
                    painter = painterResource(R.drawable.baseline_add_circle_outline_24),
                    contentDescription = "add button",
                    modifier = Modifier.size(30.dp)
                )
                Text("Add a Task", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.align(Alignment.CenterVertically).padding(start=5.dp))
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, index: Int, onStatusChange:(Task)->Unit) {
    var isChecked by remember { mutableStateOf(task.status ?: false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "ID: ${task.taskId ?: "N/A"}",
            modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Column (
            modifier = Modifier.align(Alignment.CenterVertically),
        ){
            Text(
                text = "Task: $index",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 5.dp, end = 10.dp),
            )
            Text(
                text = "${task.description ?: "No description"}",
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 5.dp,end = 10.dp),
            )
        }
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                val updatedTask = task.copy(status = checked)
                onStatusChange(updatedTask)
            }
        )
        Text(
            text = "${task.dueDate ?: "No due date"}",
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 5.dp),
        )
    }
}