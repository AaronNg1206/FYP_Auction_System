package com.nghycp.fyp_auction_system.admin

class ModelReturn {

    var PID:String = ""
    var imageArt:String = ""
    var nameArt:String = ""
    var priceArt:String = ""
    var status:String = ""
    var uid:String = ""
    var reason:String = ""
    var desc:String = ""

    constructor()
    constructor(
        PID: String,
        imageArt:String,
        nameArt:String,
        priceArt:String,
        uid:String,
        desc:String,
        reason:String,
        status:String
    )
    {
        this.PID = PID
        this.status = status
        this.imageArt = imageArt
        this.nameArt = nameArt
        this.priceArt = priceArt
        this.desc = desc
        this.reason = reason
        this.uid = uid
    }

}