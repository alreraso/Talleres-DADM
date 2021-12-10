package com.unal.multiplayertictactoe.BackCommunicationImp

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.unal.multiplayertictactoe.BackCommunicationImp.domain.Entities.GamesDataCollectionItem
import com.unal.multiplayertictactoe.BackCommunicationImp.domain.Interfaces.GamesService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.concurrent.schedule

class BackCommunication {


    fun sendChange(position:String){

    }

    fun getPossition(matrix:String):String{

        /*val gamesService :  GamesService = RestEngine.getRestEngine().create(GamesService::class.java)
        val result: Call<List<GamesDataCollectionItem>> = gamesService.listGames()

        result.enqueue(object : Callback<List<GamesDataCollectionItem>>{
            override fun onFailure(call: Call<List<GamesDataCollectionItem>>, t: Throwable) {
                System.out.println("Error")
            }

            override fun onResponse(
                call: Call<List<GamesDataCollectionItem>>,
                response: Response<List<GamesDataCollectionItem>>
            ) {
                val myItem = response.body()
                if(response?.body() != null){
                    System.out.println(myItem)
                }


            }

        })*/
        var stringMatrix = "[[2,0,0],[0,0,0],[0,0,0]]"

            var x = "1,1"
            System.out.println("uno mas")

        return (stringMatrix)
    }

    fun listGames(): Response<ResponseBody>?{
        var value :  Response<ResponseBody>? = null
        val gamesService :  GamesService = RestEngine.getRestEngine().create(GamesService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = gamesService.listGames()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                   /* val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )*/

                   // value = response

                   // Log.d("Pretty Printed JSON :", prettyJson)


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
        return value!!
    }

    fun joinGame(){

    }

    fun CreateGame(){

    }

    fun postGame(){

    }



}