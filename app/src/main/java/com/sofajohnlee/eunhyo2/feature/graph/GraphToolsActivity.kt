package com.sofajohnlee.eunhyo2.feature.graph

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityGraphToolsBinding

class GraphToolsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGraphToolsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphToolsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpenDesmos.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DESMOS_URL)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
            }
            try {
                startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(this, "웹 브라우저를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val DESMOS_URL = "https://www.desmos.com/calculator"
    }
}
