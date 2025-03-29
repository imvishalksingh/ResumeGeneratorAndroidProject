package com.productivityservicehub.creaftmyresume.model

data class ResumeInfo(
    val personalInfo: PersonalInfo,
    val summary: String,
    val projects: List<Project>,
    val education: List<Education>,
    val experience: List<Experience>,
    val trainingAndCertifications: List<Certification>,
    val technicalSkills: TechnicalSkills
)

data class PersonalInfo(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val linkedIn: String = "",
    val github: String = "",
    val leetcode: String = "",
    val fullName: String = "",
    val address: String = "",
    val objective: String = ""
)

data class Project(
    val name: String,
    val technologies: String,
    val demoLink: String? = null,
    val description: List<String>
)

data class Education(
    val instituteName: String,
    val degree: String,
    val duration: String,
    val score: String,
    val institute: String = "",
    val year: String = ""
)

data class Certification(
    val title: String,
    val organization: String,
    val verificationId: String? = null,
    val verificationLink: String? = null
)

data class TechnicalSkills(
    val languages: List<String> = listOf(),
    val databases: List<String> = listOf(),
    val frameworks: List<String> = listOf(),
    val developerTools: List<String> = listOf(),
    val skills: List<String> = listOf()
)

data class Experience(
    val companyName: String,
    val position: String,
    val duration: String,
    val responsibilities: List<String>
)

data class ResumeTemplate(
    val id: Int,
    val name: String,
    val description: String
) 