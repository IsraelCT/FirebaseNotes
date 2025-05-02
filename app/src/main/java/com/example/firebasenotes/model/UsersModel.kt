package com.example.firebasenotes.model

data class UsersModel(
    val userId : String,
    val email : String,
    val username : String
){
    fun  toMap (): MutableMap<String, Any>{
        return mutableMapOf(
            "UserId" to this.userId,
            "email" to this.email,
            "username" to this.username
        )
    }
}
