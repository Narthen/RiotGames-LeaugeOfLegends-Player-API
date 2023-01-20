package indp.nbarthen.proj.repository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PlayerAcc {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(insertable=false, updatable=false)
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
	@OneToOne(cascade = CascadeType.ALL)
	private SummonerRank soloRank;
	@OneToOne(cascade = CascadeType.ALL)
	private SummonerRank flexRank;
	
	private String matchType;
	@Embedded
	private RecentMatchSummary recentMatchSummary;
	@Column(length = 10000)
	private Vector<String> matchHistoryList;
	@OneToMany(cascade = CascadeType.ALL)
	private List<LoLMatch> matchHistory;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Champion> recentChampions;
	
	private String apiError;
	
	public PlayerAcc(){
		accName = "";
		iconId = 29;
		summonerLevel = 0;
		soloRank = new SummonerRank("Solo/Duo");
		flexRank = new SummonerRank("Flex");
		matchType = "All";
		recentMatchSummary = new RecentMatchSummary();
		matchHistoryList = new Vector<String>();
		matchHistory = new Vector<LoLMatch>();
		//Only saving the user's top 12 most played champions. 
		recentChampions = new Vector<Champion>();
		apiError = "";
	}


	public long getUniqueId() {
		return id;
	}


	public void setUniqueId(long uniqueId) {
		this.id = uniqueId;
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

	    //  SOLO / FLEX  getter/setters
	public void setSoloRank(SummonerRank soloRank) {
		this.soloRank = soloRank;
	}
	public void setFlexRank(SummonerRank flexRank) {
		this.flexRank = flexRank;
	}
	
	public SummonerRank getSoloRank() {
		return soloRank;
	}
	public SummonerRank getFlexRank() {
		return flexRank;
	}
	
	
	    
	    //Start MATCH HISTORY getter/setters
	    
		public String getMatchType() {
		return matchType;
	}


	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}


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


		public List<LoLMatch> getMatchHistory() {
			return matchHistory;
		}


		public void setMatchHistory(List<LoLMatch> matchHistory) {
			this.matchHistory = matchHistory;
		}


		public List<Champion> getRecentChampions() {
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
