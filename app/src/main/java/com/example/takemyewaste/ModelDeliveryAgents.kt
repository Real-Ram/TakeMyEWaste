package com.example.takemyewaste

class ModelDeliveryAgents {
    //variable must match as in firebase
    var id:String = ""
    var name:String = ""
    var mobile:String = ""
    var timestamp:Long = 0
    var uid:String = ""

    //empty constructor, required by firebase
    constructor()

    //parameterized constructor
    constructor(id: String, name: String, mobile: String, timestamp: Long, uid: String) {
        this.id = id
        this.name = name
        this.mobile = mobile
        this.timestamp = timestamp
        this.uid = uid
    }
}