package com.example.apppetcat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class RemindersAdapter(private var context: Context, private var reminders: MutableList<String>) : BaseAdapter() {

    // Возвращает количество элементов в списке
    override fun getCount(): Int {
        return reminders.size
    }

    // Возвращает элемент по позиции
    override fun getItem(position: Int): Any {
        return reminders[position]
    }

    // Возвращает ID элемента по позиции
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Создание или повторное использование view для элемента списка
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_reminder, parent, false)

        // Привязка напоминания к TextView
        val reminderText = view.findViewById<TextView>(R.id.reminderTextView)
        val reminder = reminders[position]
        reminderText.text = reminder

        // Долгое нажатие для удаления напоминания
        view.setOnLongClickListener {
            showDeleteDialog(position)
            true
        }

        return view
    }

    // Метод для добавления нового напоминания
    fun addReminder(reminder: String) {
        reminders.add(reminder)
        notifyDataSetChanged()
    }

    // Метод для удаления напоминания
    private fun removeReminder(position: Int) {
        reminders.removeAt(position)
        notifyDataSetChanged()
        // Обновление данных в SharedPreferences
        saveToPreferences()
    }

    // Показывает диалоговое окно для подтверждения удаления
    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Удалить напоминание?")
            .setMessage("Вы уверены, что хотите удалить это напоминание?")
            .setPositiveButton("Удалить") { _, _ ->
                removeReminder(position)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    // Сохранение данных в SharedPreferences
    private fun saveToPreferences() {
        val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val remindersSet = reminders.toMutableSet()
        sharedPreferences.edit().putStringSet("reminders", remindersSet).apply()
    }

    // Метод для обновления списка напоминаний
    fun updateReminders(newReminders: List<String>) {
        reminders.clear()
        reminders.addAll(newReminders)
        notifyDataSetChanged()
    }
}
