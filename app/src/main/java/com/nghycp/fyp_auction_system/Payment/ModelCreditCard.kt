package com.nghycp.fyp_auction_system.Payment

class ModelCreditCard {

    var id:String = ""
    var cardHolderName:String = ""
    var cardNumber:String = ""
    var expDate:String= ""
    var cvv:String= ""
    var uid:String = ""
    var isChecked: Boolean = false

    constructor()

    constructor(
        cardHolderName: String,
        cardNumber: String,
        expDate: String,
        cvv: String,
        uid: String,
        id: String = "",
        isChecked :Boolean,
    ){
        this.id = id
        this.cardHolderName = cardHolderName
        this.cardNumber = cardNumber
        this.expDate = expDate
        this.cvv = cvv
        this.uid = uid
        this.isChecked = isChecked
    }
}