package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")

        val textViewTitulo = findViewById<TextView>(R.id.textViewTitulo)
        val textViewDescripcion = findViewById<TextView>(R.id.textViewDescripcion)
        textViewTitulo.text = titulo
        textViewDescripcion.text = descripcion

        val buttonVolver = findViewById<MaterialButton>(R.id.buttonVolver)
        buttonVolver.setOnClickListener {
            // Finalizar esta actividad nos llevará de vuelta a la actividad anterior en la pila
            finish()
        }
    }
}
