package com.example.users.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.users.data.local.User
import com.example.users.viewmodel.UserViewModel

@Composable
fun InputScreen(
    onUserAdded: () -> Unit,
    onViewUsersClick: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val userAdded by viewModel.userAdded.collectAsState()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    if (userAdded) {
        onUserAdded()
        viewModel.resetUserAddedFlag()
    }

    InputScreenContent(
        name = name,
        onNameChange = { name = it },
        age = age,
        onAgeChange = { age = it },
        jobTitle = jobTitle,
        onJobTitleChange = { jobTitle = it },
        gender = gender,
        onGenderChange = { gender = it },
        onSaveClick = {
            if (name.isNotBlank() && age.isNotBlank()) {
                viewModel.insertUser(
                    User(
                        name = name,
                        age = age.toIntOrNull() ?: 0,
                        jobTitle = jobTitle,
                        gender = gender
                    )
                )
            }
        },
        onViewUsersClick = onViewUsersClick
    )
}

@Composable
fun InputScreenContent(
    name: String,
    onNameChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    jobTitle: String,
    onJobTitleChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onViewUsersClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = onAgeChange,
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = jobTitle,
            onValueChange = onJobTitleChange,
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        GenderDropdown(
            selectedGender = gender,
            onGenderSelected = onGenderChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save User")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onViewUsersClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View All Users")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            readOnly = true,
            label = { Text("Gender") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun InputScreenPreview() {
    MaterialTheme {
        InputScreenContent(
            name = "Alice",
            onNameChange = {},
            age = "25",
            onAgeChange = {},
            jobTitle = "Engineer",
            onJobTitleChange = {},
            gender = "Female",
            onGenderChange = {},
            onSaveClick = {},
            onViewUsersClick = {}
        )
    }
}
