package com.dara.finderjob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dara.finderjob.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        supportActionBar?.title = "Register"

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.tilEmail.error = "Email is required"
                    binding.etEmail.requestFocus()
                }
                password.isEmpty() -> {
                    binding.tilPassword.error = "Password is required"
                    binding.etPassword.requestFocus()
                }
                password.length < 6 -> {
                    binding.tilPassword.error = "Password must be at least 6 characters"
                    binding.etPassword.requestFocus()
                }
                confirmPassword != password -> {
                    binding.tilConfirmPassword.error = "Passwords do not match"
                    binding.etConfirmPassword.requestFocus()
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("EMAIL", email)
                                startActivity(intent)
                                finish()
                            } else {
                                startActivity(Intent(this@SignupActivity, "Registration failed: ${task.exception?.message}"::class.java))
                            }
                        }
                }
            }

        }

    }
}