package com.example.td1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Button
import com.example.td1.databinding.ActivityDetail3Binding
import com.example.td1.network.Item
import com.example.td1.network.Plate
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File

class DetailActivity3 : AppCompatActivity() {

    companion object{
        val PLATE_EXTRA = "PLATE_EXTRA"
    }
    lateinit var binding: ActivityDetail3Binding
    var plate: Plate? = null
    private var quantity: Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "Détail"
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, PannierActivity::class.java))
        }
        plate = intent.getSerializableExtra(PLATE_EXTRA) as? Plate
        val ingredients = plate?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
        binding.ingredients.text = ingredients

        startActivity(Intent(this, PannierActivity::class.java))

        refreshPannier()
        binding.name.text = plate?.name
        binding.ingredients.text = plate?.ingredients?.joinToString(", ") { it.name }

        binding.minus.setOnClickListener {
            if (binding.quantity.text.toString().toInt() > 1) {
                quantity--
                changePrice()
                binding.quantity.text = quantity.toString()
            }
        }
        binding.plus.setOnClickListener {
            quantity++
            changePrice()
            binding.quantity.text = quantity.toString()
        }
        plate?.prices?.forEach { price ->
            val button = Button(this)
            button.id = price.id
            button.text = "${price.size} : ${(price.price * quantity).toString().replace(".", "€ ")}0"
            button.setOnClickListener {
                addInJson(price.price)
                refreshPannier()
                Snackbar.make(binding.root, "Ajouté au pannier", Snackbar.LENGTH_SHORT).show()
            }
            binding.addToCart.addView(button)
        }
    }
    private fun changePrice(){
        plate?.prices?.forEach {
            findViewById<Button>(it.id).text = "${it.size} : ${(it.price * quantity).toString().replace(".", "€ ")}0"
        }
    }

    private fun addInJson(price: String) {
        val file = File(this.filesDir, "pannier.json")
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("[{ \"id\": ${plate?.id}, \"name\": \"${plate?.name}\", \"quantity\": $quantity, \"image\": \"${plate?.images?.get(0)}\", \"price\": $price }]")
        }else {
            val json = file.readText()
            if(json == "[]") {
                file.writeText("[{ \"id\": ${plate?.id}, \"name\": \"${plate?.name}\", \"quantity\": $quantity, \"image\": \"${plate?.images?.get(0)}\", \"price\": $price }]")
            }else {
                file.writeText(json.substring(0, json.length - 1) + ", { \"id\": ${plate?.id}, \"name\": \"${plate?.name}\", \"quantity\": $quantity, \"image\": \"${plate?.images?.get(0)}\", \"price\": $price }]")
            }
        }
    }


    private fun refreshPannier() {
        val file = File(this.filesDir, "pannier.json")
        if (file.exists()) {
            val json = file.readText()
            val pannier = Gson().fromJson(json, Array<Item>::class.java)
            if(pannier.isNotEmpty()) {
                binding.toolbar.pastille.visibility = View.VISIBLE
            }else{
                binding.toolbar.pastille.visibility = View.GONE
            }
            binding.toolbar.pastille.text = pannier.size.toString()
        }else{
            binding.toolbar.pastille.visibility = View.GONE
        }

    }
    override fun onResume() {
        super.onResume()
        refreshPannier()
    }
}

private operator fun String.times(quantity: Int) {

}
