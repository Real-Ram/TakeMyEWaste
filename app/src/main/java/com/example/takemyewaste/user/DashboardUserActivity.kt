package com.example.takemyewaste.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.LoginActivity
import com.example.takemyewaste.PickUpStatusActivity
import com.example.takemyewaste.ProfileData
import com.example.takemyewaste.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.newPickupBtn.setOnClickListener {
            startActivity(Intent(this, NewPickUpActivity::class.java))
        }

        binding.pickUpHistoryBtn.setOnClickListener {
            Toast.makeText(this, "You clicked Pick Up History Button", Toast.LENGTH_SHORT).show()
        }


        binding.pickupStatusBtn.setOnClickListener {
            startActivity(Intent(this, PickUpStatusActivity::class.java))
        }

        binding.EWasteFaqBtn.setOnClickListener {
            startActivity(Intent(this, ShowEWasteFaqUser::class.java))
        }

        binding.profileDataBtn.setOnClickListener {
            startActivity(Intent(this, ProfileData::class.java))
        }
    }
}