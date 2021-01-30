package org.k2apps.simplecatsapp.ui.details


import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.k2apps.simplecatsapp.MainActivity
import org.k2apps.simplecatsapp.R
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.db.CatsRepository
import org.k2apps.simplecatsapp.data.repository.remote.RemoteCatsRepository
import org.k2apps.simplecatsapp.di.RepositoryModule
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)
class CatDetailsTest {

    @Inject
    lateinit var localCatsRepository: CatsRepository

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

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun saveCat_changeInfoText() {

        runBlocking {
            localCatsRepository.deleteAll()
        }

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.photos_grid)).perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        onView(withId(R.id.is_like)).check(matches(withText("Not saved")))

        onView(withId(R.id.like)).perform(click())

        onView(withId(R.id.is_like)).check(matches(withText("Cat saved in favourites")))

        onView(withId(R.id.like)).perform(click())

        onView(withId(R.id.is_like)).check(matches(withText("Not saved")))
    }


}
