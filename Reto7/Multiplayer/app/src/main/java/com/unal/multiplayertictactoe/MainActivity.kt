package com.unal.multiplayertictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.unal.multiplayertictactoe.BackCommunicationImp.BackCommunication
import com.unal.multiplayertictactoe.BackCommunicationImp.RestEngine
import com.unal.multiplayertictactoe.BackCommunicationImp.domain.Entities.GamesDataCollectionItem
import com.unal.multiplayertictactoe.BackCommunicationImp.domain.Interfaces.GamesService
import com.unal.multiplayertictactoe.Game.CreateGame
import com.unal.multiplayertictactoe.Game.JoinGame
import com.unal.multiplayertictactoe.Game.MainGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var back = BackCommunication()
    private var btnNewGame: Button? = null
    private var btnJoinGame: Button? = null
    private var btnExit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNewGame = findViewById(R.id.btn_newgame)
        btnJoinGame = findViewById(R.id.btn_joingame)
        btnExit = findViewById(R.id.btn_exit)


        btnNewGame!!.setOnClickListener {
            val intent = Intent(this, CreateGame::class.java)
            startActivity(intent)
        }
        btnJoinGame!!.setOnClickListener {
            val intent = Intent(this, JoinGame::class.java)
            startActivity(intent)
            //joinGame()
          //  listGames()
        }
        btnExit!!.setOnClickListener {
           /* moveTaskToBack(true)
            exitProcess(-1)*/
        }
    }



}

