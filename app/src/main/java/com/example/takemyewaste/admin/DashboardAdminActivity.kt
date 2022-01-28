package com.example.takemyewaste.admin

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.*
import com.example.takemyewaste.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
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

        binding.approvePickupBtn.setOnClickListener {
            Toast.makeText(this, "You clicked Approve Pick-Up Button", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ApprovePickup::class.java))
        }

        binding.completedPickupBtn.setOnClickListener {
            startActivity(Intent(this, CompletedPickupAdmin::class.java))
        }

        binding.cancelledPickupBtn.setOnClickListener {
            startActivity(Intent(this, CancelledPickupAdmin::class.java))
        }

        binding.aboutUsBtn.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

        binding.deliveryAgentsBtn.setOnClickListener {
            Toast.makeText(this, "You clicked Delivery Agent Button", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DeliveryAgents::class.java))

        }

        binding.EwasteInfoBtn.setOnClickListener {
            startActivity(Intent(this, AddEwasteDetails::class.java))
        }
    }
}