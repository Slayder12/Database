package com.example.database

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class DataBaseActivity : AppCompatActivity() {

    private val db = DBHelper(this, null)

    private val personData: MutableList<Person> = mutableListOf()
    private var adapter: ListAdapter? = null
    private lateinit var personLiveData: PersonViewModel

    private var roleAdapter: ArrayAdapter<String>? = null
    private var role: String? = null

    private lateinit var toolbar: Toolbar
    private lateinit var listViewLV: ListView
    private lateinit var firstNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var ageET: EditText
    private lateinit var roleSpinner: Spinner

    private lateinit var saveDataBTN:Button
    private lateinit var loadDataBTN:Button
    private lateinit var deleteDataBTN:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base)

        init()

        personLiveData = ViewModelProvider(this)[PersonViewModel::class.java]
        adapter = ListAdapter(this@DataBaseActivity, personData)
        listViewLV.adapter = adapter

        roleAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            RoleData().getRole()
        )
        roleAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        roleSpinner.adapter = roleAdapter
        val itemSelectedListener: OnItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    role = if (position != 0) parent?.getItemAtPosition(position) as String else ""
                    //if (position == 0) (view as TextView).text = "Выберите должность"
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        roleSpinner.onItemSelectedListener = itemSelectedListener

        personLifeData()

        saveDataBTN.setOnClickListener{

            val person = Person (
                firstNameET.text.toString(),
                lastNameET.text.toString(),
                ageET.text.toString().toIntOrNull(),
                role
            )

            if (!InputPersonValidation(this, person).isValidate()) return@setOnClickListener

            val role = this.role.toString()

            db.addData(
                person.firstName,
                person.lastName,
                person.age.toString(),
                role
            )
            clearEditText()
            Toast.makeText(this,
                getString(R.string.add_person_text, person.firstName, person.lastName), Toast.LENGTH_SHORT).show()
        }

        loadDataBTN.setOnClickListener{
            personLiveData.personLiveData.value?.clear()
            val cursor = db.getInfo()
            if(cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst()
                createPerson(cursor)
            }
            while (cursor!!.moveToNext()){
                createPerson(cursor)
            }
            cursor.close()
            personLifeData()
        }

        deleteDataBTN.setOnClickListener{
            db.removeAll()
            personLiveData.personLiveData.value?.clear()
            clearPersonLifeData()
        }
    }

    @SuppressLint("Range")
    private fun createPerson(
        cursor: Cursor
    ): Person {
        val person = Person(
            cursor.getString(cursor.getColumnIndex(DBHelper.KEY_FIRST_NAME)),
            cursor.getString(cursor.getColumnIndex(DBHelper.KEY_LAST_NAME)),
            cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AGE)).toIntOrNull(),
            cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ROLE)),
        )
        val currentList = personLiveData.personLiveData.value ?: mutableListOf()
        currentList.add(person)
        personLiveData.personLiveData.value = currentList
        return person
    }

    private fun clearEditText() {
        firstNameET.text.clear()
        lastNameET.text.clear()
        ageET.text.clear()
        roleSpinner.setSelection(0)
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        title = ""
        setSupportActionBar(toolbar)

        listViewLV = findViewById(R.id.listViewLV)
        firstNameET = findViewById(R.id.firstNameET)
        lastNameET = findViewById(R.id.lastNameET)
        ageET = findViewById(R.id.ageET)
        roleSpinner = findViewById(R.id.roleSP)

        saveDataBTN = findViewById(R.id.saveDataBTN)
        loadDataBTN = findViewById(R.id.loadData2BTN)
        deleteDataBTN = findViewById(R.id.deleteDataBTN)

    }

    private fun personLifeData() {
        personLiveData.personLiveData.observe(this, Observer { persons ->
            adapter?.clear()
            adapter?.addAll(persons)
            adapter?.notifyDataSetChanged()
        })
    }

    private fun clearPersonLifeData() {
        personLiveData.personLiveData.observe(this, Observer { _ ->
            adapter?.clear()
            adapter?.notifyDataSetChanged()
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) {
            Toast.makeText(this, getString(R.string.exit_text), Toast.LENGTH_SHORT).show()
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}