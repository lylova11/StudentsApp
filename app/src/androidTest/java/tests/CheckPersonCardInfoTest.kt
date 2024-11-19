package tests

import FullDataScreenFields
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
import screens.FullDataScreen
import testData.TestData

// Case 5

class CheckPersonCardInfoTest : TestCase() {

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
    @DisplayName("Кейс 5. Проверка открытия второго экрана с данными пользователя")
    fun checkPersonCardInfoTest() = run {
        with(MockPersonsApi) {
            stubGetUsers()
        }

        with(FavouritePersonsScreen(this)) {
            clickAddButtonButton()
            repeat(3) {
                clickAddPersonByNetworkButton()
            }
            clickOnPosition(0)
        }

        with(FullDataScreen(this)) {
            checkFieldContext(
                FullDataScreenFields.getByString(FullDataScreenFields.NAME.header),
                TestData.expectedPerson.firstName!!
            )
            checkFieldContext(
                FullDataScreenFields.getByString(FullDataScreenFields.SURNAME.header),
                TestData.expectedPerson.lastName!!
            )
            checkFieldContext(
                FullDataScreenFields.getByString(FullDataScreenFields.GENDER.header),
                TestData.expectedPerson.gender!!
            )
            checkFieldContext(
                FullDataScreenFields.getByString(FullDataScreenFields.BIRTHDAY.header),
                TestData.expectedPerson.birthDate!!
            )
        }
    }
}