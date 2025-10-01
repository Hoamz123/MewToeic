package com.hoamz.toeic.data.repository

import com.hoamz.toeic.data.dao.ActivityRecentDao
import com.hoamz.toeic.data.local.ActivityRecent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityRecentRepository @Inject constructor(
    private val activityRecentDao: ActivityRecentDao
) {
    //insert
    suspend fun insertNewActRecent(activityRecent: ActivityRecent){
        withContext(Dispatchers.IO){
            //kiem tra ton tai
            val acts : List<ActivityRecent> = activityRecentDao.getAllActivitiesRecent().first()//lay gia tri dau tien cua flow
            if(acts.isNotEmpty()){
                val check = checkEquals(acts[0],activityRecent)
                if(check){
                    activityRecentDao.insertNewActivity(activityRecent)
                    if(acts.size > 5){
                        val actOldest = acts.last()
                        activityRecentDao.deleteActivityOldest(actOldest)
                    }
                }
            }
            else{
                //null
                activityRecentDao.insertNewActivity(activityRecent)
            }
        }
    }

    //get
    fun getAllActivitiesRecent() : Flow<List<ActivityRecent>>{
        return activityRecentDao.getAllActivitiesRecent()
    }

    private fun checkEquals(a1: ActivityRecent,a2: ActivityRecent) : Boolean{
        return a1.listAnswer != a2.listAnswer
    }

}