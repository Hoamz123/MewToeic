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
            /*
            cho nay do sort theo desc nen [0] se la moi nhat
             */
            val acts : List<ActivityRecent> = activityRecentDao.getAllActivitiesRecent().first()//lay gia tri dau tien cua flow
            if(acts.isNotEmpty()){

                val check = acts.any { checkEquals(it,activityRecent) }//true khi va chi khi 1 cap dung
                /*
                cho nay phai kiem tra la boi vi khi nop bai thi goi den ham nay
                khi xem lai cung goi den ham nay
                ma case xem lai thi cau tra loi se giong nhau nen khong luu
                 */
                if(!check){
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
        return makeHashCode(a1) == makeHashCode(a2)
    }

    private fun makeHashCode(activityRecent: ActivityRecent) : String{
        return "{${activityRecent.numberAnswerCorrect}_{${activityRecent.listAnswer.hashCode()}}"
    }

}