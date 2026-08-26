package com.sofajohnlee.eunhyo2.feature.links

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.sofajohnlee.eunhyo2.databinding.ActivityEducationLinksBinding

class EducationLinksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationLinksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationLinksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        renderCatalog()
    }

    private fun renderCatalog() {
        var currentGroup: String? = null
        EducationLinkCatalog.links.forEach { item ->
            if (currentGroup != item.group) {
                currentGroup = item.group
                binding.linkContainer.addView(TextView(this).apply {
                    text = item.group
                    textSize = 18f
                    setPadding(0, 24, 0, 8)
                })
            }

            binding.linkContainer.addView(MaterialButton(this).apply {
                text = item.title
                isAllCaps = false
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                setOnClickListener { open(item.url) }
            })
        }
    }

    private fun open(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
