package com.example.sqlitecrud

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlitecrud.adapter.mainAdapter
import com.example.sqlitecrud.databinding.ActivityMainBinding
import com.example.sqlitecrud.dbHelper.DbHelper
import com.example.sqlitecrud.model.ModelData

class MainActivity : AppCompatActivity() {
    val helper = DbHelper(this)
    var binding: ActivityMainBinding? = null
    lateinit var list: ArrayList<ModelData>
    lateinit var notesAdapter : mainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Log.d("recoreds", "records > ${helper.getNotes()} ")

        list = helper.getNotes() as ArrayList<ModelData>
        if (!list.isEmpty()) {
            notesAdapter =mainAdapter(this, list)
            binding?.rvRecords?.adapter = notesAdapter
        }

        clickListener()


    }

    private fun clickListener() {
        binding?.fabAdd?.setOnClickListener {
            intentResultLuncher.launch(Intent(this, AddRecord::class.java))
            //helper.createNote(model)
        }

        binding?.edSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                if(p0.isNullOrEmpty()){
                    binding?.searchCancle?.visibility = View.GONE
                }
                else{
                    binding?.searchCancle?.visibility = View.VISIBLE
                    filterNotes(p0.toString())
                }
            }
        })

        binding?.searchCancle?.setOnClickListener {
            binding?.edSearch?.clearFocus()
            binding?.edSearch?.text?.clear()
            binding?.searchCancle?.visibility = View.GONE
        }

    }

    private fun filterNotes(text: String) {
        val filterList: ArrayList<ModelData> = ArrayList()

        for (item in list) {
            if (item.title.contains(text)) {
                filterList.add(item)
            }
        }
        if(filterList.isNotEmpty()){
            notesAdapter.filterNotes(filterList)
        }
        else{
            //Toast.makeText(this, "No record Found", Toast.LENGTH_SHORT).show()
            binding?.searchCancle?.visibility = View.GONE
        }

    }


    val intentResultLuncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                list = helper.getNotes() as ArrayList<ModelData>
                if (!list.isEmpty()) {
                    binding?.rvRecords?.adapter?.notifyDataSetChanged()
                }
            }
        }

}