package com.example.takemyewaste.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takemyewaste.ModelFaqs
import com.example.takemyewaste.databinding.RowFaqsUserBinding

class AdapterFaqsUser: RecyclerView.Adapter<AdapterFaqsUser.HolderCategory> {

    private  val context: Context
    public var faqArrayList:ArrayList<ModelFaqs>
    private var filterList: ArrayList<ModelFaqs>

    private lateinit var binding: RowFaqsUserBinding

    //constructor
    constructor(context: Context, faqArrayList: ArrayList<ModelFaqs>) {
        this.context = context
        this.faqArrayList = faqArrayList
        this.filterList = faqArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowFaqsUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return  HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {

        //get data
        val model = faqArrayList[position]
        val id = model.id
        val question = model.question
        val answer = model.answer
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.quetionTv.text = question
        holder.answerTv.text = answer

    }

    override fun getItemCount(): Int {
        return faqArrayList.size
    }


    //Viewholder class to hold/init UI views for row_category.xml
    inner class HolderCategory(itemView: View): RecyclerView.ViewHolder(itemView) {
        //init ui views
        var quetionTv: TextView = binding.QuesTv
        var answerTv: TextView = binding.AnsTv
    }
}