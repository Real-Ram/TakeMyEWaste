package com.example.takemyewaste.user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.admin.AddEwasteDetails
import com.example.takemyewaste.databinding.ActivityNewPickUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class NewPickUpActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityNewPickUpBinding

    //firbase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private  lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPickUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, begin upload category
        binding.confirmBtn.setOnClickListener {
            validateData()
        }


        //generating time for the pickup date
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val date1 = simpleDateFormat.format(calendar.time)

        binding.timeanddateBtn.setOnClickListener {
            binding.timeanddateEt.setText(date1)
        }

    }

    private var name = ""
    private var mobile = ""
    private var product = ""
    private var address = ""
    private var time = ""

    private fun validateData() {
        //get data
        name = binding.nameEt.text.toString().trim()
        mobile = binding.mobileEt.text.toString().trim()
        product = binding.productsEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()
        time = binding.timeanddateEt.text.toString().trim()


        //validate data
        if(name.isEmpty()){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
        }
        else if(mobile.isEmpty()){
            Toast.makeText(this, "Enter mobile", Toast.LENGTH_SHORT).show()
        }
        else if(product.isEmpty()){
            Toast.makeText(this, "Enter products", Toast.LENGTH_SHORT).show()
        }
        else if(address.isEmpty()){
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show()
        }
        else if(time.isEmpty()){
            Toast.makeText(this, "Enter time", Toast.LENGTH_SHORT).show()
        }
        else {
            addFaqFirebase()
        }
    }

    private fun addFaqFirebase() {
        //show progressdialog
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap = HashMap<String,Any>()
        hashMap["id"] = "$timestamp"
        hashMap["name"] = name
        hashMap["mobile"] = mobile
        hashMap["product"] = product
        hashMap["address"] = address
        hashMap["time"] = time
        hashMap["uid"] = "${firebaseAuth.uid}"


        //for User
        //add to firebase db: Database Root > Categories > CategoryId > CategoryInfo
        val ref = FirebaseDatabase.getInstance().getReference("PendingPickup-user")
        ref.child(firebaseAuth.uid!!).child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully....", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@NewPickUpActivity, DashboardUserActivity::class.java))
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

        //for Admin
        val ref1 = FirebaseDatabase.getInstance().getReference("PendingPickup-admin")
        ref1.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }

    }
}