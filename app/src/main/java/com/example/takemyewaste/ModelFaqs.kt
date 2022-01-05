package com.example.takemyewaste

class ModelFaqs {
    //variable must match as in firebase
    var id:String = ""
    var question:String = ""
    var answer:String = ""
    var timestamp:Long = 0
    var uid:String = ""

    //empty constructor, required by firebase
    constructor()

    //parameterized constructor
    constructor(id: String, question: String, answer: String, timestamp: Long, uid: String) {
        this.id = id
        this.question = question
        this.answer = answer
        this.timestamp = timestamp
        this.uid = uid
    }
}