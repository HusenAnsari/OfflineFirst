package com.husenansari.offlinefirst.repository

import com.husenansari.offlinefirst.api.RetrofitInstance
import com.husenansari.offlinefirst.db.UserDatabase
import com.husenansari.offlinefirst.model.Item


class GetUserRepository(val db: UserDatabase) {

    suspend fun getUserApi(page : Int, pagesize: Int, order: String, sort: String, site : String) =
        RetrofitInstance.api.getUserList(page, pagesize, order, sort, site)


    suspend fun insertResponse(userItemList: ArrayList<Item>) = db.getUserDao().insertAllUsers(userItemList)

    fun getAllResponse() = db.getUserDao().getAllUsers()

}