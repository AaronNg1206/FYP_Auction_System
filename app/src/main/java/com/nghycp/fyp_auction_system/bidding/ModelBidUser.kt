package com.nghycp.fyp_auction_system.bidding

class ModelBidUser {

    var userName:String = ""
    var price:String = ""
    //var totalPrice:String = ""
    var img:String = ""
    var uid:String = ""
    var name: String = ""

    constructor()

    constructor(
        userName: String,
        img: String,
        uid: String,
        name: String,
        price: String
        //totalPrice: String
    )

    {
        this.userName = userName
        this.price = price
        this.img = img
        this.uid = uid
        this.name = name
        //this.totalPrice = totalPrice
    }

}