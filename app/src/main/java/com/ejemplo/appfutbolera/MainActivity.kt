package com.ejemplo.appfutbolera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.appfutbolera.adapters.TeamAdapter
import com.ejemplo.appfutbolera.network.RetrofitClient
import com.ejemplo.appfutbolera.network.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etTeamName: EditText
    private lateinit var btnSearch: Button
    private lateinit var rvTeams: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTeamName = findViewById(R.id.etTeamName)
        btnSearch = findViewById(R.id.btnSearch)
        rvTeams = findViewById(R.id.rvTeams)
        rvTeams.layoutManager = LinearLayoutManager(this)

        btnSearch.setOnClickListener {
            val query = etTeamName.text.toString().trim()
            if (query.isNotEmpty()) {
                searchTeams(query)
            } else {
                Toast.makeText(this, "Ingresa un nombre de equipo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchTeams(teamName: String) {
        RetrofitClient.instance.searchTeam(teamName).enqueue(object : Callback<TeamResponse> {
            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                val teams = response.body()?.teams ?: emptyList()
                if (teams.isEmpty()) {
                    Toast.makeText(this@MainActivity, "No se encontraron equipos", Toast.LENGTH_SHORT).show()
                } else {
                    rvTeams.adapter = TeamAdapter(teams) { selectedTeam ->
                        val intent = Intent(this@MainActivity, TeamDetailActivity::class.java)
                        intent.putExtra("TEAM_ID", selectedTeam.idTeam)
                        intent.putExtra("TEAM_NAME", selectedTeam.strTeam)
                        intent.putExtra("LEAGUE_ID", selectedTeam.idLeague)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}