package com.example.medicalapp

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide


class DoctorAdapter(
    private val doctorList: ArrayList<UserProfileTwo>,val doctorItemData: DoctorItemData
) :RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullname: TextView = itemView.findViewById(R.id.Id_tv_name_one)
        val qualification: TextView = itemView.findViewById(R.id.Id_tv_qual_one)
        val experience: TextView = itemView.findViewById(R.id.Id_tv_exp_one)
        val Image: ImageView = itemView.findViewById(R.id.cardiodr)
        val itemList:LinearLayout=itemView.findViewById(R.id.doctor_list_item);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_doctor_layout, parent, false)
        return DoctorViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val currentitem = doctorList[position]
        holder.fullname.text ="Name : " +currentitem.fullname
        holder.qualification.text = "Qualification : " +currentitem.qualification.toString()
        holder.experience.text ="Experience : "+ currentitem.experience.toString()+" years"
        Glide.with(holder.Image.context).load(currentitem.profileImgUrl)
            .into(holder.Image)



        holder.itemList.setOnClickListener({
          doctorItemData.handleDoctorItemData(currentitem)
        })

    }

    override fun getItemCount(): Int {
        return doctorList.size
    }
}

interface DoctorItemData{
    fun handleDoctorItemData(item: UserProfileTwo){}
}


