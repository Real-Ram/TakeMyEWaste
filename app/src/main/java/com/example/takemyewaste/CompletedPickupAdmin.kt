package com.example.takemyewaste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.databinding.ActivityCancelledPickupAdminBinding
import com.example.takemyewaste.databinding.ActivityCompletedPickupAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompletedPickupAdmin : AppCompatActivity() {

    //View binding
    private lateinit var binding: ActivityCompletedPickupAdminBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold categories
    private lateinit var pickupStatusArrayList: ArrayList<ModelPickUp>

    //adapter
    private lateinit var adapterCompletedPickupAdmin: AdapterCompletedPickupAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletedPickupAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        loadPickupStatus()

    }

    //show the pickup status
    private fun loadPickupStatus() {
        //init arraylisdt
        pickupStatusArrayList = ArrayList()


        //get all categories from firebase database....Firebase DB >Categories
        val ref = FirebaseDatabase.getInstance().getReference("Completed_order")
        ref.orderByChild("uid")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //clear list before starting adding data into it
                    pickupStatusArrayList.clear()
                    for (ds in snapshot.children){
                        //get data as model
                        val model = ds.getValue(ModelPickUp::class.java)

                        //add to arraylist
                        pickupStatusArrayList.add(model!!)
                    }
                    //setup adapter
                    adapterCompletedPickupAdmin = AdapterCompletedPickupAdmin(this@CompletedPickupAdmin, pickupStatusArrayList)

                    //set adapter to recyclerview
                    binding.pickupStatusRv.adapter = adapterCompletedPickupAdmin
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}