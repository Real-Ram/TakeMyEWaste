package com.example.takemyewaste

class ModelPickUp {
    var id:String = ""
    var name:String = ""
    var mobile:String = ""
    var product:String = ""
    var address:String = ""
    var time:String = ""
    var timestamp:Long = 0
    var uid:String = ""

    //empty constructor
    constructor()

    //parameterized constructor
    constructor(
        id: String,
        name: String,
        mobile: String,
        product: String,
        address: String,
        time: String,
        timestamp: Long,
        uid: String
    ) {
        this.id = id
        this.name = name
        this.mobile = mobile
        this.product = product
        this.address = address
        this.time = time
        this.timestamp = timestamp
        this.uid = uid
    }
}

