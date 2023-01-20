package indp.nbarthen.proj.repository;

import java.util.Vector;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Champion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String accId;
	@Column(length = 50000)
	private Vector<String> matchIds; 
	private int summonerChampionId; //championId
	private String summonerChampionName; //championName
	private String summonerChampionUrl; 
	private int wins;
	private int losses;
	private int gamesPlayed;
	private int kills;
	private int deaths;
	private int assists;
	private String winRate;
	private String kda;
	private String kd;
	private String championExists;
	
	public Champion(){
		accId = "";
		matchIds = new Vector<String>();
		summonerChampionId = 0;
		summonerChampionName = "";
		summonerChampionUrl = "";
		wins = 0;
		losses = 0;
		gamesPlayed = 0;
		kills = 0;
		deaths = 0;
		assists = 0;
		winRate = "0";
		kda = "0-0-0";
		kd = "0.0";
		championExists = "false"; //Used in thymeleaf to display div / data
	}
	
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public Vector<String> getMatchIds() {
		return matchIds;
	}

	public void setMatchIds(Vector<String> matchIds) {
		this.matchIds = matchIds;
	}

	public int getSummonerChampionId() {
		return summonerChampionId;
	}
	public void setSummonerChampionId(int summonerChampionId) {
		this.summonerChampionId = summonerChampionId;
	}
	public String getSummonerChampionName() {
		return summonerChampionName;
	}
	public void setSummonerChampionName(String summonerChampionName) {
		this.summonerChampionName = summonerChampionName;
	}
	public String getSummonerChampionUrl() {
		return summonerChampionUrl;
	}
	public void setSummonerChampionUrl(String summonerChampionUrl) {
		this.summonerChampionUrl = summonerChampionUrl;
	}
	
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
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

	public String getWinRate() {
		return winRate;
	}
	public void setWinRate(String winRate) {
		this.winRate = winRate;
	}
	public String getKda() {
		return kda;
	}
	public void setKda(String kda) {
		this.kda = kda;
	}
	public String getKd() {
		return kd;
	}
	public void setKd(String kd) {
		this.kd = kd;
	}

	public String getChampionExists() {
		return championExists;
	}

	public void setChampionExists(String championExists) {
		this.championExists = championExists;
	}
	
	
	
	
	
	
}
