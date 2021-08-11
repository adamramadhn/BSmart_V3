package com.bpkp.bsmartapp.core.data.source.remote.network

import com.bpkp.bsmartapp.core.data.source.remote.response.AuthLoginResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.SpResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {
    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/approval/surattugas")
    fun approvalST(
        @Field("username") username: String,
        @Field("id_st") id_st: Int,
        @Field("approval") approval: Int,
        @Field("catatan") catatan: String,
    ): Call<SuratTugasResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/approval/suratpengantar")
    fun approvalSP(
        @Field("username") username: String,
        @Field("id_st") id_sp: Int?,
        @Field("approval") approval: Int,
        @Field("catatan") catatan: String,
    ): Call<SpResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/tte")
    fun loginTte(
        @Field("username") username: String,
        @Field("nik") nik: String,
        @Field("passphrase") passphrase: String,
    ): Call<DetailST>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/nikpassphrase")
    fun getTte(
        @Field("id_st") id_sp: Int,
        @Field("nik") nik: String,
        @Field("passphrase") passphrase: String,
    ): Call<DetailST>

    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @GET("api/suratpengantar")
    fun getSP(
        @Query("idst") id_st: Int?,
    ): Call<SpResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/surattugas")
    fun getList(
        @Field("username") username: String,
        @Query("page") page: Int
    ): Call<ListSuratTugasResponse>

//    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
//    @GET
//    fun getListST(@Url url: String): Call<ListSuratTugasResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/detail")
    fun getDetailST(
        @Field("username") username: String,
        @Field("idst") id_st: Int,
    ): Call<DetailST>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/filter")
    fun getFilter(
        @Field("username") username: String,
        @Query("page")page:Int
    ): Call<ListSuratTugasResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/cari")
    fun getSearch(
        @Field("username") username: String,
        @Field("cari") cari: String
    ): Call<ListSuratTugasResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("api/login")
    fun authLogin(
        @Field("username") usernameLogin: String,
        @Field("password") passwordLogin: String
    ): Call<AuthLoginResponse>


    companion object {
        operator fun invoke(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://aplikasistore.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}