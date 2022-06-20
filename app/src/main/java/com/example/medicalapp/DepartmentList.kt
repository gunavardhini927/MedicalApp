package com.example.medicalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicalapp.databinding.ActivityDepartmentListBinding
import com.example.medicalapp.department.DepartmentAdapter
import com.example.medicalapp.department.DepartmentModel
import com.example.medicalapp.doctorList.AllDoctorsList
import com.google.firebase.firestore.FirebaseFirestore

class DepartmentList : AppCompatActivity() {

    private lateinit var binding: ActivityDepartmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDepartmentListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DepartmentList)
        }
        fetchData()
    }

    private fun fetchData(){
        FirebaseFirestore.getInstance().collection("Hospital Department")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    val user =documents.toObjects(DepartmentModel::class.java)
                    val adapter= DepartmentAdapter(this,user)
                    binding.mainRecyclerView.adapter =adapter

                    adapter.setOnItemClickListener(object : DepartmentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@DepartmentList, AllDoctorsList::class.java)
                            startActivity(intent)

                        }
                    })
                }
            }

    }
}