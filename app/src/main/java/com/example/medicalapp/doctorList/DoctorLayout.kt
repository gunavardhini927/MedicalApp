package com.example.medicalapp.doctorList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.example.medicalapp.databinding.ActivityDoctorLayoutBinding
import com.google.firebase.database.DatabaseReference


class DoctorLayout : AppCompatActivity() {

    private lateinit var binding : ActivityDoctorLayoutBinding
    private lateinit var databasereference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

       }

    }




