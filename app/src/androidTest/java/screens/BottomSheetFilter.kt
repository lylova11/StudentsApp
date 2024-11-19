package screens


import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.check.KCheckBox
import ru.tinkoff.favouritepersons.R

class BottomSheetFilter(testContext: TestContext<*>) : BaseKaspressoScreen(testContext) {

    private val defaultFilter = KCheckBox { withId(R.id.bsd_rb_default) }
    private val byAge = KCheckBox { withId(R.id.bsd_rb_age) }

    fun verifySelectDefault(): BottomSheetFilter {
        step("Проверяем выбор фильтра по умолчанию") {
            byAge.isDisplayed()
            defaultFilter.isChecked()
        }
        return this
    }

    fun selectSortByAge(): BottomSheetFilter {
        step("Выбираем сортировку по возрасту") {
            byAge.click()
        }
        return this
    }
}