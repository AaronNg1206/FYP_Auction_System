package com.nghycp.fyp_auction_system.history_return

class ModelShow {

    var id:String = ""
    var artImage:String = ""
    var artName:String = ""
    var artPrice:String = ""
    var status:String = ""
    var uid:String = ""


    constructor()
    constructor(
        id: String,
        artImage:String,
        artName:String,
        artPrice:String,
        uid:String,
        status:String
    )
    {
        this.id = id
        this.status = status
        this.artImage = artImage
        this.artName = artName
        this.artPrice = artPrice
        this.uid = uid
    }

}