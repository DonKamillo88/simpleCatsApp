package org.k2apps.simplecatsapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.remote.RemoteCatsRepository
import org.k2apps.simplecatsapp.di.RepositoryModule

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)
class MainTest {

    private val expectedRepos = listOf(
        Cat("1", "", 100, 100, "1"),
        Cat("2", "", 100, 100, "2")
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @BindValue
    @JvmField
    val provideFakeRemoteCatsRepository: RemoteCatsRepository = object : RemoteCatsRepository {
        override suspend fun getCats(page: Int, limit: Int, order: String): List<Cat> {
            return expectedRepos
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