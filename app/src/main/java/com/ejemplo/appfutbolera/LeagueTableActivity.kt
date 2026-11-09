package com.ejemplo.appfutbolera

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.appfutbolera.adapters.TableAdapter
import com.ejemplo.appfutbolera.network.RetrofitClient
import com.ejemplo.appfutbolera.network.TableResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeagueTableActivity : AppCompatActivity() {

    private lateinit var rvTable: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_table)

        rvTable = findViewById(R.id.rvTable)
        rvTable.layoutManager = LinearLayoutManager(this)

        val leagueId = intent.getStringExtra("LEAGUE_ID") ?: ""
        loadTable(leagueId)
    }

    private fun loadTable(leagueId: String) {
        RetrofitClient.instance.getLeagueTable(leagueId).enqueue(object : Callback<TableResponse> {
            override fun onResponse(call: Call<TableResponse>, response: Response<TableResponse>) {
                val tableItems = response.body()?.table ?: emptyList()
                if (tableItems.isEmpty()) {
                    Toast.makeText(this@LeagueTableActivity, "Tabla no disponible", Toast.LENGTH_SHORT).show()
                } else {
                    rvTable.adapter = TableAdapter(tableItems)
                }
            }

            override fun onFailure(call: Call<TableResponse>, t: Throwable) {
                Toast.makeText(this@LeagueTableActivity, "Error al cargar la tabla", Toast.LENGTH_SHORT).show()
            }
        })
    }
}