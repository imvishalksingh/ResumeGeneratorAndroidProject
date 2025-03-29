package com.productivityservicehub.creaftmyresume

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.productivityservicehub.creaftmyresume.model.*

@Composable
fun ResumeInfoScreen(
    template: ResumeTemplate,
    initialResumeInfo: ResumeInfo,
    onNavigateBack: () -> Unit,
    onSaveResume: (ResumeInfo) -> Unit,
    onPreviewResume: (ResumeInfo) -> Unit
) {
    var resumeInfo by remember { mutableStateOf(initialResumeInfo) }
    val scrollState = rememberScrollState()

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
                Text("←")
            }
            Text(
                text = "Resume Information",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Personal Information Section
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.name,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(name = it)) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.email,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.phone,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(phone = it)) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.address,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(address = it)) },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.linkedIn,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(linkedIn = it)) },
            label = { Text("LinkedIn Profile") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.github,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(github = it)) },
            label = { Text("GitHub Profile") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.leetcode,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(leetcode = it)) },
            label = { Text("LeetCode Profile") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.personalInfo.objective,
            onValueChange = { resumeInfo = resumeInfo.copy(personalInfo = resumeInfo.personalInfo.copy(objective = it)) },
            label = { Text("Career Objective") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Summary Section
        Text(
            text = "Professional Summary",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.summary,
            onValueChange = { resumeInfo = resumeInfo.copy(summary = it) },
            label = { Text("Summary") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Education Section
        Text(
            text = "Education",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                resumeInfo = resumeInfo.copy(
                    education = resumeInfo.education + Education(
                        instituteName = "",
                        degree = "",
                        duration = "",
                        score = ""
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Education")
        }

        resumeInfo.education.forEachIndexed { index, education ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Education ${index + 1}")
            OutlinedTextField(
                value = education.instituteName,
                onValueChange = {
                    val newEducation = education.copy(instituteName = it)
                    val newList = resumeInfo.education.toMutableList()
                    newList[index] = newEducation
                    resumeInfo = resumeInfo.copy(education = newList)
                },
                label = { Text("Institution") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = education.degree,
                onValueChange = {
                    val newEducation = education.copy(degree = it)
                    val newList = resumeInfo.education.toMutableList()
                    newList[index] = newEducation
                    resumeInfo = resumeInfo.copy(education = newList)
                },
                label = { Text("Degree") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = education.duration,
                onValueChange = {
                    val newEducation = education.copy(duration = it)
                    val newList = resumeInfo.education.toMutableList()
                    newList[index] = newEducation
                    resumeInfo = resumeInfo.copy(education = newList)
                },
                label = { Text("Duration") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = education.score,
                onValueChange = {
                    val newEducation = education.copy(score = it)
                    val newList = resumeInfo.education.toMutableList()
                    newList[index] = newEducation
                    resumeInfo = resumeInfo.copy(education = newList)
                },
                label = { Text("Score/GPA") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Experience Section
        Text(
            text = "Experience",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                resumeInfo = resumeInfo.copy(
                    experience = resumeInfo.experience + Experience(
                        companyName = "",
                        position = "",
                        duration = "",
                        responsibilities = emptyList()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Experience")
        }

        resumeInfo.experience.forEachIndexed { index, experience ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Experience ${index + 1}")
            OutlinedTextField(
                value = experience.companyName,
                onValueChange = {
                    val newExperience = experience.copy(companyName = it)
                    val newList = resumeInfo.experience.toMutableList()
                    newList[index] = newExperience
                    resumeInfo = resumeInfo.copy(experience = newList)
                },
                label = { Text("Company") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = experience.position,
                onValueChange = {
                    val newExperience = experience.copy(position = it)
                    val newList = resumeInfo.experience.toMutableList()
                    newList[index] = newExperience
                    resumeInfo = resumeInfo.copy(experience = newList)
                },
                label = { Text("Position") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = experience.duration,
                onValueChange = {
                    val newExperience = experience.copy(duration = it)
                    val newList = resumeInfo.experience.toMutableList()
                    newList[index] = newExperience
                    resumeInfo = resumeInfo.copy(experience = newList)
                },
                label = { Text("Duration") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = experience.responsibilities.joinToString("\n"),
                onValueChange = {
                    val newExperience = experience.copy(responsibilities = it.split("\n").filter { it.isNotEmpty() })
                    val newList = resumeInfo.experience.toMutableList()
                    newList[index] = newExperience
                    resumeInfo = resumeInfo.copy(experience = newList)
                },
                label = { Text("Responsibilities (one per line)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Projects Section
        Text(
            text = "Projects",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                resumeInfo = resumeInfo.copy(
                    projects = resumeInfo.projects + Project(
                        name = "",
                        technologies = "",
                        description = emptyList()
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Project")
        }

        resumeInfo.projects.forEachIndexed { index, project ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Project ${index + 1}")
            OutlinedTextField(
                value = project.name,
                onValueChange = {
                    val newProject = project.copy(name = it)
                    val newList = resumeInfo.projects.toMutableList()
                    newList[index] = newProject
                    resumeInfo = resumeInfo.copy(projects = newList)
                },
                label = { Text("Project Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = project.technologies,
                onValueChange = {
                    val newProject = project.copy(technologies = it)
                    val newList = resumeInfo.projects.toMutableList()
                    newList[index] = newProject
                    resumeInfo = resumeInfo.copy(projects = newList)
                },
                label = { Text("Technologies Used") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = project.demoLink ?: "",
                onValueChange = {
                    val newProject = project.copy(demoLink = it)
                    val newList = resumeInfo.projects.toMutableList()
                    newList[index] = newProject
                    resumeInfo = resumeInfo.copy(projects = newList)
                },
                label = { Text("Demo Link (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = project.description.joinToString("\n"),
                onValueChange = {
                    val newProject = project.copy(description = it.split("\n").filter { it.isNotEmpty() })
                    val newList = resumeInfo.projects.toMutableList()
                    newList[index] = newProject
                    resumeInfo = resumeInfo.copy(projects = newList)
                },
                label = { Text("Description (one per line)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Certifications Section
        Text(
            text = "Certifications",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                resumeInfo = resumeInfo.copy(
                    trainingAndCertifications = resumeInfo.trainingAndCertifications + Certification(
                        title = "",
                        organization = ""
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Certification")
        }

        resumeInfo.trainingAndCertifications.forEachIndexed { index, certification ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Certification ${index + 1}")
            OutlinedTextField(
                value = certification.title,
                onValueChange = {
                    val newCertification = certification.copy(title = it)
                    val newList = resumeInfo.trainingAndCertifications.toMutableList()
                    newList[index] = newCertification
                    resumeInfo = resumeInfo.copy(trainingAndCertifications = newList)
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = certification.organization,
                onValueChange = {
                    val newCertification = certification.copy(organization = it)
                    val newList = resumeInfo.trainingAndCertifications.toMutableList()
                    newList[index] = newCertification
                    resumeInfo = resumeInfo.copy(trainingAndCertifications = newList)
                },
                label = { Text("Organization") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = certification.verificationId ?: "",
                onValueChange = {
                    val newCertification = certification.copy(verificationId = it)
                    val newList = resumeInfo.trainingAndCertifications.toMutableList()
                    newList[index] = newCertification
                    resumeInfo = resumeInfo.copy(trainingAndCertifications = newList)
                },
                label = { Text("Verification ID (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = certification.verificationLink ?: "",
                onValueChange = {
                    val newCertification = certification.copy(verificationLink = it)
                    val newList = resumeInfo.trainingAndCertifications.toMutableList()
                    newList[index] = newCertification
                    resumeInfo = resumeInfo.copy(trainingAndCertifications = newList)
                },
                label = { Text("Verification Link (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Technical Skills Section
        Text(
            text = "Technical Skills",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.technicalSkills.languages.joinToString(", "),
            onValueChange = { 
                resumeInfo = resumeInfo.copy(
                    technicalSkills = resumeInfo.technicalSkills.copy(
                        languages = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                )
            },
            label = { Text("Programming Languages (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.technicalSkills.databases.joinToString(", "),
            onValueChange = { 
                resumeInfo = resumeInfo.copy(
                    technicalSkills = resumeInfo.technicalSkills.copy(
                        databases = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                )
            },
            label = { Text("Databases (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.technicalSkills.frameworks.joinToString(", "),
            onValueChange = { 
                resumeInfo = resumeInfo.copy(
                    technicalSkills = resumeInfo.technicalSkills.copy(
                        frameworks = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                )
            },
            label = { Text("Frameworks (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.technicalSkills.developerTools.joinToString(", "),
            onValueChange = { 
                resumeInfo = resumeInfo.copy(
                    technicalSkills = resumeInfo.technicalSkills.copy(
                        developerTools = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                )
            },
            label = { Text("Developer Tools (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = resumeInfo.technicalSkills.skills.joinToString(", "),
            onValueChange = { 
                resumeInfo = resumeInfo.copy(
                    technicalSkills = resumeInfo.technicalSkills.copy(
                        skills = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                )
            },
            label = { Text("Other Skills (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Preview and Save Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onPreviewResume(resumeInfo) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Preview")
            }
            Button(
                onClick = { onSaveResume(resumeInfo) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save Resume")
            }
        }
    }
} 