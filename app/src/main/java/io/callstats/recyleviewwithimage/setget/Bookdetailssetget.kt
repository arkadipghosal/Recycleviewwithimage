package io.callstats.recyleviewwithimage.setget

class Bookdetailssetget {

    lateinit var _book_title: String
    var _image: String
    var _author: String


    constructor(

        book_title: String,
        image: String,
        author: String

    ){

        this._book_title=book_title
        this._image=image
        this._author=author


    }

    constructor(

        image: String,
        author: String,

    ){


        this._image=image
        this._author=author


    }
}