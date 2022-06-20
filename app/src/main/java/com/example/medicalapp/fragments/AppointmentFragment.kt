package com.example.medicalapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medicalapp.databinding.FragmentAppointmentBinding
import com.example.medicalapp.doctorList.AllDoctorsList

class AppointmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind= FragmentAppointmentBinding.inflate(layoutInflater)

        bind.cardioCard.setOnClickListener{
            val intent= Intent(this@AppointmentFragment.requireContext(), AllDoctorsList::class.java)
            intent.putExtra("message_key","Cardiology");
            startActivity(intent)
        }
        bind.gynaecologyCard.setOnClickListener{
            val intent= Intent(this@AppointmentFragment.requireContext(), AllDoctorsList::class.java)
            intent.putExtra("message_key","Gynaecology");
            startActivity(intent)
        }
        bind.neuroCard.setOnClickListener {
            val intent =
                Intent(this@AppointmentFragment.requireContext(), AllDoctorsList::class.java)
            intent.putExtra("message_key", "Neurology");
            startActivity(intent)
        }
        bind.dermatologyCard.setOnClickListener {
            val intent =
                Intent(this@AppointmentFragment.requireContext(), AllDoctorsList::class.java)
            intent.putExtra("message_key", "Dermatology");
            startActivity(intent)
        }
        bind.orthopedicsCard.setOnClickListener {
            val intent =
                Intent(this@AppointmentFragment.requireContext(), AllDoctorsList::class.java)
            intent.putExtra("message_key", "Orthopaedics");
            startActivity(intent)
        }



        // Inflate the layout for this fragment
        return bind.root
    }


}