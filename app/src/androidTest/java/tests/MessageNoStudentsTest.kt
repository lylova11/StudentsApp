package tests

import MockPersonsApi
import PreferenceRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.room.PersonDataBase
import screens.FavouritePersonsScreen


class MessageNoStudentsTest : TestCase() {

    private lateinit var db: PersonDataBase

    @Before
    fun createDb() {
        db = PersonDataBase.getDBClient(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun clearDB() {
        db.personsDao().clearTable()
    }

    @get:Rule(order = 1)
    val preferenceRule = PreferenceRule()

    @get:Rule(order = 2)
    val mock = WireMockRule(5000)

    @get:Rule(order = 3)
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    @DisplayName("Кейс 1. Проверка скрытия сообщения об отсутствии студентов")
    fun checkMessageNoStudentsDisappearedTest() = run {
        with(MockPersonsApi) {
            stubGetUsers()
        }

        with(FavouritePersonsScreen(this)) {
            clickAddButtonButton()
            clickAddPersonByNetworkButton()
            checkMessageNoPersonsIsNotDisplayed()
        }
    }
}
