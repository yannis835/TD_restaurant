package com.example.td1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.td1.databinding.ActivityPannierBinding
import com.example.td1.network.Item
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File


class PannierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPannierBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pannier)

        binding = ActivityPannierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title.text = "Pannier"

        refreshPannier()
        reloadLayout()

        binding.valider.setOnClickListener {
            val file = File(this.filesDir, "pannier.json")
            if (file.exists()) {
                val json = file.readText()
                val pannier = Gson().fromJson(json, Array<Item>::class.java)
                if(pannier.isNotEmpty()) {
                    Snackbar.make(binding.root, "Commande passée", Snackbar.LENGTH_SHORT).show()
                    file.delete()
                    refreshPannier()
                    reloadLayout()
                }
            }
        }
    }

    private fun reloadLayout() {
        binding.list.layoutManager = LinearLayoutManager(null)
        val file = File(this.filesDir, "pannier.json")
        if (file.exists()) {
            val json = file.readText()
            val pannier = Gson().fromJson(json, Array<Item>::class.java)
            binding.list.adapter = PannierAdapter(pannier) { target ->
                File(this.filesDir, "pannier.json").writeText(Gson().toJson(pannier.filter { it !== target }.toTypedArray()))
                refreshPannier()
                reloadLayout()
            }
        }else{
            binding.list.adapter = null
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