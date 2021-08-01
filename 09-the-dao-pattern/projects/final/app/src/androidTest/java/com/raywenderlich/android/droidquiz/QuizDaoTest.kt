package com.raywenderlich.android.droidquiz

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.raywenderlich.android.droidquiz.data.QuestionInfoProvider
import com.raywenderlich.android.droidquiz.data.db.QuizDao
import com.raywenderlich.android.droidquiz.data.db.QuizDatabase
import com.raywenderlich.android.droidquiz.data.model.Question
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizDaoTest {
  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  private lateinit var database: QuizDatabase
  private lateinit var quizDao: QuizDao

  @Before
  fun setUp() {
    val context: Context = InstrumentationRegistry.getInstrumentation().context // 1
    try {
      database = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java) //2
          .allowMainThreadQueries() //3
          .build()
    } catch (e: Exception) {
      Log.i(this.javaClass.simpleName, e.localizedMessage ?: "") //4
    }
    quizDao = database.quizDao() //5
  }

  @Test
  fun testInsertQuestion() {
    // 1
    val previousNumberOfQuestions = quizDao.getAllQuestions().size
    //2
    val question = Question(1, "What is your name?")
    quizDao.insert(question)
    //3
    val newNumberOfQuestions = quizDao.getAllQuestions().size
    //4
    val changeInQuestions = newNumberOfQuestions - previousNumberOfQuestions
    // 5
    Assert.assertEquals(1, changeInQuestions)
  }

  @Test
  fun testClearQuestions() {
    for (question in QuestionInfoProvider.questionList) {
      quizDao.insert(question)
    }
    Assert.assertTrue(quizDao.getAllQuestions().isNotEmpty())
    Log.d("testData", quizDao.getAllQuestions().toString())
    quizDao.clearQuestions()
    Assert.assertTrue(quizDao.getAllQuestions().isEmpty())
  }

  @After
  fun tearDown() {
    database.close()
  }
}