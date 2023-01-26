package indp.nbarthen.proj.getgeneralinfo;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indp.nbarthen.proj.repository.PlayerAcc;
import indp.nbarthen.proj.repository.SummonerRank;

public class SetFlexRank {
	//Reads the json provided to set the Summoner's Flex Rank information
	
	public static PlayerAcc setFlexRankInfo(PlayerAcc summoner, String accId, JsonNode rankInfoRoot, int index) {
		SummonerRank flexInfo = new SummonerRank();
		flexInfo.setAccId(accId);
		flexInfo.setQueueType("Flex");
		flexInfo.setLeagueId(rankInfoRoot.get(index).get("leagueId").asText());	
		flexInfo.setTier(rankInfoRoot.get(index).get("tier").asText());	
		flexInfo.setRank(rankInfoRoot.get(index).get("rank").asText());	
		flexInfo.setSummonerId(rankInfoRoot.get(index).get("summonerId").asText());		
		flexInfo.setSummonerName(rankInfoRoot.get(index).get("summonerName").asText());	
		flexInfo.setLeaguePoints(rankInfoRoot.get(index).get("leaguePoints").asInt());
		flexInfo.setWins(rankInfoRoot.get(index).get("wins").asInt());
		flexInfo.setLosses(rankInfoRoot.get(index).get("losses").asInt());	
		
		summoner.setFlexRank(flexInfo);	
		
		return summoner;

	}






}
