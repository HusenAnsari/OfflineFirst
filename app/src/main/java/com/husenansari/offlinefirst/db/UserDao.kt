package com.husenansari.offlinefirst.db

import androidx.lifecycle.LiveData
import androidx.room.*

import com.husenansari.offlinefirst.model.Item

@Dao
interface UserDao {

    @Query("SELECT * FROM userItem")
    fun getAllUsers(): LiveData<List<Item>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(allNews: ArrayList<Item>)

}