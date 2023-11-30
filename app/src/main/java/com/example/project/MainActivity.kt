
package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailEditText = findViewById<EditText>(R.id.editTextText)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val signInButton = findViewById<Button>(R.id.button)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email == "usuario@example.com" && password == "contraseña123") {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email/Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show()
            }
        }
    }
}
