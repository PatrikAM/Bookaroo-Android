package cz.mendelu.pef.xmichl.bookaroo

import androidx.activity.compose.setContent
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
import cz.mendelu.pef.xmichl.bookaroo.testTags.LoginRegisterTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.activities.MainActivity
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.NavGraphs
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.BookarooTheme
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UITestsListOfLibraries {
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
    fun test_list() {
        val books = BokarooBooksServerMock.all
        launch()
        with(composeRule) {
            waitForIdle()
            Thread.sleep(1000)

            books.forEach { book ->
                onNodeWithTag(BooksTestTags.TestTagBookCard + book.id)
                    .assertExists()
                onNodeWithTag(BooksTestTags.TestTagBookCard + book.id)
                    .assertIsDisplayed()
            }

            waitForIdle()
            Thread.sleep(1000)
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