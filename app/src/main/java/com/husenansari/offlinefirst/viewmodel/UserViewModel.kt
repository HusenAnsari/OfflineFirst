package com.husenansari.offlinefirst.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.husenansari.offlinefirst.model.Item
import com.husenansari.offlinefirst.model.MainResponse
import com.husenansari.offlinefirst.repository.GetUserRepository
import com.husenansari.offlinefirst.util.HelperFunction
import com.husenansari.offlinefirst.util.MyApplication
import com.husenansari.offlinefirst.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class UserViewModel(
        application: Application,
        private val repository: GetUserRepository
) : AndroidViewModel(application) {

    val allUserMutableLiveData: MutableLiveData<Resource<MainResponse>> = MutableLiveData()
    var mainResponse: MainResponse? = null

    fun getAllUser(page : Int, pagesize: Int, order: String, sort: String, site : String) = viewModelScope.launch {
        callGetAllUser(page, pagesize, order, sort, site)
    }

    private suspend fun callGetAllUser(
        page: Int,
        pagesize: Int,
        order: String,
        sort: String,
        site: String
    ) {
        allUserMutableLiveData.postValue(Resource.Loading())
        try {
            if (HelperFunction.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getUserApi(
                    page, pagesize, order, sort, site
                )
                allUserMutableLiveData.postValue(handleBreakingNewsResponse(response))
           } else {
                allUserMutableLiveData.postValue(Resource.Error("No internet connection"))

            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> allUserMutableLiveData.postValue(Resource.Error("Network Failure"))
                else -> allUserMutableLiveData.postValue(Resource.Error("Conversion Error : ${t.localizedMessage}"))
            }
        }
    }

    fun saveAllUser(itemList: ArrayList<Item>) = viewModelScope.launch {
        repository.insertResponse(itemList)
    }

    fun getAllResponse() = repository.getAllResponse()

    private fun handleBreakingNewsResponse(response: Response<MainResponse>) : Resource<MainResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(mainResponse == null) {
                    mainResponse = resultResponse
                } else {
                    val newArticles = resultResponse.items
                    mainResponse?.items?.addAll(newArticles)
                }
                return Resource.Success(mainResponse!!)
            }
        }
        return Resource.Error(response.message())
    }
}