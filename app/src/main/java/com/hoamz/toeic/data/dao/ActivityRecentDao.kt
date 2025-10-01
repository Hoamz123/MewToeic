package com.hoamz.toeic.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoamz.toeic.data.local.ActivityRecent
import kotlinx.coroutines.flow.Flow


@Dao
//chi cho phep luu toi da 5 hoat dong gan day
interface ActivityRecentDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewActivity(activityRecent: ActivityRecent)

    //lay
    @Query("select * from activityrecent order by timeStamp desc limit 5")
    fun getAllActivitiesRecent() : Flow<List<ActivityRecent>>

    //xoa di (muc dich la khi them moi hoat dong neu da co 5 act roi -> xoa act lau nhat di)
    @Delete
    suspend fun deleteActivityOldest(activityRecent: ActivityRecent)
}