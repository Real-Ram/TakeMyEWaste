package com.example.takemyewaste

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.databinding.ActivityDashboardDeliveryboyBinding
import com.example.takemyewaste.user.NewPickUpActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardDeliveryboy : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardDeliveryboyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardDeliveryboyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("LOGOUT")
                .setMessage("Are you sure you want to logout now?")
                .setPositiveButton("Confirm"){ a, d->
                    Toast.makeText(this, "logging out....", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

        binding.profileDataBtn.setOnClickListener {
            startActivity(Intent(this, ProfileData::class.java))
        }

        binding.approvedPickupBtn.setOnClickListener {
            startActivity(Intent(this, ApprovedPickupDeliveryboy::class.java))
        }

        binding.underDeliveryPickupBtn.setOnClickListener {
            //startActivity(Intent(this, ::class.java))
            Toast.makeText(this, " You Pressed under delivery button....", Toast.LENGTH_SHORT).show()
        }

        binding.completedPickupBtn.setOnClickListener {
            //startActivity(Intent(this, CompletedPickup::class.java))
            Toast.makeText(this, " You Pressed completed delivery button....", Toast.LENGTH_SHORT).show()
        }

        binding.aboutUsBtn.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }
    }
}