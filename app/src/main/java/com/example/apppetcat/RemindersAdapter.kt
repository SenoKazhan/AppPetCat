package com.example.apppetcat

import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
data class Reminder(var text: String, var isImportant: Boolean = false)

class RemindersAdapter(private val context: HealthRemindersActivity, private var reminders: MutableList<Reminder>) : BaseAdapter() {
    private val gestureDetector = GestureDetector(context, GestureListener())

    // Возвращает количество элементов в списке
    override fun getCount(): Int {
        return reminders.size
    }

    // Возвращает элемент по позиции
    override fun getItem(position: Int): Reminder {
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
        reminderText.text = reminder.text

        // Устанавливаем цвет текста в зависимости от важности напоминания
        if (reminder.isImportant) {
            reminderText.setTextColor(context.getColor(R.color.importantReminderColor))
        } else {
            reminderText.setTextColor(context.getColor(R.color.black))
        }

        // Настройка обработчика касаний с performClick
        view.setOnTouchListener { v, event ->
            if (gestureDetector.onTouchEvent(event)) {
                v.performClick()  // Вызов performClick при обнаружении клика
                true
            } else {
                false
            }
        }

        return view
    }

    // Внутренний класс для обработки жестов
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        // Обработка двойного нажатия для редактирования
        override fun onDoubleTap(e: MotionEvent): Boolean {
            val position = getPosition(e)
            if (position != -1) {
                showEditReminderDialog(position)
                Toast.makeText(context, "Редактирование: ${reminders[position].text}", Toast.LENGTH_SHORT).show()
            }
            return true
        }

        // Обработка свайпа для пометки важности и удаления
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 != null) {
                val position = getPosition(e1)
                if (position != -1) {
                    if (e2.x - e1.x > 100) {  // Свайп влево для важного
                        toggleImportantReminder(position)
                        Toast.makeText(context, if (reminders[position].isImportant) "Помечено важным: ${reminders[position].text}" else "Снята важность: ${reminders[position].text}", Toast.LENGTH_SHORT).show()
                    } else if (e1.x - e2.x > 10) {  // Свайп вправо для удаления
                        showDeleteConfirmationDialog(position)
                    }
                }
            }
            return true
        }

        // Обработка одиночного нажатия для вызова performClick
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val position = getPosition(e)
            if (position != -1) {
                Toast.makeText(context, "Элемент выбран: ${reminders[position].text}", Toast.LENGTH_SHORT).show()
            }
            return true
        }

        private fun getPosition(event: MotionEvent): Int {
            val listView = context.findViewById<ListView>(R.id.remindersListView)
            for (i in 0 until listView.childCount) {
                val view = listView.getChildAt(i)
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                if (event.rawX >= location[0] && event.rawX <= location[0] + view.width &&
                    event.rawY >= location[1] && event.rawY <= location[1] + view.height) {
                    return listView.getPositionForView(view)
                }
            }
            return -1
        }
    }

    // Метод для пометки важности напоминания
    private fun toggleImportantReminder(position: Int) {
        val reminder = reminders[position]
        reminder.isImportant = !reminder.isImportant
        notifyDataSetChanged()
    }

    // Метод для отображения диалога редактирования напоминания
    private fun showEditReminderDialog(position: Int) {
        val reminder = reminders[position]
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Редактировать напоминание")

        val input = EditText(context)
        input.setText(reminder.text)
        builder.setView(input)

        builder.setPositiveButton("Сохранить") { dialog, _ ->
            val newText = input.text.toString()
            if (newText.isNotEmpty()) {
                reminder.text = newText
                notifyDataSetChanged()
                context.saveRemindersToPreferences()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    // Диалог подтверждения удаления
    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Удалить напоминание")
            .setMessage("Вы уверены, что хотите удалить это напоминание?")
            .setPositiveButton("Удалить") { _, _ -> removeReminder(position) }
            .setNegativeButton("Отмена", null)
            .show()
    }



    // Метод для добавления нового напоминания
    fun addReminder(reminder: Reminder) {
        reminders.add(reminder)
        notifyDataSetChanged()
    }

    // Метод для удаления напоминания
    private fun removeReminder(position: Int) {
        reminders.removeAt(position)
        notifyDataSetChanged()
    }

    // Метод для получения списка напоминаний
    fun getReminders(): List<Reminder> {
        return reminders
    }

}