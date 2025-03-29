package com.productivityservicehub.creaftmyresume.util

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.productivityservicehub.creaftmyresume.model.ResumeInfo
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PDFGenerator(private val context: Context) {
    private val pageWidth = 612f  // Letter size width in points
    private val pageHeight = 792f // Letter size height in points
    private val margin = 72f      // 1-inch margin
    private val lineHeight = 18f
    private val sectionSpacing = 20f
    private val bulletIndent = 15f
    private val footerMargin = 50f // Margin from bottom for page number

    // Colors
    private val darkGreen = Color.rgb(0, 102, 51)

    // Track current page and position
    private var currentPage = 1
    private lateinit var document: PdfDocument
    private lateinit var canvas: Canvas
    private var yPosition = margin
    private lateinit var currentPdfPage: PdfDocument.Page

    fun generatePdf(resumeInfo: ResumeInfo, templateName: String): File {
        document = PdfDocument()
        startNewPage()

        // Set up paints
        val headerPaint = TextPaint().apply {
            color = darkGreen
            textSize = 24f
            typeface = Typeface.create("Times New Roman", Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }

        val linePaint = Paint().apply {
            color = darkGreen
            strokeWidth = 0.8f
            style = Paint.Style.STROKE
        }

        val sectionHeaderPaint = TextPaint().apply {
            color = darkGreen
            textSize = 14f
            typeface = Typeface.create("Times New Roman", Typeface.BOLD)
        }

        val normalPaint = TextPaint().apply {
            color = Color.BLACK
            textSize = 11f
            typeface = Typeface.create("Times New Roman", Typeface.NORMAL)
        }

        val boldPaint = TextPaint(normalPaint).apply {
            typeface = Typeface.create("Times New Roman", Typeface.BOLD)
        }

        // Draw name
        canvas.drawText(resumeInfo.personalInfo.name.uppercase(), pageWidth / 2, yPosition, headerPaint)
        yPosition += lineHeight * 1.5f

        // Contact information in a single line
        val contactInfo = mutableListOf<String>()
        resumeInfo.personalInfo.phone.takeIf { it.isNotEmpty() }?.let { contactInfo.add(it) }
        resumeInfo.personalInfo.email.takeIf { it.isNotEmpty() }?.let { contactInfo.add(it) }
        resumeInfo.personalInfo.linkedIn.takeIf { it.isNotEmpty() }?.let { contactInfo.add("iamvishalksingh") }
        resumeInfo.personalInfo.github.takeIf { it.isNotEmpty() }?.let { contactInfo.add("imvishalksingh") }
        resumeInfo.personalInfo.leetcode.takeIf { it.isNotEmpty() }?.let { contactInfo.add("LeetCode") }

        val contactText = contactInfo.joinToString(" • ")
        val contactPaint = TextPaint(normalPaint).apply {
            textAlign = Paint.Align.CENTER
            textSize = 10f
        }
        canvas.drawText(contactText, pageWidth / 2, yPosition, contactPaint)
        yPosition += lineHeight * 1.5f

        // Summary
        if (resumeInfo.summary.isNotEmpty()) {
            checkAndStartNewPageIfNeeded(lineHeight * 4)
            canvas.drawText("SUMMARY", margin, yPosition, sectionHeaderPaint)
            yPosition += lineHeight / 2
            canvas.drawLine(margin, yPosition, pageWidth - margin, yPosition, linePaint)
            yPosition += lineHeight

            val summaryHeight = getTextHeight(resumeInfo.summary, normalPaint, pageWidth - margin * 2)
            checkAndStartNewPageIfNeeded(summaryHeight + lineHeight)
            drawWrappedText(canvas, resumeInfo.summary, margin, yPosition, normalPaint, pageWidth - margin * 2)
            yPosition += summaryHeight + lineHeight
        }

        // Projects
        if (resumeInfo.projects.isNotEmpty()) {
            checkAndStartNewPageIfNeeded(lineHeight * 3)
            canvas.drawText("PROJECTS", margin, yPosition, sectionHeaderPaint)
            yPosition += lineHeight / 2
            canvas.drawLine(margin, yPosition, pageWidth - margin, yPosition, linePaint)
            yPosition += lineHeight

            resumeInfo.projects.forEach { project ->
                checkAndStartNewPageIfNeeded(lineHeight * 2)
                
                // Project name and technologies
                val projectName = project.name
                val technologies = project.technologies
                
                // Draw project name in bold
                canvas.drawText(projectName, margin, yPosition, boldPaint)
                
                // Calculate width of project name to position technologies
                val projectNameWidth = boldPaint.measureText(projectName)
                
                // Draw technologies after project name
                canvas.drawText("| $technologies", margin + projectNameWidth + 10, yPosition, normalPaint)
                
                // Draw demo link if available
                project.demoLink?.let {
                    val demoText = "[ Demo ]"
                    val demoWidth = normalPaint.measureText(demoText)
                    canvas.drawText(demoText, pageWidth - margin - demoWidth, yPosition, normalPaint)
                }
                
                yPosition += lineHeight

                // Project description points
                project.description.forEach { desc ->
                    val bulletText = "– $desc"
                    val descHeight = getTextHeight(bulletText, normalPaint, pageWidth - margin * 2 - bulletIndent)
                    checkAndStartNewPageIfNeeded(descHeight + lineHeight/2)
                    
                    drawWrappedText(canvas, bulletText, margin + bulletIndent, yPosition, normalPaint, pageWidth - margin * 2 - bulletIndent)
                    yPosition += descHeight + lineHeight/2
                }
            }
        }

        // Education
        if (resumeInfo.education.isNotEmpty()) {
            checkAndStartNewPageIfNeeded(lineHeight * 4)
            yPosition += sectionSpacing
            canvas.drawText("EDUCATION", margin, yPosition, sectionHeaderPaint)
            yPosition += lineHeight / 2
            canvas.drawLine(margin, yPosition, pageWidth - margin, yPosition, linePaint)
            yPosition += lineHeight

            resumeInfo.education.forEach { edu ->
                checkAndStartNewPageIfNeeded(lineHeight * 3)
                canvas.drawText(edu.instituteName, margin, yPosition, boldPaint)
                
                // Draw duration on the right
                val durationText = "${edu.duration}"
                val durationWidth = normalPaint.measureText(durationText)
                canvas.drawText(durationText, pageWidth - margin - durationWidth, yPosition, normalPaint)
                
                yPosition += lineHeight
                
                // Degree and CGPA
                val eduDetails = "Bachelor of technology in ${edu.degree}"
                val italicPaint = TextPaint(normalPaint).apply {
                    typeface = Typeface.create("Times New Roman", Typeface.ITALIC)
                }
                canvas.drawText(eduDetails, margin, yPosition, italicPaint)
                
                val cgpaText = "CGPA- ${edu.score}"
                val cgpaWidth = normalPaint.measureText(cgpaText)
                canvas.drawText(cgpaText, pageWidth - margin - cgpaWidth, yPosition, normalPaint)
                
                yPosition += lineHeight * 1.5f
            }
        }

        // Technical Skills
        if (resumeInfo.technicalSkills.languages.isNotEmpty() || 
            resumeInfo.technicalSkills.databases.isNotEmpty() || 
            resumeInfo.technicalSkills.frameworks.isNotEmpty() || 
            resumeInfo.technicalSkills.developerTools.isNotEmpty()) {
            
            checkAndStartNewPageIfNeeded(lineHeight * 4)
            yPosition += sectionSpacing
            canvas.drawText("TECHNICAL SKILLS", margin, yPosition, sectionHeaderPaint)
            yPosition += lineHeight / 2
            canvas.drawLine(margin, yPosition, pageWidth - margin, yPosition, linePaint)
            yPosition += lineHeight

            val skillsList = mutableListOf<Pair<String, List<String>>>()
            
            if (resumeInfo.technicalSkills.languages.isNotEmpty()) {
                skillsList.add("Languages" to resumeInfo.technicalSkills.languages)
            }
            if (resumeInfo.technicalSkills.frameworks.isNotEmpty()) {
                skillsList.add("Frameworks" to resumeInfo.technicalSkills.frameworks)
            }
            if (resumeInfo.technicalSkills.databases.isNotEmpty()) {
                skillsList.add("Database" to resumeInfo.technicalSkills.databases)
            }
            if (resumeInfo.technicalSkills.developerTools.isNotEmpty()) {
                skillsList.add("Developer Tools" to resumeInfo.technicalSkills.developerTools)
            }

            skillsList.forEach { (category, skills) ->
                val skillText = "$category: ${skills.joinToString(", ")}"
                val skillHeight = getTextHeight(skillText, normalPaint, pageWidth - margin * 2)
                checkAndStartNewPageIfNeeded(skillHeight + lineHeight)
                
                canvas.drawText("– $category:", margin, yPosition, boldPaint)
                drawWrappedText(canvas, skills.joinToString(", "), margin + boldPaint.measureText("– $category: "), yPosition, normalPaint, pageWidth - margin * 2 - bulletIndent)
                yPosition += skillHeight + lineHeight
            }
        }

        // Certifications
        if (resumeInfo.trainingAndCertifications.isNotEmpty()) {
            checkAndStartNewPageIfNeeded(lineHeight * 4)
            yPosition += sectionSpacing
            canvas.drawText("CERTIFICATIONS", margin, yPosition, sectionHeaderPaint)
            yPosition += lineHeight / 2
            canvas.drawLine(margin, yPosition, pageWidth - margin, yPosition, linePaint)
            yPosition += lineHeight

            resumeInfo.trainingAndCertifications.forEach { cert ->
                val certText = "– ${cert.title} - ${cert.organization}"
                val certHeight = getTextHeight(certText, normalPaint, pageWidth - margin * 2)
                checkAndStartNewPageIfNeeded(certHeight + lineHeight)
                
                drawWrappedText(canvas, certText, margin, yPosition, normalPaint, pageWidth - margin * 2)
                yPosition += certHeight + lineHeight
            }
        }

        // Finish the last page
        finishCurrentPage()

        // Save the document
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "Resume_${templateName}_$timeStamp.pdf"
        val file = File(context.getExternalFilesDir(null), fileName)
        FileOutputStream(file).use { out ->
            document.writeTo(out)
        }
        document.close()

        return file
    }

    private fun startNewPage() {
        if (::canvas.isInitialized) {
            finishCurrentPage()
        }
        
        val pageInfo = PageInfo.Builder(pageWidth.toInt(), pageHeight.toInt(), currentPage).create()
        currentPdfPage = document.startPage(pageInfo)
        canvas = currentPdfPage.canvas
        yPosition = margin
        
        // Draw page number at the bottom
        val pageNumberPaint = TextPaint().apply {
            color = Color.GRAY
            textSize = 10f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create("Times New Roman", Typeface.NORMAL)
        }
        canvas.drawText("Page $currentPage", pageWidth / 2, pageHeight - footerMargin, pageNumberPaint)
    }

    private fun finishCurrentPage() {
        if (::currentPdfPage.isInitialized) {
            document.finishPage(currentPdfPage)
        }
    }

    private fun checkAndStartNewPageIfNeeded(neededSpace: Float) {
        if (yPosition + neededSpace > pageHeight - footerMargin) {
            currentPage++
            startNewPage()
        }
    }

    private fun getTextHeight(text: String, paint: TextPaint, maxWidth: Float): Float {
        val layout = StaticLayout.Builder.obtain(text, 0, text.length, paint, maxWidth.toInt())
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1f)
            .setIncludePad(false)
            .build()
        return layout.height.toFloat()
    }

    private fun drawWrappedText(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        paint: TextPaint,
        maxWidth: Float
    ): Float {
        val layout = StaticLayout.Builder.obtain(text, 0, text.length, paint, maxWidth.toInt())
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1f)
            .setIncludePad(false)
            .build()

        canvas.save()
        canvas.translate(x, y)
        layout.draw(canvas)
        canvas.restore()

        return layout.height.toFloat()
    }
} 