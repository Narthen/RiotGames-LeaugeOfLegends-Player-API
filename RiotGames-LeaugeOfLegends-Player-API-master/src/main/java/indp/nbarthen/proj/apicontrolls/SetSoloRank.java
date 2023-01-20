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
import indp.nbarthen.proj.repository.SummonerRank;

public class SetSoloRank {
	//Reads the json provided to set the Summoner's Solo/Duo Rank information
	public static PlayerAcc setSoloRankInfo(PlayerAcc summoner, String accId, JsonNode rankInfoRoot, int index) {
		SummonerRank soloInfo = new SummonerRank();
		soloInfo.setAccId(accId);
		soloInfo.setQueueType("Solo/Duo");
		soloInfo.setLeagueId(rankInfoRoot.get(index).get("leagueId").asText());	
		soloInfo.setTier(rankInfoRoot.get(index).get("tier").asText());	
		soloInfo.setRank(rankInfoRoot.get(index).get("rank").asText());	
		soloInfo.setSummonerId(rankInfoRoot.get(index).get("summonerId").asText());		
		soloInfo.setSummonerName(rankInfoRoot.get(index).get("summonerName").asText());	
		soloInfo.setLeaguePoints(rankInfoRoot.get(index).get("leaguePoints").asInt());
		soloInfo.setWins(rankInfoRoot.get(index).get("wins").asInt());
		soloInfo.setLosses(rankInfoRoot.get(index).get("losses").asInt());	
				
		summoner.setSoloRank(soloInfo);	
		
		return summoner;

	}






}
