package com.ejemplo.appfutbolera.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 1. Modelos JSON
data class TeamResponse(val teams: List<Team>?)
data class Team(
    val idTeam: String,
    val strTeam: String,
    val strBadge: String?,
    val idLeague: String?
)

data class PlayerResponse(val player: List<Player>?)
data class Player(
    val idPlayer: String,
    val strPlayer: String,
    val strPosition: String?
)

data class TableResponse(val table: List<TableItem>?)
data class TableItem(
    val intRank: String?,
    val strTeam: String?,
    val intPlayed: String?,
    val intPoints: String?
)

data class PlayerDetailResponse(val players: List<PlayerDetail>?)
data class PlayerDetail(
    val idPlayer: String,
    val strPlayer: String,
    val strCutout: String?,
    val strThumb: String?,
    val strDescriptionEN: String?
)

// 2. Definición de los 4 endpoints de la evaluación
interface TheSportsDbApi {
    @GET("searchteams.php")
    fun searchTeam(@Query("t") teamName: String): Call<TeamResponse>

    @GET("lookup_all_players.php")
    fun getPlayersByTeam(@Query("id") teamId: String): Call<PlayerResponse>

    @GET("lookuptable.php")
    fun getLeagueTable(@Query("l") leagueId: String): Call<TableResponse>

    @GET("lookupplayer.php")
    fun getPlayerDetail(@Query("id") playerId: String): Call<PlayerDetailResponse>
}