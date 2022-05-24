package waslim.binar.andlima.classdiscussions.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import waslim.binar.andlima.classdiscussions.model.GetAllUserResponseItem


interface ApiService {

    @GET("user")
    fun getDataUser(
    @Query ("username") username : String,
    @Query ("password") password : String
    ) : Call<List<GetAllUserResponseItem>>

}