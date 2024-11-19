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
import java.time.LocalDate

//case 7

class PersonIsAddedTest : TestCase() {

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
    @DisplayName("Кейс 7. Проверка добавления студента")
    fun checkPersonCardInfoTest() = run {
        with(MockPersonsApi) {
            stubGetUsers()
        }

        with(FavouritePersonsScreen(this)) {
            clickAddButtonButton()
            clickAddNewManual()
        }
        val date = LocalDate.now().minusYears(50).toString()

        with(FullDataScreen(this)) {
            fillOutField(FullDataScreenFields.NAME, "Rene")
            fillOutField(FullDataScreenFields.SURNAME, "Ortiz")
            fillOutField(FullDataScreenFields.GENDER, "М")
            fillOutField(FullDataScreenFields.BIRTHDAY, date)
            fillOutField(FullDataScreenFields.EMAIL, "rene.ortiz@example.com")
            fillOutField(FullDataScreenFields.PHONE_NUM, "0121 213 0947")
            fillOutField(FullDataScreenFields.ADDRESS, "United Kingdom, Main Street 3924")
            fillOutField(
                FullDataScreenFields.LINK_PHOTO,
                "https://randomuser.me/api/portraits/men/81.jpg"
            )
            fillOutField(FullDataScreenFields.SUMMARY_POINTS, "1")
            tapSave()
        }
        with(FavouritePersonsScreen(this)) {
            checkPersonOnPositionByName(0, "Ortiz")
            checkPersonInfoOnPosition(
                0,
                name = "Rene",
                surname = "Ortiz",
                gender = "Male, 50",
                age = 50,
                email = "rene.ortiz@example.com",
                phone = "0121 213 0947",
                address = "United Kingdom, Main Street 3924",
                rate = "1"
            )
        }
    }


}