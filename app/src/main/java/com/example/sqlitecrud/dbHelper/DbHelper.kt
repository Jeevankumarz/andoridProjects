package com.example.sqlitecrud.dbHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.Display.Mode
import com.example.sqlitecrud.model.ModelData

class DbHelper(context : Context) : SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CREATE TABLE NOTES (id INTEGER NOT NULL PRIMARY KEY,title TEXT, message INTEGER)"
        p0!!.execSQL(query)
        Log.v("@@@WWE", " Table Created Sucessfully")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS NOTES")
        onCreate(p0)
    }
    companion object {
        private val DATABASENAME = "Notes"
        private val DATABASEVERSION = 1
    }



    fun createNote(note: ModelData) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("title", note.title)
        values.put("message", note.message)
        db.insert("NOTES", null, values)
        db.close()
        Log.v("@@@WWe ", " Record Inserted Sucessfully")
    }

    @SuppressLint("Range")
    fun getNotes(): List<ModelData> {
        val db = this.writableDatabase
        val list = ArrayList<ModelData>()
        val cusrsor: Cursor
        cusrsor = db.rawQuery("SELECT * FROM NOTES", null)
        if (cusrsor != null) {
            if (cusrsor.count > 0) {
                cusrsor.moveToFirst()
                do {
                    val noteID = cusrsor.getInt(cusrsor.getColumnIndex("id"))
                    val noteTitle = cusrsor.getString(cusrsor.getColumnIndex("title"))
                    val noteMessage = cusrsor.getString(cusrsor.getColumnIndex("message"))
                    val notes = ModelData(noteID, noteTitle, noteMessage)
                    list.add(notes)
                } while (cusrsor.moveToNext())
            }
        }
        return list
    }

    fun updateUser(users: ModelData) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("userID", users.title)

        val retVal = db.update("USER", values, "userID = " + users.title, null)
        if (retVal >= 1) {
            Log.v("@@@WWe", " Record updated")
        } else {
            Log.v("@@@WWe", " Not updated")
        }
        db.close()

    }

    fun deleteUser(users: ModelData) {
        val db = this.writableDatabase
        var values = ContentValues()
        values.put("userID", users.title)
        val retVal = db.delete("USER", "userID = " + users.title, null)
        if (retVal >= 1) {
            Log.v("@@@WWe", " Record deleted")
        } else {
            Log.v("@@@WWe", " Not deleted")
        }
        db.close()

    }


}