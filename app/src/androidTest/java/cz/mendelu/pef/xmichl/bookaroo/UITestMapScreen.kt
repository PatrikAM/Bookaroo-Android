package cz.mendelu.pef.xmichl.bookaroo

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreConstants
import cz.mendelu.pef.xmichl.bookaroo.datastore.dataStore
import cz.mendelu.pef.xmichl.bookaroo.testTags.BottomBarTestTags
import cz.mendelu.pef.xmichl.bookaroo.testTags.PlacesTestTags
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
class UITestMapScreen {
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

    @get:Rule(order = 2)
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

    @Test
    fun test_map_is_displayed() {
        launch()

        with(composeRule) {
            onNodeWithTag(BottomBarTestTags.TestTagBottomNavMap).performClick()
            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(PlacesTestTags.TestTagMapMap).assertExists()
            onNodeWithTag(PlacesTestTags.TestTagMapMap).assertIsDisplayed()
        }
    }

    // How to test that map?
//    @Test
//    fun test_map_shows_detail() {
//        launch()
//
//        with(composeRule) {
//            onNodeWithTag(BottomBarTestTags.TestTagBottomNavMap).performClick()
//            waitForIdle()
//            Thread.sleep(1000)
//        }
//    }

    private fun launch() {
        composeRule.activity.setContent {
            BookarooTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }
    }
}