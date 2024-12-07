package com.example.database

import android.content.Context
import android.widget.Toast

class Person (
    val firstName: String,
    val lastName: String,
    val age: Int?,
    val role: String?
)

class InputPersonValidation(private val context: Context, private val person: Person) {
    fun isValidate(): Boolean {

        if (person.firstName.isEmpty() && person.lastName.isEmpty() && person.age == null) {
            Toast.makeText(context,  "Введите все поля", Toast.LENGTH_SHORT).show()
            return false
        }

        if (person.firstName.isEmpty()) {
            Toast.makeText(context, "Введите имя", Toast.LENGTH_SHORT).show()
            return false
        }
        if (person.firstName.length !in 2..32) {
            Toast.makeText(context, "Имя должно быть от 2 до 32 символов", Toast.LENGTH_SHORT).show()
            return false
        }

        if (person.lastName.isEmpty()) {
            Toast.makeText(context,
                "Введите фамилию", Toast.LENGTH_SHORT).show()
            return false
        }
        if (person.lastName.length !in 2..32) {
            Toast.makeText(context, "Фамилия должна быть от 2 до 32 символов", Toast.LENGTH_SHORT).show()
            return false
        }

        if (person.age == null) {
            Toast.makeText(context,
                "Введите возраст", Toast.LENGTH_SHORT).show()
            return false
        }
        if (person.age !in 1..120) {
            Toast.makeText(context, "Возраст должен быть от 1 до 120 лет", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}