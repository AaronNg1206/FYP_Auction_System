package com.nghycp.fyp_auction_system.report

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.nghycp.fyp_auction_system.R
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.nghycp.fyp_auction_system.databinding.FragmentBarChartBinding

class BarChartFragment : Fragment() {
    private var _binding: FragmentBarChartBinding? = null
    private val binding get() = _binding!!
    //private lateinit var pieChartList: ArrayList<ModelReport>

    // on below line we are creating
    // variables for our bar chart
    lateinit var barChart: BarChart

    // on below line we are creating
    // a variable for bar data
    lateinit var barData: BarData

    // on below line we are creating a
    // variable for bar data set
    lateinit var barDataSet: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBarChartBinding.inflate(inflater, container, false)
        barChart = binding.idBarChart
        // on below line we are initializing
        // our variable with their ids.

       // barChart = findViewById(R.id.idTVHead)

        // on below line we are calling get bar
        // chart data to add data to our array list
        getBarChartData()

        // on below line we are initializing our bar data set
        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")

        // on below line we are initializing our bar data
        barData = BarData(barDataSet)

        // on below line we are setting data to our bar chart
        barChart.data = barData

        // on below line we are setting colors for our bar chart text
        barDataSet.valueTextColor = Color.BLACK

        // on below line we are setting color for our bar data set
        barDataSet.setColor(resources.getColor(R.color.purple_200))

        // on below line we are setting text size
        barDataSet.valueTextSize = 16f

        // on below line we are enabling description as false
        barChart.description.isEnabled = false

        return binding.root
    }

        private fun getBarChartData() {
            barEntriesList = ArrayList()

            // on below line we are adding data
            // to our bar entries list
            barEntriesList.add(BarEntry(1f, 1f))
            barEntriesList.add(BarEntry(2f, 2f))
            barEntriesList.add(BarEntry(3f, 3f))
            barEntriesList.add(BarEntry(4f, 4f))
            barEntriesList.add(BarEntry(5f, 5f))

        }
}