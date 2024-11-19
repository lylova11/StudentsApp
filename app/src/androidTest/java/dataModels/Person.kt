package dataModels


data class Person(
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: String? = null,
    val gender: String? = null


) {
    companion object {
        val empty = Person()
    }
}