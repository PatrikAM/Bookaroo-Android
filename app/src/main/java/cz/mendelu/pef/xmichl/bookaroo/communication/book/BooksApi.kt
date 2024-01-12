package cz.mendelu.pef.xmichl.bookaroo.communication.book

import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @POST("book")
    suspend fun createBook(
        @Query("title") title: String,
        @Query("author") author: String,
        @Query("library") library: String,
        @Query("isbn") isbn: String?,
        @Query("cover") cover: String?,
        @Query("subtitle") subtitle: String?,
        @Query("pages") pages: Int?,
        @Query("description") description: String?,
        @Query("publisher") publisher: String?,
        @Query("published") published: String?,
        @Query("language") language: String?,
        @Query("token") userToken: String
    ) : Response<Book>

    @GET("book/all_books")
    suspend fun fetchBooks(
        @Query("token") token: String
    ) : Response<List<Book>>

    @GET("book/{book_id}")
    suspend fun fetchBook(
        @Path("book_id") bookId: String,
        @Query("token") token: String
    ) : Response<Book>

    @GET("book/remove_by_id/{book_id}")
    suspend fun deleteBook(
        @Path("book_id") bookId: String,
        @Query("token") token: String
    ) : Response<Book>

    @GET("book/find_by_library/{library_id}")
    suspend fun fetchBooksFromLibrary(
        @Path("library_id") libraryId: String,
        @Query("token") token: String
    ) : Response<List<Book>>

    @PUT("book")
    suspend fun updateBook(
        @Body book: Book,
        @Query("token") token: String
    ) : Response<Book>
}