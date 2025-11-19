package com.hoamz.toeic.data.repository

import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import com.hoamz.toeic.data.dao.WordDao
import com.hoamz.toeic.data.local.DataChart
import com.hoamz.toeic.data.local.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val wordDao: WordDao
) {
    //them tu moi vao list can hoc
    suspend fun insertNewWord(word: Word){
        word.let {
            wordDao.insertNewWord(word)
        }
    }
    //xoa tu khoi danh sach hoc
    suspend fun deleteWord(word : Word){
        word.let {
            wordDao.deleteWord(word)
        }
    }
    //lay ds tat ca cac tu da luu
    fun getAllNewWords() : Flow<List<Word>>{
        return wordDao.getAllNewWords()
    }

    //xoa tat ca cac tu da luu
    suspend fun deleteAllWords(list : List<Word>) {
        wordDao.deleteAllWords(list)
    }

    //lay tat ca cac tu da ton tai de so sanh (-> tranh luu nhieu lan)
    suspend fun getAllWordsExisted(words : List<String>) : List<Word>{
        return wordDao.getAllWordsExisted(words)
    }

    //lay ra du lieu de do nen chart
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataChart(): Flow<List<DataChart>> = wordDao.getDataForChart()
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
    fun getReviewedWords() : Flow<List<Word>>{
        return wordDao.getWordsReviewed()
    }

    //lay ra ds cac tu da master roi
    fun getMasteredWords() : Flow<List<Word>>{
        return wordDao.getWordsMastered()
    }

    //set word -> isMastered
    suspend fun masteredWord(id : Long){
        wordDao.masteredWord(id = id)
    }

    suspend fun unMasteredWord(id : Long){
        wordDao.unMasteredWord(id = id)
    }

    //set word -> isReviewed
    suspend fun reviewedWord(id : Long){
        wordDao.reviewedWord(id = id)
    }
}