package com.hoamz.toeic.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoamz.toeic.data.local.DataChart
import com.hoamz.toeic.data.local.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewWord(word: Word)
    @Delete
    suspend fun deleteWord(word: Word)
    //lay tat ca tu
    @Query("select distinct * from Word order by date desc,id DESC")//dung them id de sort
    //minh muon sort tu moi nhat se o tren dua,ma date dang yyyy/MM/dd nen neu tu cung ngay thi se ko sort theo y muon dc
    fun getAllNewWords() : Flow<List<Word>>
    @Delete
    suspend fun deleteAllWords(words : List<Word>)

    @Query("select * from Word where word in (:words)")
    suspend fun getAllWordsExisted(words: List<String>) : List<Word>

    //thong ke 3 thang gan nhat
    @Query("""
        select date,count(*) as cnt from Word
        where date >= date('now','-3 months')
        group by date
    """)
    fun getDataForChart() : Flow<List<DataChart>>

    //lay ra cac tu da hoc roi (mastered)
    @Query("select * from Word where isMastered = :isMastered")
    fun getWordsMastered(isMastered : Boolean = true) : Flow<List<Word>>

    //lay ra cac tu da gap roi
    @Query("select * from Word where isReviewed = :isReviewed")
    fun getWordsReviewed(isReviewed : Boolean = true) : Flow<List<Word>>

    //set word -> isMastered
    @Query("update Word set isMastered =:isMastered where id =:id")
    suspend fun masteredWord(isMastered : Boolean = true,id : Long)

    //set word -> isMastered
    @Query("update Word set isMastered =:isMastered where id =:id")
    suspend fun unMasteredWord(isMastered : Boolean = false,id : Long)

    //set word -> isReviewed
    @Query("update Word set isReviewed =:isReviewed where id = :id")
    suspend fun reviewedWord(isReviewed: Boolean = true,id : Long)
}
