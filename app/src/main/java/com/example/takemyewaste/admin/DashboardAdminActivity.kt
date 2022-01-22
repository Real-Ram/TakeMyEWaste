package com.example.takemyewaste.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.ApprovePickup
import com.example.takemyewaste.LoginActivity
import com.example.takemyewaste.ProfileData
import com.example.takemyewaste.user.NewPickUpActivity
import com.example.takemyewaste.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.profileDataBtn.setOnClickListener {
            startActivity(Intent(this, ProfileData::class.java))
        }

        binding.approvePickupBtn.setOnClickListener {
            Toast.makeText(this, "You clicked Approve Pick-Up Button", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ApprovePickup::class.java))

        }

        binding.allPickUpHistoryBtn.setOnClickListener {
            Toast.makeText(this, "You clicked All Pick-Up History Button", Toast.LENGTH_SHORT).show()
        }

        binding.EwasteInfoBtn.setOnClickListener {
            startActivity(Intent(this, AddEwasteDetails::class.java))
        }
    }
}