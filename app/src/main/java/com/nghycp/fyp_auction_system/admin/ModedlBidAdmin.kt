package com.nghycp.fyp_auction_system.admin

class ModedlBidAdmin {

    var id:String = ""
    var artist:String = ""
    var desc:String = ""
    var name:String = ""
    var price:String = ""
    var expDate:String = ""
    var profileImage:String= ""
    //var uid:String = ""

    constructor()

    constructor(
        id: String,
        artist: String,
        desc: String,
        name: String,
        price: String,
        expDate: String,
        profileImage: String,
        //uid: String,
    ){
        this.id = id
        this.artist = artist
        this.desc = desc
        this.name = name
        this.price = price
        this.expDate = expDate
        this.profileImage = profileImage
        //this.uid = uid
    }

}