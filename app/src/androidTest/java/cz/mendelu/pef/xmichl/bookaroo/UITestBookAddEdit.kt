package cz.mendelu.pef.xmichl.bookaroo

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreConstants
import cz.mendelu.pef.xmichl.bookaroo.datastore.dataStore
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooBooksServerMock
import cz.mendelu.pef.xmichl.bookaroo.mock.BookarooLibrariesServerMock
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags
import cz.mendelu.pef.xmichl.bookaroo.testTags.BottomBarTestTags
import cz.mendelu.pef.xmichl.bookaroo.testTags.LibrariesTestTags
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
class UITestBookAddEdit {
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
    fun test_add_book_from_all_books() {
        val book = BokarooBooksServerMock.Book1
        launch()
        with(composeRule) {
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookAddButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookAddButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCreateManually).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookCreateUsingISBN).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCreateManually).performClick()

            waitForIdle()
//            Thread.sleep(1000)
            mainClock.autoAdvance = false

            mainClock.advanceTimeBy(400L)
//            mainClock.autoAdvance = true

            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertTextEquals("")

            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertTextEquals("")

            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertTextEquals("")

            onNodeWithTag(
                BooksTestTags.TestTagBookLibraryTextField,
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithTag(
                BooksTestTags.TestTagBookLibraryTextField,
                useUnmergedTree = true
            ).assertTextEquals("")

            waitForIdle()
            mainClock.autoAdvance = false
            Thread.sleep(1000)
            mainClock.autoAdvance = true

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsEnabled()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookFillFormDialog).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookFillFormDialog).assertIsDisplayed()

            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).assertIsEnabled()
            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(
                BooksTestTags.TestTagBookISBNTextField,
                useUnmergedTree = true
            ).performTextInput(book.isbn!!)
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookISBNTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.isbn!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).performTextInput(book.author!!)
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.author!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).performTextInput(book.title!!)
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.title!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookLibSelector,
                useUnmergedTree = true
            ).performClick()
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryTextField, useUnmergedTree = true)
                .assertTextEquals(BookarooLibrariesServerMock.Name.lib1.name)

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).performClick()
//            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsNotEnabled()

            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertDoesNotExist()

            onNodeWithTag(BooksTestTags.TestTagBooksList).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBooksList).assertIsDisplayed()
        }

    }

    @Test
    fun test_add_book_from_lib1() {
        val book = BokarooBooksServerMock.Book1
        launch()
        with(composeRule) {
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(LibrariesTestTags.LibTestTagCard + book.library).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookAddButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookAddButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCreateManually).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookCreateUsingISBN).assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCreateManually).performClick()

            waitForIdle()
//            Thread.sleep(1000)
            mainClock.autoAdvance = false

            mainClock.advanceTimeBy(400L)
//            mainClock.autoAdvance = true

            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertTextEquals("")

            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertTextEquals("")

            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertTextEquals("")

            onNodeWithTag(
                BooksTestTags.TestTagBookLibraryTextField,
                useUnmergedTree = true
            ).assertIsDisplayed()
            onNodeWithTag(
                BooksTestTags.TestTagBookLibraryTextField,
                useUnmergedTree = true
            ).assertTextEquals(BookarooLibrariesServerMock.Name.lib1.name)

            waitForIdle()
            mainClock.autoAdvance = false
            Thread.sleep(1000)
            mainClock.autoAdvance = true

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsEnabled()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookFillFormDialog).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookFillFormDialog).assertIsDisplayed()

            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).assertIsEnabled()
            onNodeWithTag(BooksTestTags.TestTagBookDismissDialogButton).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(
                BooksTestTags.TestTagBookISBNTextField,
                useUnmergedTree = true
            ).performTextInput(book.isbn!!)
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookISBNTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.isbn!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).performTextInput(book.author!!)
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.author!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).performTextInput(book.title!!)
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.title!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookLibSelector,
                useUnmergedTree = true
            ).performClick()
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryTextField, useUnmergedTree = true)
                .assertTextEquals(BookarooLibrariesServerMock.Name.lib1.name)

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).performClick()
//            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsNotEnabled()

            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertDoesNotExist()

            onNodeWithTag(BooksTestTags.TestTagBooksList).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBooksList).assertIsDisplayed()
        }

    }

    @Test
    fun test_edit_book_from_all_books() {
        val book = BokarooBooksServerMock.Book1
        launch()
        with(composeRule) {
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BottomBarTestTags.TestTagBottomNavBooks).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookCard + book.id).performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookEditButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookEditButton).performClick()

            waitForIdle()
            Thread.sleep(1000)


            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertTextEquals(book.isbn!!)

            onNodeWithTag(BooksTestTags.TestTagBookAuthorTextField, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookAuthorTextField, useUnmergedTree = true)
                .assertTextEquals(book.author!!)

            onNodeWithTag(BooksTestTags.TestTagBookTitleTextField, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookTitleTextField, useUnmergedTree = true)
                .assertTextEquals(book.title!!)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryTextField, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookLibraryTextField, useUnmergedTree = true)
                .assertTextEquals(BookarooLibrariesServerMock.Name.lib1.name)

            waitForIdle()
            mainClock.autoAdvance = false
            Thread.sleep(1000)
            mainClock.autoAdvance = true

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsEnabled()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .performTextInput("1")
            waitForIdle()
            onNodeWithTag(BooksTestTags.TestTagBookISBNTextField, useUnmergedTree = true)
                .assertTextEquals(book.isbn!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).performTextInput("1")
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookAuthorTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.author!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).performTextInput("1")
            waitForIdle()
            onNodeWithTag(
                BooksTestTags.TestTagBookTitleTextField,
                useUnmergedTree = true
            ).assertTextEquals(book.title!!)

            onNodeWithTag(
                BooksTestTags.TestTagBookLibSelector,
                useUnmergedTree = true
            ).performClick()
            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .assertExists()
            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .assertIsDisplayed()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryOption + book.library)
                .performClick()

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookLibraryTextField, useUnmergedTree = true)
                .assertTextEquals(BookarooLibrariesServerMock.Name.lib1.name)

            waitForIdle()
            Thread.sleep(1000)

            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).performClick()
//            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertIsNotEnabled()

            waitForIdle()
            Thread.sleep(1000)
            onNodeWithTag(BooksTestTags.TestTagBookSaveButton).assertDoesNotExist()

            onNodeWithTag(BooksTestTags.TestTagBooksList).assertDoesNotExist()
            onNodeWithTag(BooksTestTags.TestTagBookTitle).assertIsDisplayed()
            onNodeWithTag(BooksTestTags.TestTagBookTitle).assertTextEquals(book.title!!)
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