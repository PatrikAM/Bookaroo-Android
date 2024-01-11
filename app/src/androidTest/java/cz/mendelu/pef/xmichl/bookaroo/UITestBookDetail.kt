package cz.mendelu.pef.xmichl.bookaroo

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertValueEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreConstants
import cz.mendelu.pef.xmichl.bookaroo.datastore.dataStore
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooBooksServerMock
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags
import cz.mendelu.pef.xmichl.bookaroo.testTags.BottomBarTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.activities.MainActivity
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.NavGraphs
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.BookarooTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestBookDetail {
    private lateinit var navController: NavHostController
    private val context: Context = InstrumentationRegistry
        .getInstrumentation()
        .targetContext

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        runBlocking {
            context.dataStore.edit {
                it[stringPreferencesKey(DataStoreConstants.USER_TOKEN)] = "user1"
            }
        }
    }

    @Test
    fun test_detail() {
        val book = BokarooBooksServerMock.Book1
        launch()
        with(composeRule) {
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).assertExists()
            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookTitle).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookTitle).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookTitle).assertValueEquals(book.title!!)

            onNodeWithTag(BooksTestTags.TestTagBookISBN).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookISBN).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookISBN).assertValueEquals(book.isbn!!)

            onNodeWithTag(BooksTestTags.TestTagBookImage).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookImage).assertIsDisplayed()

            onNodeWithTag(BooksTestTags.TestTagBookAuthor).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookAuthor).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookAuthor).assertValueEquals(book.author!!)

        }

    }

    private fun launch() {
        composeRule.activity.setContent {
            BookarooTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }
    }

}