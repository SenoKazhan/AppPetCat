package com.example.apppetcat

import CatAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var catAdapter: CatAdapter
    private lateinit var catsRecyclerView: RecyclerView
    private val cats = mutableListOf<Cat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catsRecyclerView = findViewById(R.id.catsRecyclerView)
        catsRecyclerView.layoutManager = LinearLayoutManager(this)

        catAdapter = CatAdapter(cats)
        catsRecyclerView.adapter = catAdapter

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

        loadCats()
    }

    private fun loadCats() {
        cats.addAll(listOf(
            Cat("Барсик", 3, ""),
            Cat("Мурка", 2, "")
        ))
        catAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val catName = data?.getStringExtra("catName")
            val catAge = data?.getIntExtra("catAge", 0) ?: 0
            if (catName != null) {
                cats.add(Cat(catName, catAge, "")) // Add new cat with age
                catAdapter.notifyDataSetChanged()
            }
        }
    }
}