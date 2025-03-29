package com.productivityservicehub.creaftmyresume

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.productivityservicehub.creaftmyresume.model.*

@OptIn(ExperimentalMaterial3Api::class)
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
            .safeContentPadding()
            .navigationBarsPadding()
    ) {
        ScreenHeader(onNavigateBack = onNavigateBack)
        Spacer(modifier = Modifier.height(24.dp))

        PersonalInfoSection(
            personalInfo = resumeInfo.personalInfo,
            onPersonalInfoChanged = { newInfo ->
                resumeInfo = resumeInfo.copy(personalInfo = newInfo)
            }
        )

        SummarySection(
            summary = resumeInfo.summary,
            onSummaryChanged = { resumeInfo = resumeInfo.copy(summary = it) }
        )

        EducationSection(
            educationList = resumeInfo.education,
            onEducationChanged = { newList ->
                resumeInfo = resumeInfo.copy(education = newList)
            }
        )

        ExperienceSection(
            experienceList = resumeInfo.experience,
            onExperienceChanged = { newList ->
                resumeInfo = resumeInfo.copy(experience = newList)
            }
        )

        ProjectsSection(
            projectsList = resumeInfo.projects,
            onProjectsChanged = { newList ->
                resumeInfo = resumeInfo.copy(projects = newList)
            }
        )

        CertificationsSection(
            certificationsList = resumeInfo.trainingAndCertifications,
            onCertificationsChanged = { newList ->
                resumeInfo = resumeInfo.copy(trainingAndCertifications = newList)
            }
        )

        SkillsSection(
            skills = resumeInfo.technicalSkills,
            onSkillsChanged = { newSkills ->
                resumeInfo = resumeInfo.copy(technicalSkills = newSkills)
            }
        )

        ActionButtons(
            onPreview = { onPreviewResume(resumeInfo) },
            onSave = { onSaveResume(resumeInfo) }
        )
    }
}

