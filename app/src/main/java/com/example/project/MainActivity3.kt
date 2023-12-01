package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.button.MaterialButton

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val listView = findViewById<ListView>(R.id.listViewArticulos)
        val titulos = intent.getStringArrayListExtra("titulosArticulos") ?: arrayListOf()
        val descripciones = intent.getStringArrayListExtra("descripcionesArticulos") ?: arrayListOf()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MainActivity4::class.java).apply {
                putExtra("titulo", titulos[position])
                putExtra("descripcion", descripciones[position])
            }
            startActivity(intent)
        }
        val buttonRegresar = findViewById<MaterialButton>(R.id.buttonRegresar)
        buttonRegresar.setOnClickListener {
            // Finalizar esta actividad nos llevará de vuelta a la actividad anterior en la pila
            finish()
        }
    }
}
