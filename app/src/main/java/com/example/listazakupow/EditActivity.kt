package com.example.listazakupow

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.fragment_products_list.*

class EditActivity : AppCompatActivity() {

    private var dbConnect = DBHelper(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val intent = intent

        val name = intent.getStringExtra("name")
        val priority = intent.getIntExtra("priority",1)
        val maxPrice = intent.getFloatExtra("maxPrice",0F)
        val date= intent.getStringExtra("date")
        val id:Long = intent.getLongExtra("id",0)

        edit_name_product.setText(name)
        edit_price_product.setText(maxPrice.toString())
        edit_priority.setText(priority.toString())
        edit_date.setText(date)


        save_edit_button.setOnClickListener {
            val check = done_edit_group.checkedRadioButtonId

            var done = when (check) {
                R.id.done_edit_radio_button ->  true
                R.id.todo_edit_radio_button -> false
                else -> false
            }

            val product = Product(Integer.parseInt(edit_priority.text.toString()),edit_name_product.text.toString(),edit_price_product.text.toString().toFloat(),edit_date.text.toString(),done)

            dbConnect.deleteProduct(id)
            dbConnect.addProduct(product)

            finish()
        }

        cancel_button.setOnClickListener {
            finish()
        }
    }
}
