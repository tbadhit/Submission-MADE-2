package com.tbadhit.submissionbfaa2.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tbadhit.submissionbfaa2.api.ApiConfig
import com.tbadhit.submissionbfaa2.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val listUsers = MutableLiveData<ResponseUser>()

    fun setDetailUser(username: String) {
        ApiConfig.service().getDetailUser(username).enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    listUsers.postValue(responseBody)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getDetailUser(): LiveData<ResponseUser> {
        return listUsers
    }
}