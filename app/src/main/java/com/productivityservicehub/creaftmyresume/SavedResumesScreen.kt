package com.productivityservicehub.creaftmyresume

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.productivityservicehub.creaftmyresume.util.ResumeMetadata
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SavedResumesScreen(
    savedResumes: List<ResumeMetadata>,
    onResumeClick: (String) -> Unit,
    onDeleteResume: (String) -> Unit,
    onCreateNewResume: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Saved Resumes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (savedResumes.isEmpty()) {
            // Show empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved resumes yet",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            // Show list of saved resumes
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(savedResumes.sortedByDescending { it.timestamp }) { resume ->
                    SavedResumeItem(
                        resume = resume,
                        onClick = { onResumeClick(resume.key) },
                        onDelete = { onDeleteResume(resume.key) }
                    )
                }
            }
        }

        // Create new resume button
        Button(
            onClick = onCreateNewResume,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Create New Resume")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedResumeItem(
    resume: ResumeMetadata,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = resume.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = resume.email,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatTimestamp(resume.timestamp),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Template: ${resume.templateName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            IconButton(onClick = { showDeleteDialog = true }) {
                Text("🗑️")
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Resume") },
            text = { Text("Are you sure you want to delete this resume?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun formatTimestamp(timestamp: String): String {
    try {
        val inputFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(timestamp) ?: return timestamp
        return outputFormat.format(date)
    } catch (e: Exception) {
        return timestamp
    }
} 