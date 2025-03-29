package com.productivityservicehub.creaftmyresume.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.productivityservicehub.creaftmyresume.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsSection(
    projects: List<Project>,
    onProjectsChange: (List<Project>) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add Project")
            }
        }

        LazyColumn {
            items(projects) { project ->
                ProjectCard(
                    project = project,
                    onDelete = {
                        onProjectsChange(projects.filter { it != project })
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var technologies by remember { mutableStateOf("") }
        var demoLink by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Project") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Project Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = technologies,
                        onValueChange = { technologies = it },
                        label = { Text("Technologies Used") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = demoLink,
                        onValueChange = { demoLink = it },
                        label = { Text("Demo Link (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description (separate points with new lines)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newProject = Project(
                            name = name,
                            technologies = technologies,
                            demoLink = demoLink.takeIf { it.isNotEmpty() },
                            description = description.split("\n").filter { it.isNotEmpty() }
                        )
                        onProjectsChange(projects + newProject)
                        showAddDialog = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationSection(
    education: List<Education>,
    onEducationChange: (List<Education>) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Education",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add Education")
            }
        }

        LazyColumn {
            items(education) { edu ->
                EducationCard(
                    education = edu,
                    onDelete = {
                        onEducationChange(education.filter { it != edu })
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        var instituteName by remember { mutableStateOf("") }
        var degree by remember { mutableStateOf("") }
        var duration by remember { mutableStateOf("") }
        var score by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Education") },
            text = {
                Column {
                    OutlinedTextField(
                        value = instituteName,
                        onValueChange = { instituteName = it },
                        label = { Text("Institute Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = degree,
                        onValueChange = { degree = it },
                        label = { Text("Degree") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = duration,
                        onValueChange = { duration = it },
                        label = { Text("Duration") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = score,
                        onValueChange = { score = it },
                        label = { Text("Score/Grade") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newEducation = Education(
                            instituteName = instituteName,
                            degree = degree,
                            duration = duration,
                            score = score
                        )
                        onEducationChange(education + newEducation)
                        showAddDialog = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificationsSection(
    certifications: List<Certification>,
    onCertificationsChange: (List<Certification>) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Training & Certifications",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add Certification")
            }
        }

        LazyColumn {
            items(certifications) { cert ->
                CertificationCard(
                    certification = cert,
                    onDelete = {
                        onCertificationsChange(certifications.filter { it != cert })
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        var title by remember { mutableStateOf("") }
        var organization by remember { mutableStateOf("") }
        var verificationId by remember { mutableStateOf("") }
        var verificationLink by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Certification") },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = organization,
                        onValueChange = { organization = it },
                        label = { Text("Organization") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = verificationId,
                        onValueChange = { verificationId = it },
                        label = { Text("Verification ID (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = verificationLink,
                        onValueChange = { verificationLink = it },
                        label = { Text("Verification Link (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newCertification = Certification(
                            title = title,
                            organization = organization,
                            verificationId = verificationId.takeIf { it.isNotEmpty() },
                            verificationLink = verificationLink.takeIf { it.isNotEmpty() }
                        )
                        onCertificationsChange(certifications + newCertification)
                        showAddDialog = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechnicalSkillsSection(
    skills: TechnicalSkills,
    onSkillsChange: (TechnicalSkills) -> Unit
) {
    Column {
        Text(
            text = "Technical Skills",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = skills.languages.joinToString(", "),
            onValueChange = { 
                onSkillsChange(skills.copy(
                    languages = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                ))
            },
            label = { Text("Programming Languages") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = skills.databases.joinToString(", "),
            onValueChange = { 
                onSkillsChange(skills.copy(
                    databases = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                ))
            },
            label = { Text("Databases") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = skills.frameworks.joinToString(", "),
            onValueChange = { 
                onSkillsChange(skills.copy(
                    frameworks = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                ))
            },
            label = { Text("Frameworks") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = skills.developerTools.joinToString(", "),
            onValueChange = { 
                onSkillsChange(skills.copy(
                    developerTools = it.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                ))
            },
            label = { Text("Developer Tools") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceSection(
    experience: List<Experience>,
    onExperienceChange: (List<Experience>) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Experience",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Add Experience")
            }
        }

        LazyColumn {
            items(experience) { exp ->
                ExperienceCard(
                    experience = exp,
                    onDelete = {
                        onExperienceChange(experience.filter { it != exp })
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        var companyName by remember { mutableStateOf("") }
        var position by remember { mutableStateOf("") }
        var duration by remember { mutableStateOf("") }
        var responsibilities by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Experience") },
            text = {
                Column {
                    OutlinedTextField(
                        value = companyName,
                        onValueChange = { companyName = it },
                        label = { Text("Company Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = position,
                        onValueChange = { position = it },
                        label = { Text("Position") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = duration,
                        onValueChange = { duration = it },
                        label = { Text("Duration") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = responsibilities,
                        onValueChange = { responsibilities = it },
                        label = { Text("Responsibilities (separate points with new lines)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newExperience = Experience(
                            companyName = companyName,
                            position = position,
                            duration = duration,
                            responsibilities = responsibilities.split("\n").filter { it.isNotEmpty() }
                        )
                        onExperienceChange(experience + newExperience)
                        showAddDialog = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ProjectCard(
    project: Project,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete Project")
                }
            }
            Text(
                text = project.technologies,
                style = MaterialTheme.typography.bodyMedium
            )
            project.demoLink?.let {
                Text(
                    text = "Demo: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            project.description.forEach { point ->
                Text(
                    text = "• $point",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun EducationCard(
    education: Education,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = education.instituteName,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete Education")
                }
            }
            Text(
                text = education.degree,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${education.duration} | ${education.score}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun CertificationCard(
    certification: Certification,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = certification.title,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete Certification")
                }
            }
            Text(
                text = certification.organization,
                style = MaterialTheme.typography.bodyMedium
            )
            certification.verificationId?.let {
                Text(
                    text = "ID: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            certification.verificationLink?.let {
                Text(
                    text = "Verify: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ExperienceCard(
    experience: Experience,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = experience.companyName,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete Experience")
                }
            }
            Text(
                text = experience.position,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = experience.duration,
                style = MaterialTheme.typography.bodyMedium
            )
            experience.responsibilities.forEach { responsibility ->
                Text(
                    text = "• $responsibility",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
} 