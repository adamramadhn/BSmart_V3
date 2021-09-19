package com.bpkp.bsmartapp.core.data.source.remote.network

import com.bpkp.bsmartapp.core.data.source.remote.response.AuthLoginResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananResponse
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
    @POST("login")
    fun authLogin(
        @Field("username") usernameLogin: String,
        @Field("password") passwordLogin: String
    ): Call<AuthLoginResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("surattugas")
    fun getList(
        @Field("id_pegawai") username: String,
        @Field("rule") rule: Int,
        @Field("approval") approval: Int,
    ): Call<ListSuratTugasResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("surattugas/cari")
    fun getSearch(
        @Field("id_pegawai") username: String,
        @Field("rule") rule: Int,
        @Field("cari") cari: String
    ): Call<ListSuratTugasResponse>

    //================================================
    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("surattugas/approval")
    fun approvalST(
        @Field("id_pegawai") id_pegawai: String,
        @Field("id_st") id_st: Int,
        @Field("rule") rule: Int,
        @Field("approval") approval: Int,
        @Field("catatan") catatan: String,
    ): Call<SuratTugasResponse>


    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @GET("surattugas/detail")
    fun getDetailST(
        @Query("id_st") id_st: Int
//        @Field("username") username: String,
//        @Field("idst") id_st: Int,
    ): Call<DetailST>

//    @FormUrlEncoded
//    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
//    @POST("surattugas/filter")
//    fun getFilter(
//        @Field("username") username: String,
//        @Query("page") page: Int
//    ): Call<ListSuratTugasResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("suratpengantar/approval")
    fun approvalSP(
        @Field("username") username: String,
        @Field("id_st") id_sp: Int?,
        @Field("approval") approval: Int,
        @Field("catatan") catatan: String,
    ): Call<SpResponse>

    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("tte")
    fun getTte(
        @Field("id_st") id_st: Int,
        @Field("passphrase") passphrase: String,
        @Field("nik") nik: String,
    ): Call<DetailST>

//    @FormUrlEncoded
//    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
//    @POST("passphrase")
//    fun loginTte(
//        @Field("username") id_sp: String,
//        @Field("nik") nik: String,
//        @Field("passphrase") passphrase: String,
//    ): Call<DetailST>

    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @GET("suratpengantar")
    fun getSP(
        @Query("id_st") id_st: Int?,
    ): Call<SpResponse>


//    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
//    @GET
//    fun getListST(@Url url: String): Call<ListSuratTugasResponse>


    //Pembebanan
    @FormUrlEncoded
    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @POST("beban")
    fun getBebanList(
        @Field("id_pegawai") id_pegawai: String,
        @Field("rule") rule: Int,
    ): Call<PembebananResponse>

    @Headers("Authorization:Bearer 3|nqrPrNiabhSqjVMa57cZT8fb3kzU40X42RsRKKYL")
    @GET("beban/detail")
    fun getDetailBeban(
        @Query("id_beban") id_beban: Int?,
    ): Call<SpResponse> //ganti


    companion object {
        operator fun invoke(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://mobsmart.bpkp.go.id/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}