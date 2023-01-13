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

public class SetFlexRank {
	//Reads the json provided to set the Summoner's Flex Rank information
	
	public static PlayerAcc setFlexRankInfo(PlayerAcc summoner, String accId, JsonNode rankInfoRoot, int index) {
		summoner.setFlexRankAccId(accId);
		summoner.setFlexRankQueueType("Flex");
		summoner.setFlexRankLeagueId(rankInfoRoot.get(index).get("leagueId").asText());	
		summoner.setFlexRankTier(rankInfoRoot.get(index).get("tier").asText());	
		summoner.setFlexRankRank(rankInfoRoot.get(index).get("rank").asText());	
		summoner.setFlexRankSummonerId(rankInfoRoot.get(index).get("summonerId").asText());		
		summoner.setFlexRankSummonerName(rankInfoRoot.get(index).get("summonerName").asText());	
		summoner.setFlexRankLeaguePoints(rankInfoRoot.get(index).get("leaguePoints").asInt());
		summoner.setFlexRankWins(rankInfoRoot.get(index).get("wins").asInt());
		summoner.setFlexRankLosses(rankInfoRoot.get(index).get("losses").asInt());	
		summoner.setFlexRankSummonerId(rankInfoRoot.get(index).get("summonerId").asText());
				
		return summoner;

	}






}
