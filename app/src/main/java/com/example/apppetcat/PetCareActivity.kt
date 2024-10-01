package com.example.apppetcat

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class PetCareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.care_tips)

        // Кнопка возврата на главный экран
        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Список советов
        val tips = listOf(
            "Регулярно кормите кота сбалансированным кормом.",
            "Обеспечьте доступ к свежей воде.",
            "Регулярно чистите лоток.",
            "Проводите игры и уделяйте внимание коту.",
            "Регулярно посещайте ветеринара для профилактики."
        )

        // Настройка ListView
        val tipsListView = findViewById<ListView>(R.id.tipsListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tips)
        tipsListView.adapter = adapter
    }
}