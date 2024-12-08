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
            Toast.makeText(context,
                context.getString(R.string.enter_all_fields_text), Toast.LENGTH_SHORT).show()
            return false
        }

        if (person.firstName.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.enter_name_text), Toast.LENGTH_SHORT).show()
            return false
        }
        if (person.firstName.length !in 2..32) {
            Toast.makeText(context,
                context.getString(R.string.name_out_of_range_text), Toast.LENGTH_SHORT).show()
            return false
        }

        if (person.lastName.isEmpty()) {
            Toast.makeText(context,
                context.getString(R.string.input_last_name), Toast.LENGTH_SHORT).show()
            return false
        }
        if (person.lastName.length !in 2..32) {
            Toast.makeText(context,
                context.getString(R.string.last_name_out_of_range_text), Toast.LENGTH_SHORT).show()
            return false
        }

        if (person.age == null) {
            Toast.makeText(context,
                context.getString(R.string.input_age), Toast.LENGTH_SHORT).show()
            return false
        }
        if (person.age !in 1..120) {
            Toast.makeText(context, context.getString(R.string.age_out_of_range), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}