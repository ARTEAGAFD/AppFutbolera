package com.ejemplo.appfutbolera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.appfutbolera.adapters.PlayerAdapter
import com.ejemplo.appfutbolera.network.PlayerResponse
import com.ejemplo.appfutbolera.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetailActivity : AppCompatActivity() {

    private lateinit var tvTeamName: TextView
    private lateinit var btnLeagueTable: Button
    private lateinit var rvPlayers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        tvTeamName = findViewById(R.id.tvTeamName)
        btnLeagueTable = findViewById(R.id.btnLeagueTable)
        rvPlayers = findViewById(R.id.rvPlayers)
        rvPlayers.layoutManager = LinearLayoutManager(this)

        val teamId = intent.getStringExtra("TEAM_ID") ?: ""
        val teamName = intent.getStringExtra("TEAM_NAME") ?: "Equipo"
        val leagueId = intent.getStringExtra("LEAGUE_ID") ?: ""

        tvTeamName.text = teamName

        btnLeagueTable.setOnClickListener {
            if (leagueId.isNotEmpty()) {
                val intent = Intent(this, LeagueTableActivity::class.java)
                intent.putExtra("LEAGUE_ID", leagueId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Liga no disponible para este equipo", Toast.LENGTH_SHORT).show()
            }
        }

        loadPlayers(teamId)
    }

    private fun loadPlayers(teamId: String) {
        RetrofitClient.instance.getPlayersByTeam(teamId).enqueue(object : Callback<PlayerResponse> {
            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                val players = response.body()?.player ?: emptyList()
                rvPlayers.adapter = PlayerAdapter(players) { selectedPlayer ->
                    val intent = Intent(this@TeamDetailActivity, PlayerDetailActivity::class.java)
                    intent.putExtra("PLAYER_ID", selectedPlayer.idPlayer)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                Toast.makeText(this@TeamDetailActivity, "Error al cargar jugadores", Toast.LENGTH_SHORT).show()
            }
        })
    }
}