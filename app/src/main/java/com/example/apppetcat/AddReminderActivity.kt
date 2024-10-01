package com.example.apppetcat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddReminderActivity : AppCompatActivity() {

    private lateinit var reminderInput: EditText
    private lateinit var saveButton: Button
    private lateinit var backToMainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_reminder)

        // Инициализация элементов интерфейса
        reminderInput = findViewById(R.id.reminderInput)
        saveButton = findViewById(R.id.saveButton)
        backToMainButton = findViewById(R.id.backToMainButton)

        // Сохранение нового напоминания в SharedPreferences
        saveButton.setOnClickListener {
            val reminderText = reminderInput.text.toString()
            if (reminderText.isNotEmpty()) {
                // Получаем SharedPreferences и существующие напоминания
                val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
                val remindersSet = sharedPreferences.getStringSet("reminders", mutableSetOf()) ?: mutableSetOf()

                // Добавляем новое напоминание (поддерживается кириллица)
                remindersSet.add(reminderText)
                sharedPreferences.edit().putStringSet("reminders", remindersSet).apply()

                // Сообщение об успешном добавлении
                Toast.makeText(this, "Напоминание сохранено: $reminderText", Toast.LENGTH_SHORT).show()
                reminderInput.text.clear() // Очистка поля ввода

                // Возвращаемся в HealthRemindersActivity
                val intent = Intent(this, HealthRemindersActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Введите текст напоминания", Toast.LENGTH_SHORT).show()
            }
        }

        // Кнопка возврата на главный экран
        backToMainButton.setOnClickListener {
            // Возвращаемся на главный экран
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
