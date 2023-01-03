package indp.nbarthen.proj.repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoLMatch {
	@Id
	private String matchId;
	private Vector<Participant> participants; //uses PUUID
	private long gameDuration;
	private String gameEndTimestamp;
	private int queueId; 
	private String queueName; //uses https://static.developer.riotgames.com/docs/lol/queues.json and queueId
 	private String winLoss; //"Victory" or "Defeat"
	
 	//Specific Information for the main Summoner 
 	private String summonerAccName;
 	private int summonerChampionId; //championId
	private String summonerChampionName; //championName
	private String summonerChampionUrl; 
	private int summonerKills; //kills
	private int summonerDeaths; //deaths
	private int summonerAssists; //assists
	private int summonerTotalMinionsKilled; //cs (creep-score)
 	private String kda;
 	
 	
	//Constructor
	public LoLMatch(){
		participants = new Vector<Participant>();
	}

	
	
	
	//Getters & Setters
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public Vector<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(Vector<Participant> participants) {
		this.participants = participants;
	}

	public long getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}

	public String getGameEndTimestamp() {
		return gameEndTimestamp;
	}

	public void setGameEndTimestamp(long gameEndTimestamp) {
		long currentTime = System.currentTimeMillis(); // current time in milliseconds
		long elapsedTime = currentTime - gameEndTimestamp; // elapsed time in milliseconds

		long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
		long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
		long days = TimeUnit.MILLISECONDS.toDays(elapsedTime);

		if (days > 0) {
			this.gameEndTimestamp = days + " Day(s) Ago";
		} else if (hours > 0) {
			this.gameEndTimestamp = hours + " Hour(s) Ago";
		} else {
			this.gameEndTimestamp = minutes + " Minute(s) Ago";
		}
		
	}

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getWinLoss() {
		return winLoss;
	}

	public void setWinLoss(String winLoss) {
		this.winLoss = winLoss;
	}
	
	//Getter / Setters for main Summoner (summoner who searched up matchHistory)
	public String getSummonerAccName() {
		return summonerAccName;
	}

	public void setSummonerAccName(String summonerAccName) {
		this.summonerAccName = summonerAccName;
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

	public void setSummonerChampionUrl(String currPatch) {
		this.summonerChampionUrl = "http://ddragon.leagueoflegends.com/cdn/"+ currPatch +"/img/champion/"+ summonerChampionName +".png";
	}

	public int getSummonerKills() {
		return summonerKills;
	}
	public void setSummonerKills(int summonerKills) {
		this.summonerKills = summonerKills;
	}

	public int getSummonerDeaths() {
		return summonerDeaths;
	}

	public void setSummonerDeaths(int summonerDeaths) {
		this.summonerDeaths = summonerDeaths;
	}
	public int getSummonerAssists() {
		return summonerAssists;
	}

	public void setSummonerAssists(int summonerAssists) {
		this.summonerAssists = summonerAssists;
	}

	public int getSummonerTotalMinionsKilled() {
		return summonerTotalMinionsKilled;
	}

	public void setSummonerTotalMinionsKilled(int summonerTotalMinionsKilled) {
		this.summonerTotalMinionsKilled = summonerTotalMinionsKilled;
	}
	
	public String getSummonerKda() {
		double summonerKda = (summonerKills + summonerAssists) / (double) summonerDeaths;
		double roundedKda = Math.round(summonerKda * 10) / 10.0;
		return Double.toString(roundedKda);
	}
	
	
	
	
}
