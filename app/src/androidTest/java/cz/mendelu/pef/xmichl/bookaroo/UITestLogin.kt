package cz.mendelu.pef.xmichl.bookaroo

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.xmichl.bookaroo.datastore.dataStore
import cz.mendelu.pef.xmichl.bookaroo.testTags.LoginRegisterTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.activities.MainActivity
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.NavGraphs
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.BookarooTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UITestLogin {

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
    }

    @After
    fun clear() {
        runBlocking {
            context.dataStore.edit {
                it.clear()
            }
        }
    }

    @Test
    fun test_login() {
        launch()
        with(composeRule) {
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginTextField).assertExists()
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordTextField).assertExists()
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).assertExists()

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginErrorText, useUnmergedTree = true).assertIsDisplayed()
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordErrorText, useUnmergedTree = true).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginTextField).performTextInput("exam@ex.com")
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordTextField).performTextInput("pass.123")
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).assertDoesNotExist()
        }
    }

    @Test
    fun test_register() {
        launch()
        with(composeRule) {

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagChangeLoginRegisterButton).assertExists()
            onNodeWithTag(LoginRegisterTestTags.TestTagChangeLoginRegisterButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).assertExists()
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginErrorText, useUnmergedTree = true).assertIsDisplayed()
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordErrorText, useUnmergedTree = true).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginTextField).assertExists()
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordTextField).assertExists()
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).assertExists()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginTextField).performTextInput("exam@ex.com")
            onNodeWithTag(LoginRegisterTestTags.TestTagPasswordTextField).performTextInput("pass.123")
            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LoginRegisterTestTags.TestTagLoginRegisterButton).assertDoesNotExist()
        }
    }

    private fun launch() {
        composeRule.activity.setContent {
            BookarooTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}