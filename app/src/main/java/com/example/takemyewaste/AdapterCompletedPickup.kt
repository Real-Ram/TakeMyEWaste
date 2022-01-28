package com.example.takemyewaste

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takemyewaste.databinding.RowCancelledPickupBinding
import com.example.takemyewaste.databinding.RowCompletedPickupBinding
import com.example.takemyewaste.databinding.RowPickupStatusBinding

class AdapterCompletedPickup: RecyclerView.Adapter<AdapterCompletedPickup.HolderCategory> {

    private  val context: Context
    public var pickupStatusArrayList:ArrayList<ModelPickUp>
    private var filterList: ArrayList<ModelPickUp>

    private lateinit var binding: RowCompletedPickupBinding

    //constructor
    constructor(context: Context, pickupStatusArrayList: ArrayList<ModelPickUp>) {
        this.context = context
        this.pickupStatusArrayList = pickupStatusArrayList
        this.filterList = pickupStatusArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowCompletedPickupBinding.inflate(LayoutInflater.from(context), parent, false)
        return  HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {

        //get data
        val model = pickupStatusArrayList[position]
        val id = model.id
        val name = model.name
        val mobile = model.mobile
        val product = model.product
        val address = model.address
        val time = model.time
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.nameTv.text = name
        holder.mobileTv.text = mobile
        holder.productTv.text = product
        holder.addressTv.text = address
        holder.timeTv.text = time

    }

    override fun getItemCount(): Int {
        return pickupStatusArrayList.size
    }


    //Viewholder class to hold/init UI views for row_category.xml
    inner class HolderCategory(itemView: View): RecyclerView.ViewHolder(itemView) {
        //init ui views
        var nameTv: TextView = binding.nameEt
        var mobileTv: TextView = binding.mobileEt
        var productTv: TextView = binding.productEt
        var addressTv: TextView = binding.addressEt
        var timeTv: TextView = binding.timeEt
    }
}