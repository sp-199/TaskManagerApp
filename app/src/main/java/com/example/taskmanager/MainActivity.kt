package com.example.taskmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.ui.theme.TaskManagerTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("Migration", "Applying Migration 2 -> 3")
                database.execSQL("PRAGMA foreign_keys=OFF;")
                database.execSQL("ALTER TABLE users RENAME TO users_old;")
                database.execSQL("""
            CREATE TABLE users (
                email TEXT PRIMARY KEY,  -- Email as primary key
                firstName TEXT,
                lastName TEXT,
                age INTEGER,             -- Moved 'age' before 'phoneNumber'
                phoneNumber TEXT,
                password TEXT
            );
        """)
                database.execSQL("""
            INSERT INTO users (email, firstName, lastName, age, phoneNumber, password)
            SELECT email, firstName, lastName, age, phoneNumber, password
            FROM users_old;
        """)
                database.execSQL("DROP TABLE users_old;")
                database.execSQL("PRAGMA foreign_keys=ON;")
            }
        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("Migration", "Applying Migration 3 -> 4")
                database.execSQL("PRAGMA foreign_keys=OFF;")
                database.execSQL("ALTER TABLE tasks RENAME TO tasks_old;")
                database.execSQL("""
            CREATE TABLE tasks (
                taskId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                email TEXT NOT NULL,
                description TEXT,
                due_date TEXT,          -- Updated column name
                status INTEGER NOT NULL, -- Boolean stored as 0/1
                FOREIGN KEY(email) REFERENCES users(email) ON DELETE CASCADE
            );
        """)
                database.execSQL("""
            INSERT INTO tasks (taskId, email, description, due_date, status)
            SELECT taskId, email, description, dueDate, status
            FROM tasks_old;
        """)
                database.execSQL("DROP TABLE tasks_old;")
                database.execSQL("PRAGMA foreign_keys=ON;")
            }
        }
        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "usersdbexpl.db"
        ).addMigrations(MIGRATION_2_3, MIGRATION_3_4)
            .build()

        val userRepository = UserRepository(db.userDAO())
        val userViewModel: UserViewModel by viewModels {
            UserViewModelFactory(userRepository)
        }

        val taskRepository = TaskRepository(db.taskDAO())
        val taskViewModel: TaskViewModel by viewModels {
            TaskViewModelFactory(taskRepository)
        }

        setContent {
            TaskManagerTheme {
                MyNavigationApp(userViewModel, taskViewModel)
            }
        }
    }
}






























