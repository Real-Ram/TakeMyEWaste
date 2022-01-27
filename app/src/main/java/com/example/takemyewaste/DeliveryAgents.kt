package com.example.takemyewaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.admin.AdapterFaqs
import com.example.takemyewaste.admin.AddFaq
import com.example.takemyewaste.databinding.ActivityAddEwasteDetailsBinding
import com.example.takemyewaste.databinding.ActivityDeliveryAgentsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import layout.AdapterDeliveryAgents

class DeliveryAgents : AppCompatActivity() {

    //View binding
    private lateinit var binding: ActivityDeliveryAgentsBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold categories
    private lateinit var deliArrayList: ArrayList<ModelDeliveryAgents>

    //adapter
    private lateinit var adapterDeli: AdapterDeliveryAgents

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryAgentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        loadDeliveryAgents()


        //Add Delivery agents Button
        binding.deliveryAgentsBtn.setOnClickListener {
            startActivity(Intent(this, AddDeliveryAgents::class.java))
        }

    }

    private fun loadDeliveryAgents() {
        //init arraylisdt
        deliArrayList = ArrayList()

        //get all categories from firebase database....Firebase DB >Categories
        val ref = FirebaseDatabase.getInstance().getReference("DeliveryAgents")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                deliArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelDeliveryAgents::class.java)

                    //add to arraylist
                    deliArrayList.add(model!!)
                }
                //setup adapter
                adapterDeli = AdapterDeliveryAgents(this@DeliveryAgents, deliArrayList)

                //set adapter to recyclerview
                binding.deliveryAgentsRv.adapter = adapterDeli
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}