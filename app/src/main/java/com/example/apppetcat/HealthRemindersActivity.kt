package com.example.apppetcat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences

class HealthRemindersActivity : AppCompatActivity() {

    private lateinit var remindersAdapter: RemindersAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var remindersListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_reminders)

        sharedPreferences = getSharedPreferences("RemindersPrefs", Context.MODE_PRIVATE)

        // Инициализация адаптера и загрузка списка напоминаний
        val savedRemindersSet = sharedPreferences.getStringSet("reminders", setOf()) ?: setOf()

// Преобразуем Set<String> в List<Reminder>
        val savedReminders = savedRemindersSet.map { Reminder(it) }

// Преобразуем List<Reminder> в MutableList для адаптера
        remindersAdapter = RemindersAdapter(this, savedReminders.toMutableList())


        val remindersListView = findViewById<ListView>(R.id.remindersListView)
        remindersListView.adapter = remindersAdapter

        // Кнопка для добавления нового напоминания
        val addReminderButton = findViewById<Button>(R.id.addReminderButton)
        addReminderButton.setOnClickListener {
            // Запуск AddReminderActivity для добавления напоминания
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivityForResult(intent, ADD_REMINDER_REQUEST_CODE)
        }

        // Кнопка возврата на главный экран
        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // Обработка результата из AddReminderActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_REMINDER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val newReminder = data?.getStringExtra("new_reminder")
            if (!newReminder.isNullOrEmpty()) {
                val newReminderObject = Reminder(newReminder)  // Преобразуем строку в объект Reminder
                remindersAdapter.addReminder(newReminderObject)  // Добавление нового напоминания в адаптер
                saveRemindersToPreferences()  // Сохранение изменений
            }

        }
    }

    // Метод для сохранения списка напоминаний в SharedPreferences
    fun saveRemindersToPreferences() {
        val remindersSet = remindersAdapter.getReminders().map { it.text }.toMutableSet()
        sharedPreferences.edit().putStringSet("reminders", remindersSet).apply()
    }

    private fun loadRemindersFromPreferences() {
        val remindersSet = sharedPreferences.getStringSet("reminders", setOf()) ?: setOf()
        val remindersList = remindersSet.map { Reminder(it) }.toMutableList()
        remindersAdapter = RemindersAdapter(this, remindersList)
        remindersListView.adapter = remindersAdapter
    }

    companion object {
        const val ADD_REMINDER_REQUEST_CODE = 1 // Код запроса для AddReminderActivity
    }
}

