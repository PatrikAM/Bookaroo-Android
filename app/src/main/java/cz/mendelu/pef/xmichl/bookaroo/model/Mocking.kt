package cz.mendelu.pef.xmichl.bookaroo.model
import kotlin.random.Random

val libraryList = listOf(
    Library(org.mongodb.kbson.ObjectId(), "Library1", org.mongodb.kbson.ObjectId(), 1, 2, 3),
    Library(org.mongodb.kbson.ObjectId(), "Library2", org.mongodb.kbson.ObjectId(), 3, 4, 666)
)


fun createRandomReader(): Reader {
    val num: Int = Random.nextInt()
    return Reader(
        org.mongodb.kbson.ObjectId(),
        "ReaderName$num",
        "reader$num@example.com",
        "randomPassword.1",
        "randomToken"
    )
}