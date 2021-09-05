package com.tbadhit.submissionbfaa2.model
import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @field:SerializedName("items")
    val listUsers: ArrayList<ResponseUser>
)
