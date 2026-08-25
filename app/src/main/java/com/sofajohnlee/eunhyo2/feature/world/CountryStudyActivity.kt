package com.sofajohnlee.eunhyo2.feature.world

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.sofajohnlee.eunhyo2.databinding.ActivityCountryStudyBinding

class CountryStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        render(CountryRepository.countries)
        binding.editSearch.doAfterTextChanged { render(CountryRepository.find(it?.toString().orEmpty())) }
    }

    private fun render(items: List<CountryEntry>) {
        binding.textCountries.text = if (items.isEmpty()) {
            "검색 결과가 없습니다."
        } else {
            items.joinToString("\n\n") { "${it.name} · ${it.region}\n수도: ${it.capital}" }
        }
        binding.textCount.text = "${items.size}개 국가"
    }
}
