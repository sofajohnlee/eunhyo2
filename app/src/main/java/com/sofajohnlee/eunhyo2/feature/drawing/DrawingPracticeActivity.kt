package com.sofajohnlee.eunhyo2.feature.drawing

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityDrawingPracticeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DrawingPracticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingPracticeBinding
    private var pendingBitmap: Bitmap? = null

    private val createDocument = registerForActivityResult(
        ActivityResultContracts.CreateDocument("image/png")
    ) { uri ->
        val bitmap = pendingBitmap
        pendingBitmap = null
        if (uri != null && bitmap != null) {
            saveBitmap(uri, bitmap)
        }
    }

    private val openDocument = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            binding.imageBackground.setImageURI(uri)
            binding.drawingView.clear()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClear.setOnClickListener {
            binding.drawingView.clear()
            binding.imageBackground.setImageDrawable(null)
        }
        binding.buttonOpen.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }
        binding.buttonSave.setOnClickListener {
            pendingBitmap = captureCanvas()
            val stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
            createDocument.launch("eunhyo_drawing_$stamp.png")
        }
    }

    private fun captureCanvas(): Bitmap {
        val container = binding.drawingCanvasContainer
        return Bitmap.createBitmap(
            container.width.coerceAtLeast(1),
            container.height.coerceAtLeast(1),
            Bitmap.Config.ARGB_8888,
        ).also { bitmap ->
            val canvas = Canvas(bitmap)
            canvas.drawColor(android.graphics.Color.WHITE)
            container.draw(canvas)
        }
    }

    private fun saveBitmap(uri: Uri, bitmap: Bitmap) {
        val saved = runCatching {
            contentResolver.openOutputStream(uri)?.use { output ->
                check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output))
            } ?: error("출력 스트림을 열 수 없습니다.")
        }.isSuccess

        Toast.makeText(
            this,
            if (saved) "그림을 저장했습니다." else "그림 저장에 실패했습니다.",
            Toast.LENGTH_SHORT,
        ).show()
    }
}
