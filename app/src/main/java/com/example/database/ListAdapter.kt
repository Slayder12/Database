package com.example.database;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, personList: MutableList<Person>) :
        ArrayAdapter<Person>(context, R.layout.list_item, personList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false)
        }

        val personFirstNameTV = view?.findViewById<TextView>(R.id.personFirstNameTV)
        val personLastNameTV = view?.findViewById<TextView>(R.id.personLastNameTV)
        val personAgeTV = view?.findViewById<TextView>(R.id.personAgeTV)
        val personRole = view?.findViewById<TextView>(R.id.personPositionTV)

        personFirstNameTV?.text = person?.firstName
        personLastNameTV?.text = person?.lastName
        personAgeTV?.text = person?.age.toString()
        personRole?.text = person?.role

        return view!!
    }
}
