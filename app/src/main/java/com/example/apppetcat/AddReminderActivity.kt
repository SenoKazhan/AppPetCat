package com.example.apppetcat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddReminderActivity : AppCompatActivity() {

    private lateinit var reminderEditText: EditText
    private lateinit var saveReminderButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        reminderEditText = findViewById(R.id.reminderInput)
        saveReminderButton = findViewById(R.id.saveButton)

        saveReminderButton.setOnClickListener {
            val newReminder = reminderEditText.text.toString()

            if (newReminder.isNotEmpty()) {
                // Возвращаем результат обратно в HealthRemindersActivity
                val resultIntent = Intent()
                resultIntent.putExtra("new_reminder", newReminder)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }


        // Кнопка возврата на главный экран
        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}
