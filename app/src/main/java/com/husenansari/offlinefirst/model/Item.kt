package com.husenansari.offlinefirst.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "userItem"
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val account_id: Int?,
    val accept_rate: Int?,
    //val badge_counts: BadgeCounts?,
    val creation_date: Int?,
    val display_name: String?,
    val is_employee: Boolean?,
    val last_access_date: Int?,
    val last_modified_date: Int?,
    val link: String?,
    val location: String?,
    val profile_image: String?,
    val reputation: Int?,
    val reputation_change_day: Int?,
    val reputation_change_month: Int?,
    val reputation_change_quarter: Int?,
    val reputation_change_week: Int?,
    val reputation_change_year: Int?,
    val user_id: Int?,
    val user_type: String?,
    val website_url: String?
) :Serializable