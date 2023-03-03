package com.nghycp.fyp_auction_system.customer

class ModelArtwork {

    var id:String = ""
    var author:String = ""
    var description:String = ""
    var artworkName:String = ""
    var price:String = ""
    var artworkImage:String= ""
    var uid:String = ""
    var imageURL: String = ""

    constructor()

    constructor(
        id: String,
        author: String,
        description: String,
        artworkName: String,
        price: String,
        artworkImage: String,
        uid: String,
        imageURL: String
    ){
        this.id = id
        this.author = author
        this.description = description
        this.artworkName = artworkName
        this.price = price
        this.artworkImage = artworkImage
        this.uid = uid
        this.imageURL = imageURL
    }
}