import ru.tinkoff.favouritepersons.R


enum class FullDataScreenFields(
    val header: String,
    val id: Int,
    val blockId: Int
) {

    NAME("Имя", R.id.et_name, R.id.til_name),
    SURNAME("Фамилия", R.id.et_surname, R.id.til_surname),
    GENDER("Пол", R.id.et_gender, R.id.til_gender),
    BIRTHDAY("Дата рождения", R.id.et_birthdate, R.id.til_birthdate),
    EMAIL("E-mail", R.id.et_email, R.id.til_email),
    PHONE_NUM("Номер телефона", R.id.et_phone, R.id.til_phone),
    ADDRESS("Адрес", R.id.et_address, R.id.til_address),
    LINK_PHOTO("Сcыkка на фотку", R.id.et_image, R.id.til_image_link),
    SUMMARY_POINTS("Итоговый балл", R.id.et_score, R.id.til_score);

    companion object {

        fun getByString(string: String): FullDataScreenFields {
            for (v in FullDataScreenFields.values()) {
                if (v.header.lowercase() == string.lowercase()) {
                    return v
                }
            }
            throw IllegalArgumentException("Переданно некорректное имя поля - $string")
        }
    }
}