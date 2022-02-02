package com.example.takemyewaste

class ModelDeliveryBoy {
    var id:String = ""
    var name:String = ""
    var mobile:String = ""
    var product:String = ""
    var address:String = ""
    var time:String = ""
    var timestamp:Long = 0
    var uid:String = ""
    var d_uid:String = ""
    var d_name:String = ""
    var d_mobile:String = ""
    var d_date:String = ""

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
        uid: String,
        d_uid: String,
        d_name: String,
        d_mobile: String,
        d_date: String
    ) {
        this.id = id
        this.name = name
        this.mobile = mobile
        this.product = product
        this.address = address
        this.time = time
        this.timestamp = timestamp
        this.uid = uid
        this.d_uid = d_uid
        this.d_name = d_name
        this.d_mobile = d_mobile
        this.d_date = d_date
    }
}


