package com.example.medicalapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medicalapp.DepartmentList
import com.example.medicalapp.R

import com.example.medicalapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind= FragmentHomeBinding.inflate(layoutInflater)
        bind.medico = resources.getString(R.string.medico)
        bind.welcomeToMedico = resources.getString(R.string.welcome_to_medico)
        bind.allAppointments = resources.getString(R.string.allAppointments)
        bind.txtBookAppointment = resources.getString(R.string.book_appointment)
        bind.btnBookAppointment = resources.getString(R.string.btn_book_appointment)
        bind.txtMyAppointment = resources.getString(R.string.my_appointment)
        bind.myAppointmentBtn = resources.getString(R.string.btn_my_appointment)


        bind.bookAppointment.setOnClickListener{
            val intent= Intent(this@HomeFragment.requireContext(), DepartmentList::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return bind.root
    }

}