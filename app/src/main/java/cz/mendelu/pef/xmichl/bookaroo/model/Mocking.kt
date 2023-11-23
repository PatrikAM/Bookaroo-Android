package cz.mendelu.pef.xmichl.bookaroo.model

import java.util.UUID

val libraryList = listOf(
    Library(UUID.randomUUID(), "Library1", createRandomReader(), 1, 2, 3),
    Library(UUID.randomUUID(), "Library2", createRandomReader(), 3, 4, 666)
)


fun createRandomReader(): Reader {
    return Reader(
        UUID.randomUUID(),
        "ReaderName",
        "reader@example.com",
        "randomPassword",
        "randomToken"
    )
}