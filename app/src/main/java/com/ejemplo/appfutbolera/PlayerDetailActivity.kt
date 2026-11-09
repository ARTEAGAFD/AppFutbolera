package com.ejemplo.appfutbolera

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ejemplo.appfutbolera.network.PlayerDetailResponse
import com.ejemplo.appfutbolera.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var ivPlayerPhoto: ImageView
    private lateinit var tvPlayerName: TextView
    private lateinit var tvDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        ivPlayerPhoto = findViewById(R.id.ivPlayerPhoto)
        tvPlayerName = findViewById(R.id.tvPlayerName)
        tvDescription = findViewById(R.id.tvDescription)

        val playerId = intent.getStringExtra("PLAYER_ID") ?: ""
        loadPlayerDetail(playerId)
    }

    private fun loadPlayerDetail(playerId: String) {
        RetrofitClient.instance.getPlayerDetail(playerId).enqueue(object : Callback<PlayerDetailResponse> {
            override fun onResponse(call: Call<PlayerDetailResponse>, response: Response<PlayerDetailResponse>) {
                val player = response.body()?.players?.firstOrNull()
                if (player != null) {
                    tvPlayerName.text = player.strPlayer
                    tvDescription.text = player.strDescriptionEN ?: "Sin descripción disponible."

                    val photoUrl = player.strCutout ?: player.strThumb
                    Glide.with(this@PlayerDetailActivity)
                        .load(photoUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivPlayerPhoto)
                }
            }

            override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
                Toast.makeText(this@PlayerDetailActivity, "Error al cargar detalle del jugador", Toast.LENGTH_SHORT).show()
            }
        })
    }
}