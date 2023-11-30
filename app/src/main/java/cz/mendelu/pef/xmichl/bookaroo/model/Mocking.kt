package cz.mendelu.pef.xmichl.bookaroo.model
import kotlin.random.Random

val libraryList = listOf(
    Library("11111", "Library1", "11111", 1, 2, 3),
    Library("22222", "Library2", "22222", 3, 4, 666)
)


fun createRandomReader(): Reader {
    val num: Int = Random.nextInt()
    return Reader(
        "id",
//        org.mongodb.kbson.ObjectId(),
        "ReaderName$num",
        "reader$num@example.com",
//        "randomPassword.1",
        "randomToken"
    )
}