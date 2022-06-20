package com.example.medicalapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.medicalapp.databinding.ActivitySchedulePageBinding
import com.example.medicalapp.doctorList.AllDoctorsList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class SchedulePage : AppCompatActivity() {
    private lateinit var binding:ActivitySchedulePageBinding
    var firebaseDatabase: FirebaseDatabase? = null


    var databaseReference: DatabaseReference? = null
    private lateinit var doctorDataFromDepartments:UserProfileTwo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.intent.extras
        if (bundle != null) {
            doctorDataFromDepartments = (bundle.getSerializable("doctor") as UserProfileTwo?)!!
        }



        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_page)
        binding.txtSchedule = resources.getString(R.string.sc_txt_schedule)
        binding.btnDate = resources.getString(R.string.sc_btn_date)
        binding.txtDate = resources.getString(R.string.sc_txt_date)
        binding.btnTime = resources.getString(R.string.sc_btn_time)
        binding.txtTime = resources.getString(R.string.sc_txt_time)
        binding.btnAppointment = resources.getString(R.string.sc_btn_apt)
        binding.btnCancel = resources.getString(R.string.sc_btn_cancel)
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase!!.getReference("appointmentDetails");


        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
            updateLable(myCalendar)
        }
        binding.btnDatePicker.setOnClickListener {
            DatePickerDialog(this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        binding.btnTimePicker.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.tvTime.setText("$hourOfDay:$minute")
            }, startHour, startMinute, false).show()
        }

        val fireBaseData= FirebaseDatabase.getInstance()
        binding.btnBookAppointment.setOnClickListener {
            val data=appointmentDetails()
            data.date=binding.tvDate.text.toString().trim()
            data.time=binding.tvTime.text.toString().trim()
            data.doctor=doctorDataFromDepartments
            data.PatientName= binding.IdEtPatientName.text.toString().trim()
            fireBaseData.getReference("appointmentDetails").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(data).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(
                        this,
                        " Appointment Booked successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    Toast.makeText(
                        this,
                        "Appointment is not booked due to error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        //    addDatatoFirebase(data)


        }

        binding.btnCancelApt.setOnClickListener {
            val intent = Intent(this, AllDoctorsList::class.java)
            startActivity(intent)
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.tvDate.setText(sdf.format(myCalendar.time))

    }


//    private fun addDatatoFirebase(data:appointmentDetails) {
//
//        databaseReference!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//
//
//                databaseReference!!.setValue(data)
//
//
//                Toast.makeText(this@SchedulePage, "Appointment is booked successfully", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//                Toast.makeText(this@SchedulePage, "Fail to add data $error", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }
    }
