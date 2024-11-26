package com.example.apppetcat

import CatAdapter
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var catAdapter: CatAdapter
    private lateinit var catsRecyclerView: RecyclerView
    private val cats = mutableListOf<Cat>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("CatsPrefs", MODE_PRIVATE)

        // Инициализация RecyclerView
        catsRecyclerView = findViewById(R.id.catsRecyclerView)
        catsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Лямбда для удаления кота
        catAdapter = CatAdapter(cats) { cat ->
            removeCat(cat) // Удаляем кота при нажатии кнопки удаления
        }
        catsRecyclerView.adapter = catAdapter


        // Загружаем котов из SharedPreferences
        loadCats()
        val fabAddCat = findViewById<FloatingActionButton>(R.id.fabAddCat)
        fabAddCat.setOnClickListener {
            startActivityForResult(Intent(this, AddCatActivity::class.java), 1)
        }

        val goToHealthRemindersButton = findViewById<Button>(R.id.btnGoToHealthReminders)
        goToHealthRemindersButton.setOnClickListener {
            startActivity(Intent(this, HealthRemindersActivity::class.java))
        }

        val goToPetCareButton = findViewById<Button>(R.id.btnGoToPetCare)
        goToPetCareButton.setOnClickListener {
            startActivity(Intent(this, PetCareActivity::class.java))
        }

        val goToProfileButton = findViewById<Button>(R.id.btnGoToProfile)
        goToProfileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    // Загружаем котов из SharedPreferences или добавляем несколько по умолчанию
    private fun loadCats() {
        // Попробуем загрузить список котов из SharedPreferences
        val catsString = sharedPreferences.getString("cats", null)

        if (!catsString.isNullOrEmpty()) {
            // Если коты уже сохранены в SharedPreferences, загружаем их
            val catList = catsString.split(";").map {
                val parts = it.split(",")
                if (parts.size == 2) {
                    val name = parts[0]
                    val age = parts[1].toIntOrNull() ?: 0
                    Cat(name, age, "")
                } else {
                    null
                }
            }.filterNotNull()
            cats.addAll(catList)
        } else {
            // Если коты еще не сохранены, добавляем несколько по умолчанию
            cats.addAll(listOf(
                Cat("Барсик", 3, ""),
                Cat("Мурка", 2, ""),
                Cat("Аля", 5, ""),
                Cat("Лео", 4, "")
            ))
        }

        sortCats() // Сортируем котов сразу после загрузки
        catAdapter.notifyDataSetChanged()
    }

    // Сохраняем котов в SharedPreferences
    private fun saveCats() {
        val catsString = cats.joinToString(";") { "${it.name},${it.age}" }
        sharedPreferences.edit().putString("cats", catsString).apply()
    }

    // Функция для сортировки котов
    private fun sortCats() {
        cats.sortWith(compareBy({ it.name }, { it.age })) // Сортируем по имени, а затем по возрасту
        catAdapter.notifyDataSetChanged()
    }

    // Удаление кота
    private fun removeCat(cat: Cat) {
        cats.remove(cat)
        saveCats() // Обновляем SharedPreferences после удаления
        catAdapter.notifyDataSetChanged()
    }

    // Обработка результата из AddCatActivity (для добавления нового кота)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val catName = data?.getStringExtra("catName")
            val catAge = data?.getIntExtra("catAge", 0) ?: 0
            if (catName != null) {
                val newCat = Cat(catName, catAge, "")
                cats.add(newCat)  // Добавляем нового кота
                sortCats() // Сортируем список
                saveCats() // Сохраняем котов в SharedPreferences
            }
        }
    }
}


