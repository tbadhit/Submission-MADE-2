package com.tbadhit.submissionbfaa2.view_model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tbadhit.submissionbfaa2.api.ApiConfig
import com.tbadhit.submissionbfaa2.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel(private val application: Application) : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<ResponseUser>>()

    fun setListFollower(username: String) {
        ApiConfig.service().getUserFollower(username)
            .enqueue(object : Callback<ArrayList<ResponseUser>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponseUser>>,
                    response: Response<ArrayList<ResponseUser>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            listFollower.postValue(response.body())
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseUser>>, t: Throwable) {
                    t.message?.let { Log.d("onFailure", it) }
                    Toast.makeText(
                        application.applicationContext,
                        t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    fun getListFollower(): LiveData<ArrayList<ResponseUser>> {
        return listFollower
    }
}