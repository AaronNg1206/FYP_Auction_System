package com.nghycp.fyp_auction_system.customer

import java.util.*

class ModelArtwork {

    var id:String = ""
    var artAuthor:String = ""
    var artDescription:String = ""
    var artName:String = ""
    var artPrice:String = ""
    var artImage:String= ""
    var uid:String = ""
    var imageURL: String = ""
    var date : String = ""
    var cartId :String =""
    var isChecked: Boolean = false

    constructor()

    constructor(
        artAuthor: String,
        artDescription: String,
        artName: String,
        artPrice: String,
        artImage: String,
        uid: String,
        imageURL: String,
        isChecked :Boolean,
        id: String = "",
        date: String,
        cartId:String,
    ){
        this.id = id
        this.artAuthor = artAuthor
        this.artDescription = artDescription
        this.artName = artName
        this.artPrice = artPrice
        this.artImage = artImage
        this.uid = uid
        this.imageURL = imageURL
        this.isChecked = isChecked
        this.date = date
        this.cartId = cartId
    }
}