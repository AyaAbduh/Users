package com.example.users.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.users.data.local.User
import com.example.users.viewmodel.UserViewModel

@Composable
fun DisplayScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val users by viewModel.users.collectAsState()

    DisplayScreenContent(users = users)
}

@Composable
fun DisplayScreenContent(users: List<User>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Saved Users",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (users.isEmpty()) {
            Text("No users found.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(users) { user ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Name: ${user.name}")
                            Text("Age: ${user.age}")
                            Text("Job Title: ${user.jobTitle}")
                            Text("Gender: ${user.gender}")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DisplayScreenPreview() {
    MaterialTheme {
        DisplayScreenContent(
            users = listOf(
                User(1, "Alice", 28, "Engineer", "Female"),
                User(2, "Bob", 35, "Manager", "Male")
            )
        )
    }
}
