package com.example.apppetcat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class RemindersAdapter(private var reminders: MutableList<String>) : RecyclerView.Adapter<RemindersAdapter.ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.bind(reminder)

        // Обработка долгого нажатия для удаления напоминания
        holder.itemView.setOnLongClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setTitle("Удалить напоминание?")
                .setMessage("Вы уверены, что хотите удалить это напоминание?")
                .setPositiveButton("Удалить") { _, _ ->
                    removeReminder(position, context)
                }
                .setNegativeButton("Отмена", null)
                .show()
            true
        }
    }

    override fun getItemCount(): Int = reminders.size

    // Удаление напоминания
    private fun removeReminder(position: Int, context: Context) {
        // Удаляем напоминание из списка
        reminders.removeAt(position)
        notifyItemRemoved(position)

        // Обновляем SharedPreferences
        val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val remindersSet = reminders.toMutableSet()
        sharedPreferences.edit().putStringSet("reminders", remindersSet).apply()
    }

    // Обновление данных адаптера
    fun updateReminders(newReminders: List<String>) {
        reminders.clear()
        reminders.addAll(newReminders)
        notifyDataSetChanged()
    }

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reminderTextView: TextView = itemView.findViewById(R.id.reminderTextView)

        fun bind(reminder: String) {
            reminderTextView.text = reminder
        }
    }
}

