package com.sofajohnlee.eunhyo2.feature.ai

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityAiChatBinding
import kotlinx.coroutines.launch

class AiChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAiChatBinding
    private val viewModel: AiChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSend.setOnClickListener {
            val message = binding.editMessage.text?.toString().orEmpty()
            viewModel.send(message)
            binding.editMessage.text?.clear()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collect { messages ->
                    binding.textConversation.text = messages.joinToString("\n\n") {
                        (if (it.fromUser) "나: " else "은효: ") + it.text
                    }
                }
            }
        }
    }
}
