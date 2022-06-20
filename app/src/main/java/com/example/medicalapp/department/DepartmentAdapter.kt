package com.example.medicalapp.department

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medicalapp.R
import java.text.FieldPosition

class DepartmentAdapter(private val context: Context, private val departments: List<DepartmentModel>): RecyclerView.Adapter<DepartmentViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener= clickListener
    }

    override fun getItemCount(): Int {
        return departments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.department_layout,parent,false)
        return DepartmentViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
         val user = departments[position]
        holder.DeptName.text =user.DeptName

        Glide.with(context)
            .load(user.DeptImage)
            .into(holder.DeptImage)


    }

}
class DepartmentViewHolder(itemView: View, clickListener: DepartmentAdapter.onItemClickListener): RecyclerView.ViewHolder(itemView){
    val DeptImage: ImageView = itemView.findViewById(R.id.id_Dept_Image)
    val DeptName: TextView= itemView.findViewById(R.id.id_Dept_Name)

    init {
        itemView.setOnClickListener{
            clickListener.onItemClick(adapterPosition)
        }
    }
}