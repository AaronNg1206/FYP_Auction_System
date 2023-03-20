package com.nghycp.fyp_auction_system.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nghycp.fyp_auction_system.customer.ArtworkAdapter
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentMonthlyBasedBinding

import java.text.SimpleDateFormat
import java.util.*

class monthlyBasedFragment : Fragment() {

    private var _binding: FragmentMonthlyBasedBinding? = null

    private val binding get() = _binding!!

    //private val ReportViewModel: ReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
   /*     val galleryViewModel =
            ViewModelProvider(this).get(ReportViewModel::class.java)*/

        _binding = FragmentMonthlyBasedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onStart() {
        super.onStart()

        binding.button.setOnClickListener() {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val dateString: String = sdf.format(getDateFromDatePicker(binding.datePicker1))

            //loadReport(dateString)
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