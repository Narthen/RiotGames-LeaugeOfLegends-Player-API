package indp.nbarthen.proj.repository;

import java.text.DecimalFormat;
import java.util.Vector;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//Summoner in a person's game.
@Entity
public class Participant {
	@Id
	private String puuid; //puuid
	private String accName; //summonerName
	private String mapSide; //teamId
	private int championId; //championId
	private String championName; //championName
	private String championUrl; 
	private int kills; //kills
	private int deaths; //deaths
	private int assists; //assists
	private double goldEarned; //goldEarned
	private int totalMinionsKilled; //cs (creep-score)
	
	//Constructor
	public Participant(){
		
	}

	public String getPuuid() {
		return puuid;
	}

	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getMapSide() {
		return mapSide;
	}

	public void setMapSide(String mapSide) {
		this.mapSide = mapSide;
	}

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public String getChampionUrl() {
		return championUrl;
	}

	public void setChampionUrl(String currPatch) {
		this.championUrl  ="http://ddragon.leagueoflegends.com/cdn/"+ currPatch +"/img/champion/"+ this.championName +".png";
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public double getGoldEarned() {
		return goldEarned;
	}

	public void setGoldEarned(int goldEarned) {
		//Round gold to thousands (Ex. 12,123 = 12.1)
		double goldEarnedDouble = goldEarned;
		goldEarnedDouble = goldEarnedDouble / 1000;
		goldEarnedDouble = (double) Math.round(goldEarnedDouble * 10) / 10;
		this.goldEarned = goldEarnedDouble;
	}

	public int getTotalMinionsKilled() {
		return totalMinionsKilled;
	}

	public void setTotalMinionsKilled(int totalMinionsKilled) {
		this.totalMinionsKilled = totalMinionsKilled;
	}
	

	
	
	
	
}
