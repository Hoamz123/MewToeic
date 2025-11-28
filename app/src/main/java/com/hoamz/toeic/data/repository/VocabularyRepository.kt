package com.hoamz.toeic.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.hoamz.toeic.data.dao.VocabularyDao
import com.hoamz.toeic.data.local.DataChart
import com.hoamz.toeic.data.local.VocabularyEntity
import com.hoamz.toeic.data.local.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class VocabularyRepository @Inject constructor(
    private val vocabularyDao: VocabularyDao
) {
    //them tu moi vao list can hoc
    suspend fun insertNewWord(vocabularyEntity: VocabularyEntity){
        vocabularyEntity.let {
            vocabularyDao.insertNewVocabulary(vocabularyEntity)
        }
    }
    //xoa tu khoi danh sach hoc
    suspend fun deleteWord(vocabularyEntity: VocabularyEntity){
        vocabularyEntity.let {
            vocabularyDao.deleteVocabulary(vocabularyEntity)
        }
    }
    //lay ds tat ca cac tu da luu
    fun getAllNewVocab() : Flow<List<VocabularyEntity>>{
        return vocabularyDao.getAllNewVocabulary()
    }

    //xoa list cac tu da luu
    suspend fun deleteVocabularies(vocabs : List<VocabularyEntity>) {
        vocabularyDao.deleteListVocabulary(vocabs)
    }

    //lay tat ca cac tu da ton tai de so sanh (-> tranh luu nhieu lan)
    suspend fun getAllVocabsExisted(vocabs : List<String>) : List<VocabularyEntity>{
        return vocabularyDao.getAllVocabularyExisted(vocabs)
    }

    //lay ra du lieu de do nen chart
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataChart(): Flow<List<DataChart>> = vocabularyDao.getDataForChart()
        .map { dataCharts ->
            val today = LocalDate.now()
            val threeMonthsAgo = today.minusMonths(3)

            // lay ra ngay dau tien ma db nay co du lieu
            val earliestDate = dataCharts.minOfOrNull {
                LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            } ?: today

            // startDate = lay ra gia tri bat dau gan nhat so voi thoi diem bayh
            val startDate = if (earliestDate.isAfter(threeMonthsAgo)) earliestDate else threeMonthsAgo

            // tao list ngay lien tuc
            val allDates = generateSequence(startDate) { date ->
                val next = date.plusDays(1)
                if (next <= today) next else null
            }.toList()

            val mapDb = dataCharts.associateBy { it.date }//[date,{date,cnt}]

            allDates.map { date ->
                val dateStr = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                mapDb[dateStr] ?: DataChart(0, dateStr)
            }
        }

    //lay da ds cac tu da gap roi
    fun getReviewedVocabs() : Flow<List<VocabularyEntity>>{
        return vocabularyDao.getVocabularyReviewed()
    }

    //lay ra ds cac tu da master roi
    fun getMasteredVocabs() : Flow<List<VocabularyEntity>>{
        return vocabularyDao.getVocabularyMastered()
    }

    //set word -> isMastered
    suspend fun masteredVocab(id : Long){
        vocabularyDao.masteredVocabulary(id = id)
    }

    suspend fun unMasteredVocab(id : Long){
        vocabularyDao.unMasteredVocabulary(id = id)
    }

    //set word -> isReviewed
    suspend fun reviewedVocab(id : Long){
        vocabularyDao.reviewedVocabulary(id = id)
    }
}