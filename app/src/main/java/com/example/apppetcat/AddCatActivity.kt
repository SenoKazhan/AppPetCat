package com.example.apppetcat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cat)

        val catNameInput: EditText = findViewById(R.id.catNameInput)
        val catAgeInput: EditText = findViewById(R.id.catAgeInput)
        val submitCatButton: Button = findViewById(R.id.submitCatButton)

        submitCatButton.setOnClickListener {
            val catName = catNameInput.text.toString()
            val catAgeText = catAgeInput.text.toString()

            if (catName.isNotEmpty() && catAgeText.isNotEmpty()) {
                val catAge = catAgeText.toIntOrNull()
                if (catAge != null) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("catName", catName)
                    resultIntent.putExtra("catAge", catAge)
                    setResult(Activity.RESULT_OK, resultIntent)
                    Toast.makeText(this, "Кот $catName добавлен!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Введите корректный возраст", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Введите имя и возраст кота", Toast.LENGTH_SHORT).show()
            }
        }

    }
}