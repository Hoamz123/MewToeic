package com.hoamz.toeic.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoamz.toeic.data.local.DataChart
import com.hoamz.toeic.data.local.VocabularyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewVocabulary(vocabularyEntity: VocabularyEntity)
    @Delete
    suspend fun deleteVocabulary(vocabularyEntity: VocabularyEntity)
    //lay tat ca tu
    @Query("select distinct * from vocabularyentity order by date desc,id DESC")//dung them id de sort
    //minh muon sort tu moi nhat se o tren dua,ma date dang yyyy/MM/dd nen neu tu cung ngay thi se ko sort theo y muon dc
    fun getAllNewVocabulary() : Flow<List<VocabularyEntity>>
    @Delete
    suspend fun deleteListVocabulary(words : List<VocabularyEntity>)

    @Query("select * from vocabularyentity where word in (:words)")
    suspend fun getAllVocabularyExisted(words: List<String>) : List<VocabularyEntity>

    //thong ke 3 thang gan nhat
    @Query("""
        select date,count(*) as cnt from vocabularyentity
        where date >= date('now','-3 months')
        group by date
    """)
    fun getDataForChart() : Flow<List<DataChart>>

    //lay ra cac tu da hoc roi (mastered)
    @Query("select * from vocabularyentity where isMastered = :isMastered")
    fun getVocabularyMastered(isMastered : Boolean = true) : Flow<List<VocabularyEntity>>

    //lay ra cac tu da gap roi
    @Query("select * from vocabularyentity where isReviewed = :isReviewed")
    fun getVocabularyReviewed(isReviewed : Boolean = true) : Flow<List<VocabularyEntity>>

    //set word -> isMastered
    @Query("update vocabularyentity set isMastered =:isMastered where id =:id")
    suspend fun masteredVocabulary(isMastered : Boolean = true,id : Long)

    //set word -> isMastered
    @Query("update vocabularyentity set isMastered =:isMastered where id =:id")
    suspend fun unMasteredVocabulary(isMastered : Boolean = false,id : Long)

    //set word -> isReviewed
    @Query("update vocabularyentity set isReviewed =:isReviewed where id = :id")
    suspend fun reviewedVocabulary(isReviewed: Boolean = true,id : Long)//goi khi user click vao xem word detail
}