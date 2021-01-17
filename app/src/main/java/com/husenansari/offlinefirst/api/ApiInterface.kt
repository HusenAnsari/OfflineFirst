package com.husenansari.offlinefirst.api


import com.husenansari.offlinefirst.model.MainResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    @GET("2.2/users")
    suspend fun getUserList(
        @Query("page")
        page: Int,
        @Query("pagesize")
        pagesize: Int,
        @Query("order")
        order: String,
        @Query("sort")
        sort: String,
        @Query("site")
        site: String,
    ): Response<MainResponse>


}