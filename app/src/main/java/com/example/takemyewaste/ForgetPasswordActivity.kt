package com.example.takemyewaste

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //Progress Dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.recoverBtn.setOnClickListener {
            recoverPasswordFunction()
        }

        binding.rememberPassIv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }

    private var email = ""
    private fun recoverPasswordFunction() {
        progressDialog.setMessage("Sending password reset email....")
        progressDialog.show()
        email = binding.emailEt.text.toString().trim()
        if (email.isEmpty()) {
            Toast.makeText(this, "Please Enter Valid Email....", Toast.LENGTH_SHORT).show()
        }
        else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Reset password email has been sent to user successfully....", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e->
                    //failed login
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed to send email due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}