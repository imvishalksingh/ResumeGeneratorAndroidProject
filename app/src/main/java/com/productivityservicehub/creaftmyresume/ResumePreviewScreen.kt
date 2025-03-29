package com.productivityservicehub.creaftmyresume

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.productivityservicehub.creaftmyresume.model.*
import com.productivityservicehub.creaftmyresume.util.PDFGenerator
import java.io.File

@Composable
fun ResumePreviewScreen(
    resumeInfo: ResumeInfo,
    onNavigateBack: () -> Unit,
    onShareResume: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val pdfGenerator = PDFGenerator(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Text(
                text = "Resume Preview",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Personal Information
        Text(
            text = resumeInfo.personalInfo.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = resumeInfo.personalInfo.email,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = resumeInfo.personalInfo.phone,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = resumeInfo.personalInfo.address,
            style = MaterialTheme.typography.bodyMedium
        )
        
        // Social Links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (resumeInfo.personalInfo.linkedIn.isNotEmpty()) {
                Text(
                    text = "LinkedIn: ${resumeInfo.personalInfo.linkedIn}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (resumeInfo.personalInfo.github.isNotEmpty()) {
                Text(
                    text = "GitHub: ${resumeInfo.personalInfo.github}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (resumeInfo.personalInfo.leetcode.isNotEmpty()) {
                Text(
                    text = "LeetCode: ${resumeInfo.personalInfo.leetcode}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Career Objective
        if (resumeInfo.personalInfo.objective.isNotEmpty()) {
            Text(
                text = "Career Objective",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = resumeInfo.personalInfo.objective,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Professional Summary
        if (resumeInfo.summary.isNotEmpty()) {
            Text(
                text = "Professional Summary",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = resumeInfo.summary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Education
        if (resumeInfo.education.isNotEmpty()) {
            Text(
                text = "Education",
                style = MaterialTheme.typography.titleMedium
            )
            resumeInfo.education.forEach { education ->
                Text(
                    text = education.instituteName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = education.degree,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Duration: ${education.duration}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Score/GPA: ${education.score}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Experience
        if (resumeInfo.experience.isNotEmpty()) {
            Text(
                text = "Experience",
                style = MaterialTheme.typography.titleMedium
            )
            resumeInfo.experience.forEach { experience ->
                Text(
                    text = experience.companyName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = experience.position,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Duration: ${experience.duration}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Responsibilities:",
                    style = MaterialTheme.typography.bodyMedium
                )
                experience.responsibilities.forEach { responsibility ->
                    Text(
                        text = "• $responsibility",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Projects
        if (resumeInfo.projects.isNotEmpty()) {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.titleMedium
            )
            resumeInfo.projects.forEach { project ->
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Technologies: ${project.technologies}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (project.demoLink != null) {
                    Text(
                        text = "Demo: ${project.demoLink}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = "Description:",
                    style = MaterialTheme.typography.bodyMedium
                )
                project.description.forEach { description ->
                    Text(
                        text = "• $description",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Certifications
        if (resumeInfo.trainingAndCertifications.isNotEmpty()) {
            Text(
                text = "Certifications",
                style = MaterialTheme.typography.titleMedium
            )
            resumeInfo.trainingAndCertifications.forEach { certification ->
                Text(
                    text = certification.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Organization: ${certification.organization}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (certification.verificationId != null) {
                    Text(
                        text = "Verification ID: ${certification.verificationId}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (certification.verificationLink != null) {
                    Text(
                        text = "Verification Link: ${certification.verificationLink}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Technical Skills
        if (resumeInfo.technicalSkills.languages.isNotEmpty() ||
            resumeInfo.technicalSkills.databases.isNotEmpty() ||
            resumeInfo.technicalSkills.frameworks.isNotEmpty() ||
            resumeInfo.technicalSkills.developerTools.isNotEmpty() ||
            resumeInfo.technicalSkills.skills.isNotEmpty()) {
            Text(
                text = "Technical Skills",
                style = MaterialTheme.typography.titleMedium
            )
            
            if (resumeInfo.technicalSkills.languages.isNotEmpty()) {
                Text(
                    text = "Programming Languages: ${resumeInfo.technicalSkills.languages.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (resumeInfo.technicalSkills.databases.isNotEmpty()) {
                Text(
                    text = "Databases: ${resumeInfo.technicalSkills.databases.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (resumeInfo.technicalSkills.frameworks.isNotEmpty()) {
                Text(
                    text = "Frameworks: ${resumeInfo.technicalSkills.frameworks.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (resumeInfo.technicalSkills.developerTools.isNotEmpty()) {
                Text(
                    text = "Developer Tools: ${resumeInfo.technicalSkills.developerTools.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (resumeInfo.technicalSkills.skills.isNotEmpty()) {
                Text(
                    text = "Other Skills: ${resumeInfo.technicalSkills.skills.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    try {
                        val file = pdfGenerator.generatePdf(resumeInfo, "Resume")
                        Toast.makeText(
                            context,
                            "Resume saved to: ${file.absolutePath}",
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Error saving resume: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save PDF")
            }
            Button(
                onClick = onShareResume,
                modifier = Modifier.weight(1f)
            ) {
                Text("Share Resume")
            }
        }
    }
} 