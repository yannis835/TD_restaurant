package com.example.td1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.td1.databinding.ActivityMainBinding
import kotlin.math.log
import java.io.File
import com.example.td1.network.Item
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "home"
        binding.toolbar.pannier.setOnClickListener{
            startActivity(Intent(this, PannierActivity::class.java))
        }
        refreshPannier()
        buttonsListener()
    }
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }
    override fun onResume() {
        super.onResume()
        refreshPannier()
        Log.d("MainActivity", "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }
    override fun onDestroy() {
    super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    private fun buttonsListener(){
        binding.buttonEntree.setOnClickListener {
            showCategory(Category.ENTREE)
        }
        binding.ButtonPlat.setOnClickListener {
            showCategory(Category.PLAT)
        }
        binding.buttonDessert.setOnClickListener {
            showCategory(Category.DESSERT)
        }
    }
    private fun showCategory(category: Category){
        val intent =Intent( this, MainActivity2::class.java)
        intent.putExtra(MainActivity2.extraKey, category)
        startActivity(intent)
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

}