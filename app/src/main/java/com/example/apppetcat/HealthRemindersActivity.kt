package com.example.apppetcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HealthRemindersActivity : AppCompatActivity() {

    private lateinit var remindersRecyclerView: RecyclerView
    private lateinit var remindersAdapter: RemindersAdapter
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_reminders)

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)

        // Инициализация RecyclerView
        remindersRecyclerView = findViewById(R.id.remindersRecyclerView)
        remindersRecyclerView.layoutManager = LinearLayoutManager(this)

        // Изначальная загрузка напоминаний
        loadReminders()

        // Кнопка для добавления нового напоминания
        val addReminderButton = findViewById<Button>(R.id.addReminderButton)
        addReminderButton.setOnClickListener {
            // Переход на экран добавления нового напоминания
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivity(intent)
        }

        // Кнопка возврата на главный экран
        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            // Возвращаемся на главный экран
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрыть текущую активность
        }
    }

    // Метод загрузки напоминаний из SharedPreferences
    private fun loadReminders() {
        val remindersSet = sharedPreferences.getStringSet("reminders", mutableSetOf()) ?: mutableSetOf()
        val remindersList = remindersSet.toMutableList()

        // Инициализация адаптера с загруженными напоминаниями
        remindersAdapter = RemindersAdapter(remindersList)
        remindersRecyclerView.adapter = remindersAdapter
    }

    // Метод обновления списка при возвращении на активность
    override fun onResume() {
        super.onResume()
        loadReminders() // Обновляем напоминания при возврате в активность
    }
}
