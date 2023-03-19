package com.nghycp.fyp_auction_system.bidding

class ModelBid {

    var PID:String = ""
    var artist:String = ""
    var desc:String = ""
    var name:String = ""
    var price:String = ""
    var expDate:String = ""
    var profileImage:String= ""

    constructor()

    constructor(
        PID: String,
        artist: String,
        desc: String,
        name: String,
        price: String,
        expDate: String,
        profileImage: String,
    ){
        this.PID = PID
        this.artist = artist
        this.desc = desc
        this.name = name
        this.price = price
        this.expDate = expDate
        this.profileImage = profileImage
    }
}