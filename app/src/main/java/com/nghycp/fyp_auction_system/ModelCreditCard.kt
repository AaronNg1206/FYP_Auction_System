package com.nghycp.fyp_auction_system

class ModelCreditCard {

    var id:String = ""
    var cardHolderName:String = ""
    var cardNumber:String = ""
    var expDate:String= ""
    var cvv:String= ""
    var uid:String = ""

    constructor()

    constructor(
        cardHolderName: String,
        cardNumber: String,
        expDate: String,
        cvv: String,
        uid: String,
        id: String = "",
    ){
        this.id = id
        this.cardHolderName = cardHolderName
        this.cardNumber = cardNumber
        this.expDate = expDate
        this.cvv = cvv
        this.uid = uid
    }
}