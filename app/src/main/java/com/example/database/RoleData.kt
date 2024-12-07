package com.example.database

class RoleData {
   private val role = mutableListOf (
       "Должность",
       "Разработчик",
       "Тестировщик",
       "UI/UX дизайнер",
       "Аналитик данных",
       "Менеджер проектов",
       "DevOps-инженер",
       "Маркетолог"
   )
    fun getRole() = role
}
