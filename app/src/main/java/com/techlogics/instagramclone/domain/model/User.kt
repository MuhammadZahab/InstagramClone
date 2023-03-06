package com.techlogics.instagramclone.domain.model

data class User(
    var name:String = "",
    var userName:String = "",
    var userId:String ="",
    var email:String  ="",
    var password:String = "",
    var followings:List<String> = emptyList(),
    var followers:List<String> = emptyList(),
    var totalPosts:String = "",
    var bio:String = "",
    var url:String = ""
)
