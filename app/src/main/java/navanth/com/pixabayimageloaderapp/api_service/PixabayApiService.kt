package navanth.com.pixabayimageloaderapp.api_service

import io.reactivex.Observable
import navanth.com.pixabayimageloaderapp.model.PixabayImageList
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {
    @GET("api/")
    fun search(
        @Query("key") key: String?, @Query("q") query: String?,
        @Query("page") page: Int, @Query("per_page") perPage: Int
    ): Observable<PixabayImageList?>?

    /**
     * Factory class for convenient creation of the Api Service interface
     */
    object Factory {
        fun create(): PixabayApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pixabay.com/")
                .build()
            return retrofit.create(PixabayApiService::class.java)
        }
    }
}