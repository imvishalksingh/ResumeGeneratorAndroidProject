package com.productivityservicehub.creaftmyresume.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

fun showPdfFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    context.startActivity(
        Intent.createChooser(intent, "Open PDF")
    )
} 