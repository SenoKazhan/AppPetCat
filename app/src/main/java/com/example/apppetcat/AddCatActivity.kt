package com.example.apppetcat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class AddCatActivity : AppCompatActivity() {

    // Объявление нативной функции
    external fun validateCatAge(age: Int): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cat)

        // Загружаем библиотеку native-lib
        System.loadLibrary("native_lib")

        // Инициализация полей ввода
        val catNameInput: EditText = findViewById(R.id.catNameInput)
        val catAgeInput: EditText = findViewById(R.id.catAgeInput)
        val submitCatButton: Button = findViewById(R.id.submitCatButton)

        submitCatButton.setOnClickListener {
            val catName = catNameInput.text.toString()
            val catAgeText = catAgeInput.text.toString()

            // Проверяем, что возраст не пустой
            if (catAgeText.isNotEmpty()) {
                val catAge = catAgeText.toIntOrNull()

                if (catAge != null) {
                    // Вызов функции validateCatAge для проверки возраста
                    val isValid = validateCatAge(catAge)

                    if (isValid) {
                        // Если возраст валиден, передаем данные в MainActivity
                        val resultIntent = Intent()
                        resultIntent.putExtra("catName", catName)
                        resultIntent.putExtra("catAge", catAge)
                        setResult(Activity.RESULT_OK, resultIntent)
                        Toast.makeText(this, "Кот $catName добавлен!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // Если возраст не валиден
                        Toast.makeText(this, "Возраст кошки должен быть от 0 до 30 лет!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Если введен некорректный возраст
                    Toast.makeText(this, "Введите корректный возраст", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Если не введен возраст
                Toast.makeText(this, "Введите возраст кошки", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
