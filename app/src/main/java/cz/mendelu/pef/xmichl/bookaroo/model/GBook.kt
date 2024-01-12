package cz.mendelu.pef.xmichl.bookaroo.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GBooks(
    val totalItems: Int?,
    val items: List<GBook>
)

@JsonClass(generateAdapter = true)
data class GBook(
    val id: String?,
    val volumeInfo: VolumeInfo?,
) {
    fun convert(): Book {
        return Book(
            title = this.volumeInfo?.title,
            author = this.volumeInfo?.authors?.get(0),
            publisher = this.volumeInfo?.publisher,
            published = this.volumeInfo?.publishedDate,
            pages = this.volumeInfo?.pageCount,
            cover = this.volumeInfo?.imageLinks?.get(0)
        )
    }
}

@JsonClass(generateAdapter = true)
data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val categories: List<String>?,
    val language: String?,
    val imageLinks: List<String>?,
    val printType: String?
)