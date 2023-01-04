package indp.nbarthen.proj.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RecentMatchSummary {
	@Id
	private String accId;
	private int wins;
	private int losses;
	private String winRate;
	private String kda;
	private String kd;
	private int blindGames;
	private int draftGames;
	private int rankedGames;
	private int flexGames;
	private int aramGames;
	private int otherGames;
	
	public RecentMatchSummary() {
		accId= "";
		wins = 0;
		losses = 0;
		winRate = "0";
		kda = "0-0-0";
		kd = "0.0";
		blindGames = 0;
		draftGames = 0;
		rankedGames = 0;
		flexGames = 0;
		aramGames = 0;
		otherGames = 0;
	}
	
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
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

	public int getBlindGames() {
		return blindGames;
	}
	public void setBlindGames(int blindGames) {
		this.blindGames = blindGames;
	}
	public int getDraftGames() {
		return draftGames;
	}
	public void setDraftGames(int draftGames) {
		this.draftGames = draftGames;
	}
	public int getRankedGames() {
		return rankedGames;
	}
	public void setRankedGames(int rankedGames) {
		this.rankedGames = rankedGames;
	}
	public int getFlexGames() {
		return flexGames;
	}
	public void setFlexGames(int flexGames) {
		this.flexGames = flexGames;
	}
	public int getAramGames() {
		return aramGames;
	}
	public void setAramGames(int aramGames) {
		this.aramGames = aramGames;
	}
	public int getOtherGames() {
		return otherGames;
	}
	public void setOtherGames(int otherGames) {
		this.otherGames = otherGames;
	}
	
	
}
