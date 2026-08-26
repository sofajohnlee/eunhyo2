package com.sofajohnlee.eunhyo2.feature.doc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/** Modern replacement for Main_EngExpert. */
class PdfLibraryActivity : AppCompatActivity() {
    private val openPdf = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) return@registerForActivityResult
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        runCatching { startActivity(intent) }
            .onFailure { Toast.makeText(this, "PDF를 열 수 있는 앱이 없습니다.", Toast.LENGTH_SHORT).show() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val padding = (24 * resources.displayMetrics.density).toInt()
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(padding, padding, padding, padding)
        }
        root.addView(TextView(this).apply {
            text = "영어 전문가 PDF 자료"
            textSize = 24f
        })
        root.addView(TextView(this).apply {
            text = "원본의 r1.pdf, r2.pdf, Origami.pdf, Wrect_It_Ralph.pdf 같은 외부 PDF를 시스템 파일 선택기로 안전하게 엽니다."
        })
        root.addView(Button(this).apply {
            text = "PDF 선택"
            setOnClickListener { openPdf.launch(arrayOf("application/pdf")) }
        })
        setContentView(root)
    }
}
