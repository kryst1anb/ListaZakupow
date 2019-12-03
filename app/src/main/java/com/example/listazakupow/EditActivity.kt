package com.example.listazakupow

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.fragment_products_list.*
import kotlinx.android.synthetic.main.product_layout.*
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    private var dbConnect = DBHelper(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val intent = intent

        val name:String = intent.getStringExtra("name")
        val priority = intent.getIntExtra("priority",1)
        val maxPrice = intent.getFloatExtra("maxPrice",0F)
        val date:String = intent.getStringExtra("date")
        val id:Long = intent.getLongExtra("id",0)

        edit_name_product.setText(name)
        edit_price_product.setText(maxPrice.toString())
        edit_priority.setText(priority.toString())
        edit_date.setText(date)


        save_edit_button.setOnClickListener {


            val check = done_edit_group.checkedRadioButtonId

            var done = when (check) {
                R.id.done_edit_radio_button ->  true
                else -> false
            }
            val product = Product(Integer.parseInt(edit_priority.text.toString()),edit_name_product.text.toString(),edit_price_product.text.toString().toFloat(),edit_date.text.toString(),done)
            dbConnect.deleteProduct(id)
            dbConnect.addProduct(product)
            recyclerView.adapter = ProductListAdapter(dbConnect.allProducts, this)
            finish()
        }

        cancel_button.setOnClickListener {
            finish()
        }
    }
}