@Composable
private fun ScreenHeader(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Resume Information",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun PersonalInfoSection(
    personalInfo: PersonalInfo,
    onPersonalInfoChanged: (PersonalInfo) -> Unit
) {
    SectionTitle("Personal Information")

    ResumeOutlinedTextField(
        value = personalInfo.name,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(name = it)) },
        label = "Full Name"
    )

    ResumeOutlinedTextField(
        value = personalInfo.email,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(email = it)) },
        label = "Email"
    )

    ResumeOutlinedTextField(
        value = personalInfo.phone,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(phone = it)) },
        label = "Phone"
    )

    ResumeOutlinedTextField(
        value = personalInfo.address,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(address = it)) },
        label = "Address"
    )

    ResumeOutlinedTextField(
        value = personalInfo.linkedIn,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(linkedIn = it)) },
        label = "LinkedIn Profile"
    )

    ResumeOutlinedTextField(
        value = personalInfo.github,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(github = it)) },
        label = "GitHub Profile"
    )

    ResumeOutlinedTextField(
        value = personalInfo.leetcode,
        onValueChange = { onPersonalInfoChanged(personalInfo.copy(leetcode = it)) },
        label = "LeetCode Profile"
    )

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun SummarySection(
    summary: String,
    onSummaryChanged: (String) -> Unit
) {
    SectionTitle("Professional Summary")
    ResumeOutlinedTextField(
        value = summary,
        onValueChange = onSummaryChanged,
        label = "Summary",
        minLines = 3
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun EducationSection(
    educationList: List<Education>,
    onEducationChanged: (List<Education>) -> Unit
) {
    SectionTitle("Education")

    AddItemButton(
        text = "Add Education",
        onClick = {
            onEducationChanged(
                educationList + Education(
                    instituteName = "",
                    degree = "",
                    duration = "",
                    score = ""
                )
            )
        }
    )

    educationList.forEachIndexed { index, education ->
        Spacer(modifier = Modifier.height(16.dp))
        Text("Education ${index + 1}")

        ResumeOutlinedTextField(
            value = education.instituteName,
            onValueChange = {
                val newList = educationList.toMutableList()
                newList[index] = education.copy(instituteName = it)
                onEducationChanged(newList)
            },
            label = "Institution"
        )

        ResumeOutlinedTextField(
            value = education.degree,
            onValueChange = {
                val newList = educationList.toMutableList()
                newList[index] = education.copy(degree = it)
                onEducationChanged(newList)
            },
            label = "Degree"
        )

        ResumeOutlinedTextField(
            value = education.duration,
            onValueChange = {
                val newList = educationList.toMutableList()
                newList[index] = education.copy(duration = it)
                onEducationChanged(newList)
            },
            label = "Duration"
        )

        ResumeOutlinedTextField(
            value = education.score,
            onValueChange = {
                val newList = educationList.toMutableList()
                newList[index] = education.copy(score = it)
                onEducationChanged(newList)
            },
            label = "Score/GPA"
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ExperienceSection(
    experienceList: List<Experience>,
    onExperienceChanged: (List<Experience>) -> Unit
) {
    SectionTitle("Experience")

    AddItemButton(
        text = "Add Experience",
        onClick = {
            onExperienceChanged(
                experienceList + Experience(
                    companyName = "",
                    position = "",
                    duration = "",
                    responsibilities = emptyList()
                )
            )
        }
    )

    experienceList.forEachIndexed { index, experience ->
        Spacer(modifier = Modifier.height(16.dp))
        Text("Experience ${index + 1}")

        ResumeOutlinedTextField(
            value = experience.companyName,
            onValueChange = {
                val newList = experienceList.toMutableList()
                newList[index] = experience.copy(companyName = it)
                onExperienceChanged(newList)
            },
            label = "Company"
        )

        ResumeOutlinedTextField(
            value = experience.position,
            onValueChange = {
                val newList = experienceList.toMutableList()
                newList[index] = experience.copy(position = it)
                onExperienceChanged(newList)
            },
            label = "Position"
        )

        ResumeOutlinedTextField(
            value = experience.duration,
            onValueChange = {
                val newList = experienceList.toMutableList()
                newList[index] = experience.copy(duration = it)
                onExperienceChanged(newList)
            },
            label = "Duration"
        )

        ResumeOutlinedTextField(
            value = experience.responsibilities.joinToString("\n"),
            onValueChange = {
                val newList = experienceList.toMutableList()
                newList[index] = experience.copy(
                    responsibilities = it.split("\n").filter { item -> item.isNotEmpty() }
                )
                onExperienceChanged(newList)
            },
            label = "Responsibilities (one per line)",
            minLines = 3
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}
@Composable
private fun ProjectsSection(
    projectsList: List<Project>,
    onProjectsChanged: (List<Project>) -> Unit
) {
    SectionTitle("Projects")

    AddItemButton(
        text = "Add Project",
        onClick = {
            onProjectsChanged(
                projectsList + Project(
                    name = "",
                    technologies = "",
                    description = emptyList()
                )
            )
        }
    )
    projectsList.forEachIndexed { index, project ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Project ${index + 1}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Remove project button
                    IconButton(
                        onClick = {
                            val newList = projectsList.toMutableList()
                            newList.removeAt(index)
                            onProjectsChanged(newList)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove Project",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

                ResumeOutlinedTextField(
                    value = project.name,
                    onValueChange = {
                        val newList = projectsList.toMutableList()
                        newList[index] = project.copy(name = it)
                        onProjectsChanged(newList)
                    },
                    label = "Project Name"
                )

                ResumeOutlinedTextField(
                    value = project.technologies,
                    onValueChange = {
                        val newList = projectsList.toMutableList()
                        newList[index] = project.copy(technologies = it)
                        onProjectsChanged(newList)
                    },
                    label = "Technologies Used"
                )

                ResumeOutlinedTextField(
                    value = project.demoLink ?: "",
                    onValueChange = {
                        val newList = projectsList.toMutableList()
                        newList[index] = project.copy(demoLink = it.ifEmpty { null })
                        onProjectsChanged(newList)
                    },
                    label = "Demo Link (Optional)"
                )

                ResumeOutlinedTextField(
                    value = project.description.joinToString("\n"),
                    onValueChange = {
                        val newList = projectsList.toMutableList()
                        newList[index] = project.copy(
                            description = it.split("\n").filter { item -> item.isNotEmpty() }
                        )
                        onProjectsChanged(newList)
                    },
                    label = "Description (one per line)",
                    minLines = 3
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun CertificationsSection(
    certificationsList: List<Certification>,
    onCertificationsChanged: (List<Certification>) -> Unit
) {
    SectionTitle("Certifications")

    AddItemButton(
        text = "Add Certification",
        onClick = {
            onCertificationsChanged(
                certificationsList + Certification(
                    title = "",
                    organization = ""
                )
            )
        }
    )

    certificationsList.forEachIndexed { index, certification ->
        Spacer(modifier = Modifier.height(16.dp))
        Text("Certification ${index + 1}")

        ResumeOutlinedTextField(
            value = certification.title,
            onValueChange = {
                val newList = certificationsList.toMutableList()
                newList[index] = certification.copy(title = it)
                onCertificationsChanged(newList)
            },
            label = "Title"
        )

        ResumeOutlinedTextField(
            value = certification.organization,
            onValueChange = {
                val newList = certificationsList.toMutableList()
                newList[index] = certification.copy(organization = it)
                onCertificationsChanged(newList)
            },
            label = "Organization"
        )

        ResumeOutlinedTextField(
            value = certification.verificationId ?: "",
            onValueChange = {
                val newList = certificationsList.toMutableList()
                newList[index] = certification.copy(verificationId = it.ifEmpty { null })
                onCertificationsChanged(newList)
            },
            label = "Verification ID (Optional)"
        )

        ResumeOutlinedTextField(
            value = certification.verificationLink ?: "",
            onValueChange = {
                val newList = certificationsList.toMutableList()
                newList[index] = certification.copy(verificationLink = it.ifEmpty { null })
                onCertificationsChanged(newList)
            },
            label = "Verification Link (Optional)"
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun SkillsSection(
    skills: TechnicalSkills,
    onSkillsChanged: (TechnicalSkills) -> Unit
) {
    SectionTitle("Technical Skills")

    ResumeOutlinedTextField(
        value = skills.languages.joinToString(", "),
        onValueChange = {
            onSkillsChanged(skills.copy(languages = it.split(",").map { s -> s.trim() }.filter { s -> s.isNotEmpty() }))
        },
        label = "Programming Languages (comma-separated)"
    )

    ResumeOutlinedTextField(
        value = skills.databases.joinToString(", "),
        onValueChange = {
            onSkillsChanged(skills.copy(databases = it.split(",").map { s -> s.trim() }.filter { s -> s.isNotEmpty() }))
        },
        label = "Databases (comma-separated)"
    )

    ResumeOutlinedTextField(
        value = skills.frameworks.joinToString(", "),
        onValueChange = {
            onSkillsChanged(skills.copy(frameworks = it.split(",").map { s -> s.trim() }.filter { s -> s.isNotEmpty() }))
        },
        label = "Frameworks (comma-separated)"
    )

    ResumeOutlinedTextField(
        value = skills.developerTools.joinToString(", "),
        onValueChange = {
            onSkillsChanged(skills.copy(developerTools = it.split(",").map { s -> s.trim() }.filter { s -> s.isNotEmpty() }))
        },
        label = "Developer Tools (comma-separated)"
    )

    ResumeOutlinedTextField(
        value = skills.skills.joinToString(", "),
        onValueChange = {
            onSkillsChanged(skills.copy(skills = it.split(",").map { s -> s.trim() }.filter { s -> s.isNotEmpty() }))
        },
        label = "Other Skills (comma-separated)"
    )

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ActionButtons(
    onPreview: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onPreview,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text("Preview")
        }
        Button(
            onClick = onSave,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Save Resume")
        }
    }
}

// Reusable Components
@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResumeOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Blue,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray,
        ),
        minLines = minLines,
        maxLines = maxLines
    )
}

@Composable
private fun AddItemButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(text)
    }
}