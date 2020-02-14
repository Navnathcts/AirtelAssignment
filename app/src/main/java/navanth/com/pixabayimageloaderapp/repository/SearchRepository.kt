package navanth.com.pixabayimageloaderapp.repository

import io.reactivex.Observable
import navanth.com.pixabayimageloaderapp.api_service.PixabayApiService
import navanth.com.pixabayimageloaderapp.model.PixabayImageList

class SearchRepository(val apiService: PixabayApiService) {
    fun searchUsers(
        apiKey: String,
        imageSearchKeyword: String,
        page: Int,
        per_page: Int
    ): Observable<PixabayImageList?>? {
        return apiService.search(apiKey, imageSearchKeyword, page, per_page)
    }
}