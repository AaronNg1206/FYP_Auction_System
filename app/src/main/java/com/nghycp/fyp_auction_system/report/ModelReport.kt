package com.nghycp.fyp_auction_system.report

import java.util.*

class ModelReport {

    var id:String = ""
    var artAuthor:String = ""
    var artDescription:String = ""
    var artName:String = ""
    var artPrice:String = ""
    var artImage:String= ""
    var uid:String = ""
    var imageURL: String = ""
    var timestamp : String = ""
    var status : String = ""
    var PID : String = ""

    constructor()

    constructor(
        artAuthor: String,
        artDescription: String,
        artName: String,
        artPrice: String,
        artImage: String,
        uid: String,
        imageURL: String,
        id: String = "",
        timestamp: String,
        status : String,
        PID : String
    ){
        this.id = id
        this.artAuthor = artAuthor
        this.artDescription = artDescription
        this.artName = artName
        this.artPrice = artPrice
        this.artImage = artImage
        this.uid = uid
        this.imageURL = imageURL
        this.timestamp = timestamp
        this.status = status
        this.PID = PID
    }
}