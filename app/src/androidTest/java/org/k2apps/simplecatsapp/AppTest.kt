package org.k2apps.simplecatsapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.remote.CatsApiService
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppTest {

    private val expectedRepos = listOf(
        Cat("1", "", 100, 100, "1"),
        Cat("2", "", 100, 100, "2")
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var service: CatsApiService

    @Before
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        runBlocking {
            Mockito.`when`((service.getCats(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))).thenReturn(expectedRepos)
        }
    }

    @Test
    fun goToSavedCats_fabNotVisible() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.show_saved))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.show_saved)).perform(ViewActions.click())

        onView(withId(R.id.show_saved))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))

        onView(withId(R.id.refresh))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))

        Espresso.pressBack()

        onView(withId(R.id.show_saved))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}