package com.example.taskmanager

import android.graphics.drawable.Icon
import android.widget.ImageButton
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.draw.clip
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
                            .padding(top = 20.dp, bottom = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        )
                    val progress = if (totalTasks > 0) completedTasks / totalTasks.toFloat() else 0f
                    val animatedProgress by animateFloatAsState(
                        targetValue = progress,
                        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
                    )
                    val progressColor = when {
                        animatedProgress == 1f -> Color(0xFF69F0AE)
                        else -> Color(0xFFFF8700)
                    }
                    LinearProgressIndicator(
                        progress = animatedProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(30.dp))
                            .background(Color.LightGray, RoundedCornerShape(30.dp))
                            .clip(RoundedCornerShape(50)),
                        color = progressColor,
                        trackColor = Color.LightGray,
                    )
                    Text(

                        text = "$completedTasks / $totalTasks",
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(top = 10.dp)
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
            TaskList(tasks, taskViewModel, userViewModel)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick={navController.navigate("taskAdder/$email")},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                containerColor = Color(0xFFe74c3c),
                contentColor = Color.White,
            ) {
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
//        Button(
//            onClick={navController.navigate("taskAdder/$email")},
//            colors = ButtonColors(Color(0xFFe74c3c), Color.White, Color(0xFFe74c3c), Color.White),
//            modifier = Modifier.padding(20.dp)
//        ){
//            Row{
//                Image(
//                    painter = painterResource(R.drawable.baseline_add_circle_outline_24),
//                    contentDescription = "add button",
//                    modifier = Modifier.size(30.dp)
//                )
//                Text("Add a Task", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.align(Alignment.CenterVertically).padding(start=5.dp))
//            }
//        }
    }
}

@Composable
fun TaskList(tasks: List<Task>, taskViewModel: TaskViewModel, userViewModel: UserViewModel) {
    val email = userViewModel.signedInEmail.collectAsState().value
    Column(modifier = Modifier.fillMaxWidth().padding( 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)) {
            Text(
                text = "N",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 24.dp),
            )
            Image(
                painter = painterResource(R.drawable.baseline_delete_24),
                contentDescription = "delete",
                modifier = Modifier.padding(end = 50.dp)
            )
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 60.dp),
            )
            Text(
                text = "Status",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end=30.dp),
            )
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.2f),
            )
        }
        Divider()

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(tasks) { index, task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = (index + 1).toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 2.dp),
                    )
                    IconButton(
                        onClick = {
                            task.taskId?.let { email?.let { it1 ->
                            taskViewModel.deleteTask(it,
                                it1
                            )
                        }
                            }
                                  },
                        modifier = Modifier.size(25.dp).weight(0.1f),
                    ){
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_forever_24),
                            contentDescription = "delete",
                            modifier = Modifier.size(24.dp).weight(0.1f)
                        )
                    }
                    Text(
                        text = task.description ?: "No description",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(0.2f),
                    )
                    Checkbox(
                        checked = task.status ?: false,
                        onCheckedChange = { checked ->
                            val updatedTask = task.copy(status = checked)
                            taskViewModel.updateTask(updatedTask)
                        },
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = task.dueDate ?: "No due date",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(0.1f),
                    )
                }
                Divider()
            }
        }
    }
}


