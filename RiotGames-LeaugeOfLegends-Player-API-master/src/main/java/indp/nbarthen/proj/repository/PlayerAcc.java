package indp.nbarthen.proj.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PlayerAcc {
	@Id
	private String accId;
	//summonersId is synonymous with Riots naming of: 'encryptedAccountId' and 'id?'
	private String summonerId;
	private String accName;
	private String puuid;
	private String currPatch;
	
	private int iconId;
	private String iconUrl;
	
	private int summonerLevel;
	private boolean isSoloRanked;
	private boolean isFlexRanked;
	@OneToOne
	private SummonerRank soloRank;
	@OneToOne
	private SummonerRank flexRank;
	@OneToOne
	private RecentMatchSummary recentMatchSummary;
	private Vector<String> matchHistoryList;
	private Vector<LoLMatch> matchHistory;
	private Vector<Champion> recentChampions;
	
	private String apiError;
	
	public PlayerAcc(){
		accName = "";
		iconId = 29;
		summonerLevel = 0;
		soloRank = new SummonerRank("Solo/Duo");
		flexRank = new SummonerRank("Flex");
		recentMatchSummary = new RecentMatchSummary();
		matchHistoryList = new Vector<String>();
		matchHistory = new Vector<LoLMatch>();
		//Only saving the user's top 12 most played champions. 
		recentChampions = new Vector<Champion>();
		apiError = "";
	}


	public String getAccId() {
		return accId;
	}


	public void setAccId(String accId) {
		this.accId = accId;
	}


	public String getSummonerId() {
		return summonerId;
	}


	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}


	public String getAccName() {
		return accName;
	}


	public void setAccName(String accName) {
		this.accName = accName;
	}


	public String getPuuid() {
		return puuid;
	}


	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}


	public String getCurrPatch() {
		return currPatch;
	}


	public void setCurrPatch(String currPatch) {
		this.currPatch = currPatch;
	}


	public int getIconId() {
		return iconId;
	}


	public void setIconId(int iconId) {
		this.iconId = iconId;
	}


	public String getIconUrl() {
		return iconUrl;
	}


	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}


	public int getSummonerLevel() {
		return summonerLevel;
	}


	public void setSummonerLevel(int summonerLevel) {
		this.summonerLevel = summonerLevel;
	}
	
	
	
	//soloRank & flexRank Getter/Setter Methods 
	
		
		public boolean isSoloRanked() {
		return isSoloRanked;
	}


	public void setSoloRanked(boolean isSoloRanked) {
		this.isSoloRanked = isSoloRanked;
	}


	public boolean isFlexRanked() {
		return isFlexRanked;
	}


	public void setFlexRanked(boolean isFlexRanked) {
		this.isFlexRanked = isFlexRanked;
	}


		//get for soloRank
	 	public String getSoloRankAccId() {
	        return soloRank.getAccId();
	    }

	    public String getSoloRankLeagueId() {
	        return soloRank.getLeagueId();
	    }

	    public String getSoloRankQueueType() {
	        return soloRank.getQueueType();
	    }

	    public String getSoloRankTier() {
	        return soloRank.getTier();
	    }

	    public String getSoloRankRank() {
	        return soloRank.getRank();
	    }

	    public String getSoloRankSummonerId() {
	        return soloRank.getSummonerId();
	    }

	    public String getSoloRankSummonerName() {
	        return soloRank.getSummonerName();
	    }

	    public int getSoloRankLeaguePoints() {
	        return soloRank.getLeaguePoints();
	    }

	    public int getSoloRankWins() {
	        return soloRank.getWins();
	    }

	    public int getSoloRankLosses() {
	        return soloRank.getLosses();
	    }
	    @JsonIgnore
	    public int getSoloRankWinRate() {
	        return soloRank.getWinRate();
	    }
	    
	    //getting for flexRank
	    public String getFlexRankAccId() {
	        return flexRank.getAccId();
	    }

	    public String getFlexRankLeagueId() {
	        return flexRank.getLeagueId();
	    }

	    public String getFlexRankQueueType() {
	        return flexRank.getQueueType();
	    }

	    public String getFlexRankTier() {
	        return flexRank.getTier();
	    }

	    public String getFlexRankRank() {
	        return flexRank.getRank();
	    }

	    public String getFlexRankSummonerId() {
	        return flexRank.getSummonerId();
	    }

	    public String getFlexRankSummonerName() {
	        return flexRank.getSummonerName();
	    }

	    public int getFlexRankLeaguePoints() {
	        return flexRank.getLeaguePoints();
	    }

	    public int getFlexRankWins() {
	        return flexRank.getWins();
	    }

	    public int getFlexRankLosses() {
	        return flexRank.getLosses();
	    }
	    
	    @JsonIgnore
	    public int getFlexRankWinRate() {
	        return flexRank.getWinRate();
	    }
	    
	    
	    //setters for soloRank
	    public void setSoloRankAccId(String accId) {
	        soloRank.setAccId(accId);
	    }

	    public void setSoloRankLeagueId(String leagueId) {
	        soloRank.setLeagueId(leagueId);
	    }

	    public void setSoloRankQueueType(String queueType) {
	        soloRank.setQueueType(queueType);
	    }

	    public void setSoloRankTier(String tier) {
	        soloRank.setTier(tier);
	    }

	    public void setSoloRankRank(String rank) {
	        soloRank.setRank(rank);
	    }

	    public void setSoloRankSummonerId(String summonerId) {
	        soloRank.setSummonerId(summonerId);
	    }

	    public void setSoloRankSummonerName(String summonerName) {
	        soloRank.setSummonerName(summonerName);
	    }

	    public void setSoloRankLeaguePoints(int leaguePoints) {
	        soloRank.setLeaguePoints(leaguePoints);
	    }

	    public void setSoloRankWins(int wins) {
	        soloRank.setWins(wins);
	    }

	    public void setSoloRankLosses(int losses) {
	        soloRank.setLosses(losses);
	    }
	    
	    
	    //setters for flexRank
	    public void setFlexRankAccId(String accId) {
	        flexRank.setAccId(accId);
	    }

	    public void setFlexRankLeagueId(String leagueId) {
	        flexRank.setLeagueId(leagueId);
	    }

	    public void setFlexRankQueueType(String queueType) {
	        flexRank.setQueueType(queueType);
	    }

	    public void setFlexRankTier(String tier) {
	        flexRank.setTier(tier);
	    }

	    public void setFlexRankRank(String rank) {
	        flexRank.setRank(rank);
	    }
	    public void setFlexRankSummonerId(String summonerId) {
	        flexRank.setSummonerId(summonerId);
	    }

	    public void setFlexRankSummonerName(String summonerName) {
	        flexRank.setSummonerName(summonerName);
	    }

	    public void setFlexRankLeaguePoints(int leaguePoints) {
	        flexRank.setLeaguePoints(leaguePoints);
	    }

	    public void setFlexRankWins(int wins) {
	        flexRank.setWins(wins);
	    }

	    public void setFlexRankLosses(int losses) {
	        flexRank.setLosses(losses);
	    }

	    // END SOLO / FLEX  getter/setters
	    
	    //Start MATCH HISTORY getter/setters
	    
		public RecentMatchSummary getRecentMatchSummary() {
			return recentMatchSummary;
		}


		public void setRecentMatchSummary(RecentMatchSummary recentMatchSummary) {
			this.recentMatchSummary = recentMatchSummary;
		}


		public Vector<String> getMatchHistoryList() {
			return matchHistoryList;
		}
		public void setMatchHistoryList(Vector<String> matchHistoryList) {
			this.matchHistoryList = matchHistoryList;
		}


		public Vector<LoLMatch> getMatchHistory() {
			return matchHistory;
		}


		public void setMatchHistory(Vector<LoLMatch> matchHistory) {
			this.matchHistory = matchHistory;
		}


		public Vector<Champion> getRecentChampions() {
			return recentChampions;
		}


		public void setRecentChampions(Vector<Champion> recentChampions) {
			this.recentChampions = recentChampions;
		}


		public String getApiError() {
			return apiError;
		}


		public void setApiError(String apiError) {
			this.apiError = apiError;
		}

	    
}
