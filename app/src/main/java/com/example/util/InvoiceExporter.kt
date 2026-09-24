package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.model.ExtractionLogEntity
import com.example.model.RecipeEntity
import com.example.model.UserProfile
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object InvoiceExporter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    private val dateOnlyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun exportPdfInvoice(
        context: Context,
        profile: UserProfile,
        recipes: List<RecipeEntity>,
        logs: List<ExtractionLogEntity>
    ): File {
        val invoicesDir = File(context.cacheDir, "invoices").apply { mkdirs() }
        val file = File(invoicesDir, "RECIPEPOCKET_Invoice_${System.currentTimeMillis()}.pdf")

        val pdfDoc = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standard A4 (595 x 842 points)
        val page = pdfDoc.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Background
        canvas.drawColor(Color.WHITE)

        // Header Background Bar
        paint.color = Color.rgb(33, 21, 16) // Rich Espresso
        canvas.drawRect(0f, 0f, 595f, 90f, paint)

        // Header Title
        paint.color = Color.WHITE
        paint.textSize = 22f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("RECIPEPOCKET", 40f, 45f, paint)

        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.color = Color.rgb(220, 180, 150)
        canvas.drawText("BREW. CREATE. REMEMBER. • ARTISAN BARISTA INVOICE", 40f, 65f, paint)

        // Invoice Number & Date (Header Right)
        val invNumber = "RP-INV-${System.currentTimeMillis() % 1000000}"
        paint.color = Color.WHITE
        paint.textSize = 10f
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("INVOICE #: $invNumber", 555f, 45f, paint)
        canvas.drawText("DATE: ${dateFormat.format(Date())}", 555f, 65f, paint)
        paint.textAlign = Paint.Align.LEFT

        var y = 120f

        // Barista / Station Info
        paint.color = Color.rgb(40, 40, 40)
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("BARISTA STATION DETAILS:", 40f, y, paint)

        y += 18f
        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.color = Color.rgb(80, 80, 80)
        canvas.drawText("Barista: ${profile.displayName} (${profile.experienceLevel})", 40f, y, paint)
        canvas.drawText("Preferred Coffee: ${profile.preferredCoffee} • Units: ${profile.measurementUnit.name}", 320f, y, paint)

        y += 16f
        canvas.drawText("Total Active Recipes: ${recipes.size} • Total Logged Extractions: ${logs.size}", 40f, y, paint)

        // Divider
        y += 15f
        paint.color = Color.rgb(220, 220, 220)
        paint.strokeWidth = 1f
        canvas.drawLine(40f, y, 555f, y, paint)

        // Table Header
        y += 25f
        paint.color = Color.rgb(245, 240, 235)
        canvas.drawRect(40f, y - 14f, 555f, y + 10f, paint)

        paint.color = Color.rgb(40, 40, 40)
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("ITEM / COFFEE", 48f, y, paint)
        canvas.drawText("METHOD", 220f, y, paint)
        canvas.drawText("DOSE / YIELD", 310f, y, paint)
        canvas.drawText("TIME / RATIO", 410f, y, paint)
        canvas.drawText("RATING / TYPE", 485f, y, paint)

        y += 20f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.color = Color.rgb(60, 60, 60)

        // Rows from extraction logs & recipes (up to 15 items to fit nicely on page)
        val displayItems = logs.take(10)
        var totalCoffeeBeansGrams = 0f

        for (log in displayItems) {
            totalCoffeeBeansGrams += log.doseGrams
            val ratio = if (log.doseGrams > 0) String.format(Locale.US, "1:%.1f", log.yieldGrams / log.doseGrams) else "N/A"

            canvas.drawText(log.coffeeBean.take(25), 48f, y, paint)
            canvas.drawText(log.method.take(15), 220f, y, paint)
            canvas.drawText("${log.doseGrams}g / ${log.yieldGrams}g", 310f, y, paint)
            canvas.drawText("${log.brewTimeSeconds}s ($ratio)", 410f, y, paint)
            canvas.drawText("${log.rating}/5 Stars", 485f, y, paint)

            y += 16f
        }

        // Add some signature custom recipes if logs were few
        if (displayItems.size < 6) {
            for (rec in recipes.take(6 - displayItems.size)) {
                canvas.drawText(rec.title.take(25), 48f, y, paint)
                canvas.drawText(rec.brewMethod.take(15), 220f, y, paint)
                canvas.drawText("Std Recipe", 310f, y, paint)
                canvas.drawText("${rec.brewTimeSeconds}s (${rec.recommendedRatio})", 410f, y, paint)
                canvas.drawText(if (rec.isCustom) "Custom" else "Classic", 485f, y, paint)
                y += 16f
            }
        }

        // Divider
        y += 15f
        paint.color = Color.rgb(200, 200, 200)
        canvas.drawLine(40f, y, 555f, y, paint)

        // Summary Totals
        y += 20f
        paint.color = Color.rgb(248, 245, 242)
        canvas.drawRoundRect(300f, y, 555f, y + 65f, 8f, 8f, paint)

        paint.color = Color.rgb(33, 21, 16)
        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("AUDIT & CONSUMPTION SUMMARY", 315f, y + 18f, paint)

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 9f
        paint.color = Color.rgb(60, 60, 60)
        canvas.drawText("Specialty Coffee Dosed:", 315f, y + 34f, paint)
        canvas.drawText(String.format(Locale.US, "%.1f g", totalCoffeeBeansGrams), 490f, y + 34f, paint)

        canvas.drawText("Total Audit Entries:", 315f, y + 50f, paint)
        canvas.drawText("${logs.size} extractions", 490f, y + 50f, paint)

        // Notes and Disclaimer
        y += 85f
        paint.textSize = 9f
        paint.color = Color.rgb(120, 120, 120)
        canvas.drawText("Notes & Quality Sign-Off:", 40f, y, paint)
        y += 14f
        canvas.drawText("All coffee extractions calibrated to specialty SCA standards with local offline logging.", 40f, y, paint)
        y += 14f
        canvas.drawText("Verified Barista Signature: ____________________________________", 40f, y, paint)

        // Bottom Footer
        paint.color = Color.rgb(180, 180, 180)
        paint.textSize = 8f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Generated by RECIPEPOCKET • Offline Barista Companion • Confidential & Station Protected", 297f, 810f, paint)

        pdfDoc.finishPage(page)

        FileOutputStream(file).use { out ->
            pdfDoc.writeTo(out)
        }
        pdfDoc.close()

        return file
    }

    fun exportCsvInvoice(
        context: Context,
        profile: UserProfile,
        recipes: List<RecipeEntity>,
        logs: List<ExtractionLogEntity>
    ): File {
        val invoicesDir = File(context.cacheDir, "invoices").apply { mkdirs() }
        val file = File(invoicesDir, "RECIPEPOCKET_Invoice_${System.currentTimeMillis()}.csv")

        val sb = StringBuilder()
        sb.append("RECIPEPOCKET BARISTA INVOICE & EXTRACTION LOG\n")
        sb.append("Invoice Date,${dateFormat.format(Date())}\n")
        sb.append("Barista Name,\"${profile.displayName}\"\n")
        sb.append("Experience Level,\"${profile.experienceLevel}\"\n")
        sb.append("Preferred Coffee,\"${profile.preferredCoffee}\"\n\n")

        sb.append("--- EXTRACTION ENTRIES ---\n")
        sb.append("ID,Timestamp,Coffee Name,Origin/Roast,Brew Method,Grind Size,Dose (g),Yield (g),Ratio,Brew Time (s),Rating,Flavor Notes\n")

        for (log in logs) {
            val ratio = if (log.doseGrams > 0) String.format(Locale.US, "1:%.1f", log.yieldGrams / log.doseGrams) else "N/A"
            val cleanNotes = log.tastingNotes.replace("\"", "\"\"")
            val cleanOrigin = log.roaster.replace("\"", "\"\"")
            sb.append("${log.id},\"${dateFormat.format(Date(log.dateMillis))}\",\"${log.coffeeBean}\",\"$cleanOrigin\",\"${log.method}\",\"${log.grindSetting}\",${log.doseGrams},${log.yieldGrams},\"$ratio\",${log.brewTimeSeconds},${log.rating},\"$cleanNotes\"\n")
        }

        sb.append("\n--- RECIPE CATALOG ENTRIES ---\n")
        sb.append("Recipe ID,Title,Category,Brew Method,Difficulty,Ratio,Grind,Water Temp,Prep (min),Brew (s),Custom Recipe\n")
        for (r in recipes) {
            sb.append("\"${r.id}\",\"${r.title}\",\"${r.category}\",\"${r.brewMethod}\",\"${r.difficulty}\",\"${r.recommendedRatio}\",\"${r.grindSize}\",\"${r.waterTemperature}\",${r.prepTimeMinutes},${r.brewTimeSeconds},${r.isCustom}\n")
        }

        FileOutputStream(file).use { out ->
            out.write(sb.toString().toByteArray(Charsets.UTF_8))
        }

        return file
    }

    fun exportTxtInvoice(
        context: Context,
        profile: UserProfile,
        recipes: List<RecipeEntity>,
        logs: List<ExtractionLogEntity>
    ): File {
        val invoicesDir = File(context.cacheDir, "invoices").apply { mkdirs() }
        val file = File(invoicesDir, "RECIPEPOCKET_Invoice_${System.currentTimeMillis()}.txt")

        val sb = StringBuilder()
        val sep = "========================================================================\n"
        val thinSep = "------------------------------------------------------------------------\n"

        sb.append(sep)
        sb.append("                       RECIPEPOCKET BARISTA INVOICE                     \n")
        sb.append("                       Brew. Create. Remember.                          \n")
        sb.append(sep)
        sb.append("Invoice No    : RP-INV-${System.currentTimeMillis() % 1000000}\n")
        sb.append("Issued Date   : ${dateFormat.format(Date())}\n")
        sb.append("Barista Alias : ${profile.displayName} [${profile.experienceLevel}]\n")
        sb.append("Preferred Cup : ${profile.preferredCoffee} • Units: ${profile.measurementUnit.name}\n")
        sb.append(thinSep)
        sb.append(String.format("%-24s | %-12s | %-12s | %-8s | %-5s\n", "ITEM / COFFEE", "METHOD", "DOSE/YIELD", "TIME", "SCORE"))
        sb.append(thinSep)

        var totalDose = 0f
        var count = 0

        for (log in logs) {
            totalDose += log.doseGrams
            count++
            val itemStr = log.coffeeBean.take(23)
            val methodStr = log.method.take(12)
            val doseYieldStr = "${log.doseGrams}g/${log.yieldGrams}g"
            val timeStr = "${log.brewTimeSeconds}s"
            val scoreStr = "${log.rating}/5"

            sb.append(String.format("%-24s | %-12s | %-12s | %-8s | %-5s\n", itemStr, methodStr, doseYieldStr, timeStr, scoreStr))
        }

        if (count == 0) {
            for (r in recipes.take(10)) {
                val itemStr = r.title.take(23)
                val methodStr = r.brewMethod.take(12)
                val doseYieldStr = r.recommendedRatio
                val timeStr = "${r.brewTimeSeconds}s"
                val scoreStr = if (r.isCustom) "Custom" else "Base"
                sb.append(String.format("%-24s | %-12s | %-12s | %-8s | %-5s\n", itemStr, methodStr, doseYieldStr, timeStr, scoreStr))
            }
        }

        sb.append(sep)
        sb.append("TOTAL COFFEE CONSUMED : ${String.format(Locale.US, "%.1f g", totalDose)}\n")
        sb.append("TOTAL DIAL-IN SESSIONS: $count entries\n")
        sb.append(sep)
        sb.append("Quality Statement: Certified calibrated extraction report generated\n")
        sb.append("locally on-device via RECIPEPOCKET SQLite database.\n\n")
        sb.append("Sign-off: __________________________________________________\n")
        sb.append(sep)

        FileOutputStream(file).use { out ->
            out.write(sb.toString().toByteArray(Charsets.UTF_8))
        }

        return file
    }

    fun shareFile(context: Context, file: File, mimeType: String, subject: String) {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, "Here is my exported coffee invoice & brew audit from RECIPEPOCKET.")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(intent, "Share Invoice via")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}
