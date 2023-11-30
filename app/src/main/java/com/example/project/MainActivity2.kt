package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity2 : AppCompatActivity() {
    private val listaArticulos = mutableListOf<String>()
    private val listaDescripciones = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val buttonGuardar = findViewById<Button>(R.id.btnGuardar)

        buttonGuardar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val descripcion = editTextDescripcion.text.toString()

            if(titulo.isNotEmpty() && descripcion.isNotEmpty()) {
                listaArticulos.add(titulo)
                listaDescripciones.add(descripcion)
            }

            editTextTitulo.text.clear()
            editTextDescripcion.text.clear()
        }

        val buttonVerArticulos = findViewById<Button>(R.id.btnVerArticulos)
        buttonVerArticulos.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            intent.putStringArrayListExtra("titulosArticulos", ArrayList(listaArticulos))
            intent.putStringArrayListExtra("descripcionesArticulos", ArrayList(listaDescripciones))
            startActivity(intent)
        }
    }
}
