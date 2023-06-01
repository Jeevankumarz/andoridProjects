package com.example.sqlitecrud

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sqlitecrud.databinding.ActivityAddRecordBinding
import com.example.sqlitecrud.dbHelper.DbHelper
import com.example.sqlitecrud.model.ModelData
import com.example.sqlitecrud.viewModel.AddViewModel

class AddRecord : AppCompatActivity() {
    var binding: ActivityAddRecordBinding? = null
    lateinit var viewmodel : AddViewModel
    val dbHelper = DbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewmodel = ViewModelProvider(this).get(AddViewModel::class.java)

        clicklistener()

    }

    private fun addNote() {
        val title = binding?.edTitle?.text.toString()
        val message = binding?.edMessage?.text.toString()
        val db = dbHelper.writableDatabase
        var values = ContentValues()
        values.put("title", title)
        values.put("message", message)
        db.insert("NOTES", null, values)
        db.close()
        Toast.makeText(this, "Record Inserted Sucessfully", Toast.LENGTH_SHORT).show()
        Log.v("@@@WWe ", " Record Inserted Sucessfully")
    }

    private fun clicklistener() {
        binding?.ivBack?.setOnClickListener {
            super.onBackPressed()
        }

        binding?.ivDone?.setOnClickListener {
            if (binding?.edTitle?.text.isNullOrEmpty()) {
                binding?.edTitle?.error = "please enter title"
            } else if (binding?.edMessage?.text.isNullOrEmpty()) {
                binding?.edMessage?.error = "Please enter message"
            }
            else
            {
                addNote()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }


}