package com.example.listazakupow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_products_list.*
import androidx.fragment.app.Fragment as Fragment

class MainActivity : AppCompatActivity() {

    val dbConnect = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = ProductsList.newInstance()

        replaceFragment(fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.add_product_item->{
                val fragment = AddProduct.newInstance()
                replaceFragment(fragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lista_menu, menu)
        return true
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun update(){
        recyclerView.adapter = ProductListAdapter(dbConnect.allProducts, this)

        /*Odświeżenie fragmentu przy kazdym wywołaniu przycisku DELETE*/
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, ProductsList.newInstance())
        fragmentTransaction.commit()

        Toast.makeText(this,"Successfully deleted", Toast.LENGTH_SHORT).show()

    }

    override fun onResume(){
        super.onResume()
        recyclerView.adapter = ProductListAdapter(dbConnect.allProducts, this)
    }
}
