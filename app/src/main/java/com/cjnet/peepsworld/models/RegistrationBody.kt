package com.cjnet.peepsworld.models


/*
{"userFname":"Jijo",
    "userLname":"Jose",
    "userEmail":"info.jijo@gmail.com",
    "userMobile":"9845577464",
    "userGender":"M"
}*/


data class RegistrationBody(
    val userFname: String,
    val userLname: String,
    val userEmail: String,
    val userMobile: String,
    val userGender: String
)