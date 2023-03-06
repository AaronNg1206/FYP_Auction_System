package com.nghycp.fyp_auction_system.customer

class ModelArtwork {

    var id:String = ""
    var artAuthor:String = ""
    var artDescription:String = ""
    var artName:String = ""
    var artPrice:String = ""
    var artImage:String= ""
    var uid:String = ""
    var imageURL: String = ""

    constructor()

    constructor(
        id: String,
        artAuthor: String,
        artDescription: String,
        artName: String,
        artPrice: String,
        artImage: String,
        uid: String,
        imageURL: String
    ){
        this.id = id
        this.artAuthor = artAuthor
        this.artDescription = artDescription
        this.artName = artName
        this.artPrice = artPrice
        this.artImage = artImage
        this.uid = uid
        this.imageURL = imageURL
    }
}