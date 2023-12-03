package com.example.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity4 : AppCompatActivity() {
    private lateinit var imageView: ImageView

    // ActivityResultLauncher para seleccionar la imagen
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            imageView.setImageURI(imageUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")

        val textViewTitulo = findViewById<TextView>(R.id.textViewTitulo)
        val textViewDescripcion = findViewById<TextView>(R.id.textViewDescripcion)
        textViewTitulo.text = titulo ?: ""
        textViewDescripcion.text = descripcion ?: ""

        val buttonVolver = findViewById<MaterialButton>(R.id.buttonVolver)
        buttonVolver.setOnClickListener {
            finish()
        }

        imageView = findViewById(R.id.imageView)

        val buttonPickImage = findViewById<Button>(R.id.buttonPickImage)
        buttonPickImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pickImageLauncher.launch(galleryIntent)
        }
    }
}
