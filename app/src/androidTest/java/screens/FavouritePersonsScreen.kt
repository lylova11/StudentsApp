package screens

import android.view.View
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher

import ru.tinkoff.favouritepersons.R


class FavouritePersonsScreen(testContext: TestContext<*>) : BaseKaspressoScreen(testContext) {

    private val addButton = KButton { withId(R.id.fab_add_person) }
    private val addPersonByNetworkButton = KButton { withId(R.id.fab_add_person_by_network) }
    private val noPeopleDisplayed = KEditText { withId(R.id.tw_no_persons) }
    private val message = "Нет ни одного человечка( Нажми на кнопку в правом нижнем углу"
    private val filterBtn = KButton { withId(R.id.action_item_sort) }
    private val addPersonManual = KButton { withId(R.id.fab_add_person_manually) }
    private val noInternetConnectionMessage =
        KView { withText("Internet error! Check your connection") }

    private val recyclerView = KRecyclerView(
        builder = { withId(R.id.rv_person_list) },
        itemTypeBuilder = { itemType(::PersonListViewKItem) }
    )

    fun clickAddButtonButton() {
        step("Нажимаем кнопку Добавить") {
            addButton.click()
        }
    }

    fun clickAddPersonByNetworkButton() {
        step("Нажимаем кнопку Добавить по сети") {
            addPersonByNetworkButton.click()
        }
    }

    fun checkMessageNoPersonsIsNotDisplayed() {
        step("Проверяем, что сообщение $message не отображается") {
            noPeopleDisplayed.isNotDisplayed()
        }
    }

    fun swipePersonElement() {
        step("Делаем свайп влево элементом списка людей") {
            recyclerView.swipeLeft()
        }
    }

    fun checkPersonListSizeListSize(size: Int) {
        step("Проверка что на экране отображается список из $size элементов") {
            recyclerView.hasSize(size)
        }
    }

    fun tapOnFilter(): BottomSheetFilter {
        step("Нажимаем на фильтр") {
            filterBtn.click()
        }
        return BottomSheetFilter(testContext)
    }

    fun checkPersonOnPositionByName(position: Int, text: String): FavouritePersonsScreen {
        step("Проверяем ${text} на позиции ${position}") {
            recyclerView {
                childAt<PersonListViewKItem>(position) {
                    twName.hasText(containsString(text))
                }
            }
        }
        return this
    }


    fun checkPersonInfoOnPosition(
        position: Int,
        name: String = "",
        surname: String = "",
        gender: String? = null,
        age: Int? = null,
        email: String = "",
        phone: String = "",
        address: String = "",
        rate: String = ""
    ) {
        recyclerView {
            childAt<PersonListViewKItem>(position) {
                twName.hasText(containsString(name))
                twName.hasText(containsString(surname))
                twPrivateInfo.hasText(containsString(gender ?: ""))
                twPrivateInfo.hasText(containsString(age?.toString() ?: ""))
                twEMail.hasText(containsString(email))
                twPhone.hasText(containsString(phone))
                twAddress.hasText(containsString(address))
                twRating.hasText(containsString(rate))
            }
        }

    }

    fun clickOnPosition(position: Int) {
        step("Кликаем на позицию ${position} в списке пользователей") {
            recyclerView {
                childAt<PersonListViewKItem>(position) {
                    twName.click()
                }
            }
        }
    }

    fun clickAddNewManual() {
        step("Нажимаем на кнопку добавления пользовтеля вручную") {
            addPersonManual.click()
        }
    }

    fun checkNoInternetConnectionErrorMessage() {
        noInternetConnectionMessage.isDisplayed()
    }

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null
}

private class PersonListViewKItem(matcher: Matcher<View>) :
    KRecyclerItem<PersonListViewKItem>(matcher) {
    val twName = KTextView(matcher) { withId(R.id.person_name) }
    val twEMail = KTextView(matcher) { withId(R.id.person_email) }
    val twPrivateInfo = KTextView(matcher) { withId(R.id.person_private_info) }
    val twPhone = KTextView(matcher) { withId(R.id.person_phone) }
    val twAddress = KTextView(matcher) { withId(R.id.person_address) }
    val twRating = KTextView(matcher) { withId(R.id.person_rating) }
}

