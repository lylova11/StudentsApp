package tests

import PreferenceRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.room.PersonDataBase
import screens.FavouritePersonsScreen

// Case 10
class CheckInternetErrorMessageTest : TestCase() {

    private lateinit var db: PersonDataBase

    @Before
    fun createDb() {
        db = PersonDataBase.getDBClient(InstrumentationRegistry.getInstrumentation().targetContext)
        device.network.toggleWiFi(false)
        device.network.toggleMobileData(false)
    }


    @After
    fun clearDB() {
        db.personsDao().clearTable()
    }

    @get:Rule()
    val preferenceRule = PreferenceRule()


    @get:Rule()
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    @DisplayName("Кейс 10. Проверка отображения сообщения об ошибке интернет-соединения")
    fun checkNoInternetErrorMessageTest() = run {
        with(FavouritePersonsScreen(this)) {
            clickAddButtonButton()
            clickAddPersonByNetworkButton()
            checkNoInternetConnectionErrorMessage()
        }
    }
}