package com.tbadhit.submissionbfaa2.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tbadhit.submissionbfaa2.api.ApiConfig
import com.tbadhit.submissionbfaa2.model.ResponseSearch
import com.tbadhit.submissionbfaa2.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ResponseUser>>()

    fun setSearchUser(query: String) {
        ApiConfig.service().getSearchUser(query).enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(
                call: Call<ResponseSearch>,
                response: Response<ResponseSearch>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.listUsers
                    listUsers.postValue(result)
                }
            }

            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }

        })
    }

    fun getSearchUser(): LiveData<ArrayList<ResponseUser>> {
        return listUsers
    }
}