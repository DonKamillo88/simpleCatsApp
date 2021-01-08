package org.k2apps.simplecatsapp.data.repository.remote


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.k2apps.simplecatsapp.data.model.Cat
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.thecatapi.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CatsApiService {

    @GET("/v1/images/search")
    suspend fun getRandomCat(): List<Cat>

    @GET("/v1/images/search?limit=25&page=10&order=Desc")
    suspend fun getCats(): List<Cat>
}

object CatsApi {
    val retrofitService: CatsApiService by lazy { retrofit.create(CatsApiService::class.java) }
}
