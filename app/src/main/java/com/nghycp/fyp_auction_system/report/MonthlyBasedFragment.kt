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
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.customer.cartAdapter
import com.nghycp.fyp_auction_system.databinding.FragmentMonthlyBasedBinding

import java.text.SimpleDateFormat
import java.util.*

class MonthlyBasedFragment : Fragment() {

    private var _binding: FragmentMonthlyBasedBinding? = null
    private lateinit var MonthlyReportList: ArrayList<ModelReport>
    private val binding get() = _binding!!
    private val monthlyViewModel: MonthlyViewModel by activityViewModels()
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val monthlyViewModel =
            ViewModelProvider(this).get(MonthlyViewModel::class.java)
        _binding = FragmentMonthlyBasedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.RecyclerViewMonthlyReport)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        MonthlyReportList = arrayListOf<ModelReport>()

    }
    override fun onStart() {
        super.onStart()
        binding.button.setOnClickListener() {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val selectedDate: String = sdf.format(getDateFromDatePicker(binding.datePicker1))

            val linearLayoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
            val context: Context = this.requireContext()
            monthlyViewModel.loadRecordList(selectedDate, object: ReportCallback {
                override fun onCallBack(value: List<ModelReport>) {
                    binding.RecyclerViewMonthlyReport.adapter = ReportAdapter(value, context)
                    val total = value.sumBy { it.artPrice.toInt() }
                    binding.TextViewTotalSales.text = "RM$total"
                }
            })

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





}