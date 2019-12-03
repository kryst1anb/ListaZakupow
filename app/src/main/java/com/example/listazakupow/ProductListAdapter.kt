package com.example.listazakupow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ProductListAdapter(val products: MutableList<Product>, val context:Context) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = products[position]

        holder.productNameText.text = product.name
        holder.productPriorityText.text = product.priority.toString()
        holder.productMaxPriceText.text = product.maxPrice.toString()
        holder.productDateText.text = product.date.toString()
       // holder.productBoughtText.text = product.bought.toInt()

        holder.productButtonDelete.setOnClickListener {
            var connector = DBHelper(context)
            connector.deleteProduct(product.id)
            (context as? MainActivity)?.update()
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context!!, EditActivity::class.java)
            intent.putExtra("name",product.name)
            intent.putExtra("priority",product.priority)
            intent.putExtra("maxPrice",product.maxPrice)
            intent.putExtra("date",product.date.toString())
            intent.putExtra("id", product.id)
            intent.putExtra("bought", product.bought)
            ContextCompat.startActivity(context!!, intent,null)
        }

        if(product.bought)
        {
            holder.itemView.setBackgroundColor(Color.GRAY)
        }
    }
}

class  MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    val productNameText = view.findViewById<TextView>(R.id.product_name)
    val productPriorityText = view.findViewById<TextView>(R.id.product_priority)
    val productMaxPriceText = view.findViewById<TextView>(R.id.product_maxPrice)
    val productDateText = view.findViewById<TextView>(R.id.product_date)
    val productButtonDelete = view.findViewById<Button>(R.id.button_delete)
}
