package cz.mendelu.pef.xmichl.bookaroo

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooBooksServerMock
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags
import cz.mendelu.pef.xmichl.bookaroo.testTags.BottomBarTestTags
import cz.mendelu.pef.xmichl.bookaroo.testTags.LoginRegisterTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.activities.MainActivity
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.NavGraphs
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.BookarooTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestListOfBooks {
    private lateinit var navController: NavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_list_of_books_exists() {
        val book = BokarooBooksServerMock.Book1
        launch()
        getToList()
        with(composeRule) {
            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).assertExists()
            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).performClick()
            onNodeWithTag(BooksTestTags.TestTagBooksList).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBooksList).assertIsDisplayed()
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).assertIsDisplayed()

        }
    }

    private fun getToList() {
        with(composeRule) {
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginTextField).performTextInput("exam@ex.com")
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordTextField).performTextInput("pass.123")
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).performClick()
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
