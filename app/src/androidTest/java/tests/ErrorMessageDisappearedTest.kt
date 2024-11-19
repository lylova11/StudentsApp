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


class ErrorMessageDisappearedTest : TestCase() {

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
    @DisplayName("Кейс 9. Проверка скрытия сообщения об ошибке при вводе данных в поле")
    fun checkErrorMessageDisappearedTest() = run {
        with(MockPersonsApi) {
            stubGetUsers()
        }

        with(FavouritePersonsScreen(this)) {
            clickAddButtonButton()
            clickAddNewManual()
        }
        val date = LocalDate.now().minusYears(122).toString()

        with(FullDataScreen(this)) {
            fillOutField(FullDataScreenFields.NAME, "Абдул")
            fillOutField(FullDataScreenFields.SURNAME, "Алуарджанов")
            fillOutField(FullDataScreenFields.GENDER, "1")
            fillOutField(FullDataScreenFields.BIRTHDAY, date)
            fillOutField(FullDataScreenFields.EMAIL, "abd@email.com")
            fillOutField(FullDataScreenFields.PHONE_NUM, "+79999999999")
            fillOutField(FullDataScreenFields.ADDRESS, "Хельхейм, Гьёлльский заулок 22")
            fillOutField(
                FullDataScreenFields.LINK_PHOTO,
                "https://www.topnews.ru/upload/topka/2019/05/87698fcf/87698fcf_1.jpg"
            )
            fillOutField(FullDataScreenFields.SUMMARY_POINTS, "1")
            tapSave()
            clearField(FullDataScreenFields.GENDER)
            checkNoError(FullDataScreenFields.GENDER)
        }
    }
}