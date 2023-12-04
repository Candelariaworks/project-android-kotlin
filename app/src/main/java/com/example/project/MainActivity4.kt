package com.example.project

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import java.io.InputStream

class MainActivity4 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var currentItemPosition: Int = -1

    // ActivityResultLauncher para seleccionar la imagen
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { imageUri ->
                val bitmap = getBitmapFromUri(imageUri)
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        currentItemPosition = intent.getIntExtra("position", -1)

        imageView = findViewById(R.id.imageView)

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")

        val textViewTitulo = findViewById<TextView>(R.id.textViewTitulo)
        val textViewDescripcion = findViewById<TextView>(R.id.textViewDescripcion)
        textViewTitulo.text = titulo ?: ""
        textViewDescripcion.text = descripcion ?: ""

        // Solicitar permiso si no está concedido
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }

        // Recuperar la URI guardada y mostrar la imagen si está disponible
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val savedImageUriString = sharedPref.getString("savedImageUri_$currentItemPosition", null)
        if (!savedImageUriString.isNullOrEmpty()) {
            val savedImageUri = Uri.parse(savedImageUriString)
            val bitmap = getBitmapFromUri(savedImageUri)
            imageView.setImageBitmap(bitmap)
        }

        val buttonPickImage = findViewById<Button>(R.id.buttonPickImage)
        buttonPickImage.setOnClickListener {
            pickImage()
        }

        val buttonGuardarImagen = findViewById<Button>(R.id.buttonGuardarImagen)
        buttonGuardarImagen.setOnClickListener {
            saveImage()
        }

        val buttonVolver = findViewById<MaterialButton>(R.id.buttonVolver)
        buttonVolver.setOnClickListener {
            finish()
        }
    }

    private fun pickImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(galleryIntent)
    }

    private fun saveImage() {
        val imageUri = getImageUriFromImageView(imageView)
        if (imageUri != null && currentItemPosition != -1) {
            // Guardar la URI de la imagen en SharedPreferences
            val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            sharedPref.edit().putString("savedImageUri_$currentItemPosition", imageUri.toString()).apply()
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val contentResolver: ContentResolver = contentResolver
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getImageUriFromImageView(imageView: ImageView): Uri? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val path = MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                "Image Description",
                null
            )
            if (path != null) {
                return Uri.parse(path)
            }
        }
        return null
    }
}
