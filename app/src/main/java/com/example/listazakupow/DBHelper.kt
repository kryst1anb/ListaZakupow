package com.example.listazakupow

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        internal const val DATABASE_VERSION =1
        internal const val DATABASE_NAME = "ProductsList.db"
        internal const val TABLE_NAME = "productslist"
        internal const val COL_ID = "id"
        internal const val COL_NAME = "name"
        internal const val COL_MAXPRICE = "maxPrice"
        internal const val COL_DATE = "date"
        internal const val COL_PRIORITY = "priority"
        internal const val COL_BOUGHT = "bought"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = db!!.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ("+
                "$COL_ID INTEGER PRIMARY KEY, " +
                "$COL_PRIORITY TEXT NOT NULL, "+
                "$COL_NAME TEXT NOT NULL, " +
                "$COL_MAXPRICE FLOAT NOT NULL, " +
                "$COL_DATE TEXT NOT NULL," +
                "$COL_BOUGHT INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    val allProducts: MutableList<Product>

        @RequiresApi(Build.VERSION_CODES.O)
        get(){
            val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_PRIORITY DESC" //sortowanie listy najwazniejsze mniej wazne

            val products = mutableListOf<Product>()
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)

            if(cursor.moveToFirst())
            {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(COL_ID))
                    val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    val priority = cursor.getInt(cursor.getColumnIndex(COL_PRIORITY))
                    val maxPrice = cursor.getFloat(cursor.getColumnIndex(COL_MAXPRICE))
                    val date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                    val bought = cursor.getInt(cursor.getColumnIndex(COL_BOUGHT))

                    val product = Product(priority, name, maxPrice, date, when(bought){
                        1-> true
                        else -> false
                    }, id)
                    products.add((product))
                }while (cursor.moveToNext())
            }
            db.close()
            return products
        }

    fun deleteProduct(id:Long?){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun addProduct(product:Product):Long{
        val db = this.writableDatabase
        val value = contentValuesOf()


        value.put(COL_NAME, product.name)
        value.put(COL_MAXPRICE, product.maxPrice)
        value.put(COL_PRIORITY, product.priority)
        value.put(COL_BOUGHT, product.bought)
        value.put(COL_DATE, product.date.toString())

        val result = db.insert(TABLE_NAME, null, value)
        product.id = result
        db.close()
        return result
    }
}