package com.dara.finderjob

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SigninSignupActivity : AppCompatActivity() {
    private lateinit var btnGoogle: Button
    private lateinit var btnFacebook: Button
    private lateinit var btnPhone: Button
    private lateinit var btnSignup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnGoogle = findViewById<Button>(R.id.btnGoogle)
        btnFacebook = findViewById<Button>(R.id.btnFacebook)
        btnPhone = findViewById<Button>(R.id.btnPhone)
        btnSignup = findViewById<Button>(R.id.btnSignup)

        btnGoogle.setOnClickListener {
            startActivity(Intent(this@SigninSignupActivity, LoginActivity::class.java))
        }
        btnPhone.setOnClickListener {
            startActivity(Intent(this@SigninSignupActivity, PhoneLoginActivity::class.java))
        }
        btnSignup.setOnClickListener {
            startActivity(Intent(this@SigninSignupActivity, SignupActivity::class.java))
        }
    }
}