package com.example.takemyewaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@DashboardUserActivity, MainActivity::class.java))
        }

        binding.textViewSub1Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub3Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub5Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub2Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub6Title.setOnClickListener {
            startActivity(Intent(this,NewPickUpActivity::class.java))
        }

        binding.textViewSub4Title.setOnClickListener {
            startActivity(Intent(this,UpdateDataActivity::class.java))
        }
    }
}