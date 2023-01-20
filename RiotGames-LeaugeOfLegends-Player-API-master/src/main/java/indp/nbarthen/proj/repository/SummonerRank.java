package indp.nbarthen.proj.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SummonerRank {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String accId;
	private String leagueId;
	private String queueType;
	private String tier;
	private String rank;
	private String summonerId;
	private String summonerName;
	private int leaguePoints;
	private int wins;
	private int losses;
	
	
	//Constructor
	public SummonerRank(){
		this.queueType = "";
		this.leagueId = "";
		this.tier = "UNRANKED";
		this.rank = "";
		this.summonerId = "";
		this.summonerName = "";
		this.leaguePoints = 0;
		this.wins = 0;
		this.losses = 0;
	}
	public SummonerRank(String queueType){
		this.queueType = queueType;
		this.leagueId = "";
		this.tier = "UNRANKED";
		this.rank = "";
		this.summonerId = "";
		this.summonerName = "";
		this.leaguePoints = 0;
		this.wins = 0;
		this.losses = 0;
	}
	//Constructor
	SummonerRank(String leagueId, String queueType, String tier, String rank, String summonerId, String summonerName, int leaguePoints, int wins, int losses) {
		  this.leagueId = leagueId;
		  this.queueType = queueType;
		  this.tier = tier;
		  this.rank = rank;
		  this.summonerId = summonerId;
		  this.summonerName = summonerName;
		  this.leaguePoints = leaguePoints;
		  this.wins = wins;
		  this.losses = losses;
	}
	
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public String getQueueType() {
		return queueType;
	}
	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getSummonerId() {
		return summonerId;
	}
	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}
	public String getSummonerName() {
		return summonerName;
	}
	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}
	public int getLeaguePoints() {
		return leaguePoints;
	}
	public void setLeaguePoints(int leaguePoints) {
		this.leaguePoints = leaguePoints;
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
	
	
	@JsonIgnore
	public int getWinRate() {
	    if (wins + losses == 0) {
	      return 0;
	    }
	    double winRate = (double) wins / (wins + losses) * 100;
	    return (int) Math.round(winRate);
	    
	  
	}
	
	
}
