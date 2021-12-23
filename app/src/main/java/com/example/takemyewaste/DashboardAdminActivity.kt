package com.example.takemyewaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.databinding.ActivityDashboardAdminBinding
import com.example.takemyewaste.databinding.ActivityLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@DashboardAdminActivity, MainActivity::class.java))
        }

        binding.textViewSub1Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub2Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub3Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub4Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }
    }
}