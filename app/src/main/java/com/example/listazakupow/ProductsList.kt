package com.example.listazakupow

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_products_list.*


class ProductsList : Fragment() {
    companion object {
        fun newInstance() = ProductsList()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_products_list, container, false)

        var recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        val dbConnect = DBHelper(context!!)

        recyclerView.adapter = ProductListAdapter(dbConnect.allProducts,context!!)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onResume() {
        super.onResume()
        val dbConnect = DBHelper(context!!)
        recyclerView.adapter = ProductListAdapter(dbConnect.allProducts,context!!)
    }
}