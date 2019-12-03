package com.example.listazakupow

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddProduct : Fragment() {

    companion object{
        fun newInstance() = AddProduct()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_product, container, false)

        view?.cancel_button.setOnClickListener {
            activity?.onBackPressed()
        }

        view?.add_button.setOnClickListener {
            addProduct()
        }
        return view
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun addProduct(){

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = sdf.format(Date())
        val name = set_name_product.text.toString()
        val maxPrice = set_price_product.text.toString().toFloat()
        val check = priority_group.checkedRadioButtonId

        var priority= when (check) {
            R.id.high ->  3
            R.id.medium ->  2
            else -> 1
        }

        val db = DBHelper(context!!)
        var product = Product(priority, name, maxPrice,currentDate,false)
        db.addProduct(product)
        Toast.makeText(context!!,"Added",Toast.LENGTH_SHORT).show()
        clearFields()
    }

    fun clearFields(){
        set_name_product.setText("")
        set_price_product.setText("")
        priority_group.clearCheck()
    }
}
