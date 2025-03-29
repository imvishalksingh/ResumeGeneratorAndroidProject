package com.productivityservicehub.creaftmyresume

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.productivityservicehub.creaftmyresume.model.*
import com.productivityservicehub.creaftmyresume.ui.theme.CreaftMyResumeTheme
import com.productivityservicehub.creaftmyresume.util.PDFGenerator
import com.productivityservicehub.creaftmyresume.util.ResumeStorage
import com.productivityservicehub.creaftmyresume.util.showPdfFile

class MainActivity : ComponentActivity() {
    private lateinit var pdfGenerator: PDFGenerator
    private lateinit var resumeStorage: ResumeStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pdfGenerator = PDFGenerator(this)
        resumeStorage = ResumeStorage(this)

        setContent {
            val navController = rememberNavController()
            var currentTemplate by remember { mutableStateOf<ResumeTemplate?>(null) }
            var currentResumeInfo by remember { mutableStateOf<ResumeInfo?>(null) }

            NavHost(navController = navController, startDestination = "saved_resumes") {
                composable("saved_resumes") {
                    val savedResumes by remember {
                        derivedStateOf {
                            resumeStorage.getSavedResumeKeys()
                                .mapNotNull { resumeStorage.getResumeMetadata(it) }
                        }
                    }

                    SavedResumesScreen(
                        savedResumes = savedResumes,
                        onResumeClick = { resumeKey ->
                            val resume = resumeStorage.loadResume(resumeKey)
                            if (resume != null) {
                                currentResumeInfo = resume
                                navController.navigate("resume_info")
                            }
                        },
                        onDeleteResume = { resumeKey ->
                            resumeStorage.deleteResume(resumeKey)
                        },
                        onCreateNewResume = {
                            currentResumeInfo = null
                            navController.navigate("template_selection")
                        }
                    )
                }

                composable("template_selection") {
                    TemplateSelectionScreen(
                        onTemplateSelected = { template ->
                            currentTemplate = template
                            navController.navigate("resume_info")
                        }
                    )
                }

                composable("resume_info") {
                    ResumeInfoScreen(
                        template = currentTemplate ?: ResumeTemplate.TEMPLATE1,
                        initialResumeInfo = currentResumeInfo ?: ResumeInfo(),
                        onNavigateBack = { navController.navigateUp() },
                        onSaveResume = { resumeInfo ->
                            // Save the resume data
                            resumeStorage.saveResume(resumeInfo, currentTemplate?.name ?: "default")
                            
                            // Generate and show PDF
                            val pdfFile = pdfGenerator.generatePdf(resumeInfo, currentTemplate?.name ?: "default")
                            showPdfFile(this@MainActivity, pdfFile)
                            
                            // Navigate back to saved resumes
                            navController.navigate("saved_resumes") {
                                popUpTo("saved_resumes") { inclusive = true }
                            }
                        },
                        onPreviewResume = { resumeInfo ->
                            val pdfFile = pdfGenerator.generatePdf(resumeInfo, currentTemplate?.name ?: "default")
                            showPdfFile(this@MainActivity, pdfFile)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ResumeApp() {
    val navController = rememberNavController()
    var resumeInfoMap by remember { mutableStateOf(mutableMapOf<Int, ResumeInfo>()) }
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "templates") {
        composable("templates") {
            ResumeTemplatesScreen(
                onTemplateSelected = { template ->
                    navController.navigate("resume_info/${template.id}")
                }
            )
        }
        
        composable(
            route = "resume_info/{templateId}",
            arguments = listOf(navArgument("templateId") { type = NavType.IntType })
        ) { backStackEntry ->
            val templateId = backStackEntry.arguments?.getInt("templateId") ?: 1
            val template = getTemplateById(templateId)
            
            // Get existing resume info or create new one
            val existingResumeInfo = resumeInfoMap[templateId]
            
            ResumeInfoScreen(
                template = template,
                initialResumeInfo = existingResumeInfo ?: ResumeInfo(
                    personalInfo = PersonalInfo(),
                    summary = "",
                    projects = emptyList(),
                    education = emptyList(),
                    experience = emptyList(),
                    trainingAndCertifications = emptyList(),
                    technicalSkills = TechnicalSkills()
                ),
                onNavigateBack = { navController.navigateUp() },
                onSaveResume = { resumeInfo ->
                    // Save resume info to map
                    resumeInfoMap = resumeInfoMap.toMutableMap().apply {
                        put(templateId, resumeInfo)
                    }
                    navController.navigateUp()
                },
                onPreviewResume = { resumeInfo ->
                    // Save resume info to map before previewing
                    resumeInfoMap = resumeInfoMap.toMutableMap().apply {
                        put(templateId, resumeInfo)
                    }
                    navController.navigate("preview/${templateId}")
                }
            )
        }
        
        composable(
            route = "preview/{templateId}",
            arguments = listOf(navArgument("templateId") { type = NavType.IntType })
        ) { backStackEntry ->
            val templateId = backStackEntry.arguments?.getInt("templateId") ?: 1
            val template = getTemplateById(templateId)
            val resumeInfo = resumeInfoMap[templateId]
            
            resumeInfo?.let { info ->
                ResumePreviewScreen(
                    resumeInfo = info,
                    onNavigateBack = { navController.navigateUp() },
                    onShareResume = {
                        try {
                            val pdfGenerator = PDFGenerator(context)
                            val file = pdfGenerator.generatePdf(info, template.name)
                            
                            // Get the URI for the file using FileProvider
                            val uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                file
                            )
                            
                            // Create and start the share intent
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "application/pdf"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                putExtra(Intent.EXTRA_SUBJECT, "My Resume")
                            }
                            
                            context.startActivity(Intent.createChooser(shareIntent, "Share Resume"))
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Error sharing resume: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
            }
        }
    }
}

private fun getTemplateById(id: Int): ResumeTemplate {
    return when (id) {
        1 -> ResumeTemplate(1, "Professional", "Clean and modern design for corporate roles")
        2 -> ResumeTemplate(2, "Creative", "Bold and artistic layout for creative industries")
        3 -> ResumeTemplate(3, "Minimal", "Simple and elegant design for any profession")
        4 -> ResumeTemplate(4, "Technical", "Structured layout perfect for technical roles")
        5 -> ResumeTemplate(5, "Executive", "Sophisticated design for senior positions")
        else -> ResumeTemplate(1, "Professional", "Clean and modern design for corporate roles")
    }
}

@Composable
fun ResumeTemplatesScreen(
    onTemplateSelected: (ResumeTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    val templates = listOf(
        ResumeTemplate(1, "Professional", "Clean and modern design for corporate roles"),
        ResumeTemplate(2, "Creative", "Bold and artistic layout for creative industries"),
        ResumeTemplate(3, "Minimal", "Simple and elegant design for any profession"),
        ResumeTemplate(4, "Technical", "Structured layout perfect for technical roles"),
        ResumeTemplate(5, "Executive", "Sophisticated design for senior positions")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Choose Your Template",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(templates) { template ->
                ResumeTemplateCard(
                    template = template,
                    onClick = { onTemplateSelected(template) }
                )
            }
        }
    }
}

@Composable
fun ResumeTemplateCard(
    template: ResumeTemplate,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = template.name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = template.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResumeTemplatesScreenPreview() {
    CreaftMyResumeTheme {
        ResumeTemplatesScreen(onTemplateSelected = {})
    }
}
