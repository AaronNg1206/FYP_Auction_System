package com.nghycp.fyp_auction_system.report

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentWeeklyBasedBinding
import java.text.SimpleDateFormat
import java.util.*


class weeklyBasedFragment : Fragment() {

    private var _binding: FragmentWeeklyBasedBinding? = null
    private lateinit var WeeklyReportList: ArrayList<ModelReport>
    private val binding get() = _binding!!
    private val weeklyViewModel: WeeklyViewModel by activityViewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val weeklyViewModel =
            ViewModelProvider(this).get(WeeklyViewModel::class.java)

        _binding = FragmentWeeklyBasedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.RecyclerViewMonthlyReport)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        WeeklyReportList = arrayListOf<ModelReport>()
    }
    override fun onStart() {
        super.onStart()

        binding.button.setOnClickListener() {

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val selectedDate: String = sdf.format(getDateFromDatePicker(binding.datePicker1))

            val linearLayoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
            val context: Context = this.requireContext()
            weeklyViewModel.loadWeeklyList(selectedDate, object: ReportCallback {
                override fun onCallBack(value: List<ModelReport>) {

                    binding.RecyclerViewMonthlyReport.adapter = ReportAdapter(value, context)
                }
            })
            totalCal()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getDateFromDatePicker(datePicker: DatePicker): Date? {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.getTime()
    }

    private fun totalCal() {
        var total = 0.0
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("paid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                total = 0.0
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelReport::class.java)

                    val artPrice = model?.artPrice?.toDoubleOrNull()
                    total += artPrice!!
                    binding.TextViewTotalSales.text = total.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                try {

                }catch (e: Exception){
                    Log.d("c",e.toString())
                }

            }

        })
    }
}