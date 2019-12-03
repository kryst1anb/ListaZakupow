package com.example.listazakupow

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class Product(var _priority: Int, var _name: String, var _maxPrice: Float, var _date: String, var _bought: Boolean = false, var _id:Long? = null) {

    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var id : Long?
    var name: String
    var priority : Int
    var maxPrice : Float
    var bought: Boolean
    var date : LocalDate

    init{
        this.id = _id
        this.name = _name
        this.priority = _priority
        this.maxPrice = _maxPrice
        this.bought = _bought
        this.date = LocalDate.parse(_date, formatter)
    }
}