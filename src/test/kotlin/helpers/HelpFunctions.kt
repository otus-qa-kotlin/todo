package helpers

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + ('А'..'Я') + ('а'..'я')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}