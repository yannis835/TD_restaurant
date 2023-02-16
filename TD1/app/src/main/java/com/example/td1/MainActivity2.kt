package com.example.td1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request.Method
import com.example.td1.databinding.ActivityMain2Binding
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.td1.network.Item
import com.example.td1.network.MenuResult
import com.example.td1.network.NetworkConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

enum class Category {ENTREE,PLAT,DESSERT}

class MainActivity2 : AppCompatActivity() {

    companion object{
        const val extraKey ="extraKey"
    }
    private lateinit var categorie: String
    private lateinit var binding: ActivityMain2Binding
    lateinit var currentCategory :Category

    override fun onCreate(savedInstanceState: Bundle?) {

        categorie = intent.getStringExtra("category").toString()
        title = categorie
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = categorie
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, PannierActivity::class.java))
        }
        refreshPannier()


        val category =intent.getSerializableExtra(extraKey) as? Category
        currentCategory=category ?: Category.ENTREE
        supportActionBar?.title = categoryName()
        makeRequest()

    }
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }
    override fun onResume() {
        super.onResume()

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
    private fun showData(category: com.example.td1.network.Category){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CustomAdapter(category.items){
                val intent = Intent(this, DetailActivity3::class.java )
            intent.putExtra(DetailActivity3.PLATE_EXTRA, it)
            startActivity(intent)
        }
    }


    private fun parseData(data: String){
        val result= GsonBuilder().create().fromJson(data, MenuResult::class.java)
        val category = result.data.first{it.name == categoryFilterKey() }
        showData(category)
    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val params = JSONObject()
        params.put(NetworkConstants.idShopKey,1)
        val request = JsonObjectRequest(
            Method.POST,
            NetworkConstants.url,
            params,
            { result ->
                //Success of request
                Log.d("request",result.toString(2))
                parseData(result.toString())
            },
            { error->
                Log.e("request",error.toString())
            })
        queue.add(request)
            //show data
    }
    private fun categoryName(): String{
        return when (currentCategory){
            Category.ENTREE -> "entrées"
            Category.PLAT -> "plats"
            Category.DESSERT -> "Desserts"
        }
    }

    private fun categoryFilterKey():String{
        return when (currentCategory){
            Category.ENTREE -> "Entrées"
            Category.PLAT -> "Plats"
            Category.DESSERT -> "Desserts"
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

}

