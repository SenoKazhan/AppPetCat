package com.example.apppetcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HealthRemindersActivity : AppCompatActivity() {

    private lateinit var remindersListView: ListView
    private lateinit var remindersAdapter: RemindersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_reminders)

        // Инициализация ListView
        remindersListView = findViewById(R.id.remindersListView)

        // Загрузка сохранённых напоминаний из SharedPreferences
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val remindersList = sharedPreferences.getStringSet("reminders", mutableSetOf())?.toList() ?: emptyList()

        // Инициализация адаптера
        remindersAdapter = RemindersAdapter(this, remindersList.toMutableList())
        remindersListView.adapter = remindersAdapter

        // Кнопка для добавления нового напоминания
        val addReminderButton = findViewById<Button>(R.id.addReminderButton)
        addReminderButton.setOnClickListener {
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivity(intent)
            finish() // Закрыть текущую активность
        }

        // Кнопка возврата на главный экран
        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрыть текущую активность
        }
    }
}
