package screens

import FullDataScreenFields
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import ru.tinkoff.favouritepersons.R


//case 8
class FullDataScreen(testContext: TestContext<*>) : BaseKaspressoScreen(testContext) {

    private val submitBtn = KButton { withId(R.id.submit_button) }

    fun checkFieldContext(field: FullDataScreenFields, text: String) {
        step("Проверяем, что в поле '${field.header}' содержится текст '$text'") {
            val editText = KEditText { withId(field.id) }
            editText.isDisplayed()
            editText.hasText(text)
        }
    }

    fun fillOutField(field: FullDataScreenFields, text: String): FullDataScreen {
        step("Зааполняем поле - ${field.header} текстом - ${text}") {
            val editTextField = KEditText { withId(field.id) }
            editTextField.replaceText(text)
        }
        return this
    }

    fun clearField(field: FullDataScreenFields): FullDataScreen {
        step("Очищаем поле - ${field.header} от текста") {
            val editTextField = KEditText { withId(field.id) }
            editTextField.clearText()
        }
        return this
    }

    fun tapSave(): FavouritePersonsScreen {
        step("Нажимаем сохранить данные") {
            submitBtn.click()
        }
        return FavouritePersonsScreen(testContext)
    }

    fun checkErrorUnderField(field: FullDataScreenFields): FullDataScreen {
        step("Проверяем ошибку под полем ${field.header}") {
            val block = KView { withId(field.blockId) }
            block.hasDescendant { withText("Поле должно быть заполнено буквами М или Ж") }
        }
        return this
    }

    fun checkNoError(field: FullDataScreenFields): FullDataScreen {
        step("Проверяем ошибку под полем ${field.header}") {
            val block = KView { withId(field.blockId) }
            block.hasNotDescendant { withText("Поле должно быть заполнено буквами М или Ж") }
        }
        return this
    }
}