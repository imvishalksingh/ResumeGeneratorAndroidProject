package com.productivityservicehub.creaftmyresume.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.productivityservicehub.creaftmyresume.model.ResumeInfo
import java.text.SimpleDateFormat
import java.util.*

class ResumeStorage(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("resumes", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveResume(resumeInfo: ResumeInfo, templateName: String) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val resumeKey = "resume_${templateName}_$timeStamp"
        val resumeJson = gson.toJson(resumeInfo)
        
        // Save the resume data
        prefs.edit()
            .putString(resumeKey, resumeJson)
            .apply()

        // Save the resume key to the list of saved resumes
        val savedResumes = getSavedResumeKeys().toMutableSet()
        savedResumes.add(resumeKey)
        prefs.edit()
            .putStringSet("saved_resume_keys", savedResumes)
            .apply()
    }

    fun loadResume(resumeKey: String): ResumeInfo? {
        val resumeJson = prefs.getString(resumeKey, null) ?: return null
        return try {
            gson.fromJson(resumeJson, ResumeInfo::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun getSavedResumeKeys(): Set<String> {
        return prefs.getStringSet("saved_resume_keys", emptySet()) ?: emptySet()
    }

    fun deleteResume(resumeKey: String) {
        // Remove the resume data
        prefs.edit()
            .remove(resumeKey)
            .apply()

        // Remove the key from the list of saved resumes
        val savedResumes = getSavedResumeKeys().toMutableSet()
        savedResumes.remove(resumeKey)
        prefs.edit()
            .putStringSet("saved_resume_keys", savedResumes)
            .apply()
    }

    fun getResumeMetadata(resumeKey: String): ResumeMetadata? {
        val resume = loadResume(resumeKey) ?: return null
        val parts = resumeKey.split("_")
        if (parts.size < 3) return null

        return ResumeMetadata(
            key = resumeKey,
            templateName = parts[1],
            timestamp = parts[2],
            name = resume.personalInfo.name,
            email = resume.personalInfo.email
        )
    }
}

data class ResumeMetadata(
    val key: String,
    val templateName: String,
    val timestamp: String,
    val name: String,
    val email: String
) 