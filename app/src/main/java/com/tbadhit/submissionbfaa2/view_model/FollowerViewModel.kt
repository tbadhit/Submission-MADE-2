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

class FollowerViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<ResponseUser>>()

    fun setListFollower(username: String) {
        ApiConfig.service().getUserFollower(username)
            .enqueue(object : Callback<ArrayList<ResponseUser>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponseUser>>,
                    response: Response<ArrayList<ResponseUser>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        listFollower.postValue(responseBody)
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseUser>>, t: Throwable) {
                    t.message?.let { Log.d("onFailure", it) }
                }

            })
    }

    fun getListFollower(): LiveData<ArrayList<ResponseUser>> {
        return listFollower
    }
}