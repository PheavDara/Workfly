package com.dara.finderjob

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dara.finderjob.SignupActivity
import com.dara.finderjob.databinding.ActivityPhoneLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        // Back button
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Send OTP button
        binding.sendOtpButton.setOnClickListener {
            val phoneNumber = binding.phoneEditText.text.toString().trim()
            if (phoneNumber.isEmpty() || phoneNumber.length < 10) {
                binding.phoneInputLayout.error = "Enter a valid phone number"
                return@setOnClickListener
            }
            sendVerificationCode("+1$phoneNumber") // Adjust country code as needed
        }

        // Verify OTP button
        binding.verifyOtpButton.setOnClickListener {
            val otp = binding.otpEditText.text.toString().trim()
            if (otp.isEmpty() || otp.length != 6) {
                binding.otpInputLayout.error = "Enter valid OTP"
                return@setOnClickListener
            }
            verifyCode(otp)
        }

        // Signup button
        binding.btnSignup.setOnClickListener {
            // Navigate to signup activity
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@PhoneLoginActivity, e.message, Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = id
                    binding.phoneInputLayout.isEnabled = false
                    binding.sendOtpButton.visibility = View.GONE
                    binding.otpInputLayout.visibility = View.VISIBLE
                    binding.verifyOtpButton.visibility = View.VISIBLE
                    Toast.makeText(this@PhoneLoginActivity, "OTP Sent", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to main activity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                }
            }
    }
}