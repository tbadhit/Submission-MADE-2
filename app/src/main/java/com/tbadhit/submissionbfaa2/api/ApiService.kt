package com.tbadhit.submissionbfaa2.api

import com.tbadhit.submissionbfaa2.BuildConfig
import com.tbadhit.submissionbfaa2.model.ResponseSearch
import com.tbadhit.submissionbfaa2.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token $apiKey")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<ResponseSearch>


    @GET("users/{username}")
    @Headers("Authorization: token $apiKey")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $apiKey")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<ResponseUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $apiKey")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ResponseUser>>

    companion object {
        const val apiKey = BuildConfig.KEY
    }
}