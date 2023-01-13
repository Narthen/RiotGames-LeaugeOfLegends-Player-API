package indp.nbarthen.proj.apicontrolls;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indp.nbarthen.proj.repository.PlayerAcc;

public class SetSoloRank {
	//Reads the json provided to set the Summoner's Solo/Duo Rank information
	public static PlayerAcc setSoloRankInfo(PlayerAcc summoner, String accId, JsonNode rankInfoRoot, int index) {
		summoner.setSoloRankAccId(accId);
		summoner.setSoloRankQueueType("Solo/Duo");
		summoner.setSoloRankLeagueId(rankInfoRoot.get(index).get("leagueId").asText());	
		summoner.setSoloRankTier(rankInfoRoot.get(index).get("tier").asText());	
		summoner.setSoloRankRank(rankInfoRoot.get(index).get("rank").asText());	
		summoner.setSoloRankSummonerId(rankInfoRoot.get(index).get("summonerId").asText());		
		summoner.setSoloRankSummonerName(rankInfoRoot.get(index).get("summonerName").asText());	
		summoner.setSoloRankLeaguePoints(rankInfoRoot.get(index).get("leaguePoints").asInt());
		summoner.setSoloRankWins(rankInfoRoot.get(index).get("wins").asInt());
		summoner.setSoloRankLosses(rankInfoRoot.get(index).get("losses").asInt());	
		summoner.setSoloRankSummonerId(rankInfoRoot.get(index).get("summonerId").asText());
				
		return summoner;

	}






}
