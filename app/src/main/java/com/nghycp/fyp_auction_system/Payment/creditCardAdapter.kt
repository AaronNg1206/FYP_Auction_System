package com.nghycp.fyp_auction_system.Payment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentCreditCardBinding
import com.nghycp.fyp_auction_system.databinding.FragmentCreditCardLayoutBinding

class CreditCardAdapter : RecyclerView.Adapter<CreditCardAdapter.HolderCard> {

    private val context: Context
    private val checkedCheckBox = mutableListOf<ModelCreditCard>()
    private var creditCardList: ArrayList<ModelCreditCard>
    private lateinit var binding: FragmentCreditCardLayoutBinding

    constructor(context: Context, creditCardList: ArrayList<ModelCreditCard>) {
        this.context = context
        this.creditCardList = creditCardList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCard {
        binding =
            FragmentCreditCardLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCard(binding.root)
    }

    override fun getItemCount(): Int {
        //number of items in list
        return creditCardList.size
    }
    inner class HolderCard(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var HolderName: TextView = binding.textViewHolderName
        var CardNumber: TextView = binding.textViewCardNumber
        var checkBoxCard: CheckBox = binding.checkBoxCard
    }
    override fun onBindViewHolder(holder: HolderCard, position: Int) {
        //get data
        val model = creditCardList[position]
        val cardHolderName = model.cardHolderName
        for (index in 2 until model.cardNumber.length - 2){
            model.cardNumber = model.cardNumber.replaceRange(index, index + 1, "*")
        }
        val cardNumber = model.cardNumber
        val expDate = model.expDate
        val cvv = model.cvv
        val id = model.id
        val checkBox = model.isChecked

        //set data
        holder.HolderName.text = cardHolderName
        holder.CardNumber.text = cardNumber
        holder.checkBoxCard.isChecked = checkBox

        holder.checkBoxCard.setOnCheckedChangeListener { _, isChecked ->
            model.isChecked = isChecked
            if (isChecked) {
                checkedCheckBox.add(model)
            } else {
                checkedCheckBox.remove(model)
            }
        }
    }



    init {
        this.creditCardList = ArrayList()
    }
    fun getCheckedItems(): List<ModelCreditCard> {
        return checkedCheckBox.toList()
    }
}