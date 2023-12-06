package cz.mendelu.pef.xmichl.bookaroo.communication.book

import cz.mendelu.pef.xmichl.bookaroo.model.Book
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @POST("book")
    suspend fun createBook(
        @Query("book") book: Book,
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

    @GET("book/find_by_library/{library_id}")
    suspend fun fetchBooksFromLibrary(
        @Path("library_id") libraryId: String,
        @Query("token") token: String
    ) : Response<List<Book>>
}