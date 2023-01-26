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

public class GetRankInfo {
	
	public static PlayerAcc rankInformation(PlayerAcc summoner, String accId) throws JsonMappingException, JsonProcessingException {
		try {
			Dotenv dotenv = Dotenv.load();
			String apiKey = dotenv.get("API_KEY");		
			
			String rankInfoUrl = "https://na1.api.riotgames.com/lol/league/v4/entries/by-summoner/"+ summoner.getSummonerId() +"?api_key=" + apiKey ;
			RestTemplate  rankInfoRestTemplate = new RestTemplate();
			
			String rankInfoResponse = rankInfoRestTemplate.getForObject(rankInfoUrl, String.class);
			
			ObjectMapper  rankInfoMapper = new ObjectMapper();
			
			JsonNode rankInfoRoot = rankInfoMapper.readTree(rankInfoResponse.toString());

			//Summoner is not ranked (returns nothing [])
			if(rankInfoRoot.isEmpty()) {
				summoner.setSoloRanked(false);
				summoner.setFlexRanked(false);
			}
			else {
				//If the summoner is ranked in both solo/duo & flex json returns array of 2
				if(rankInfoRoot.size() == 2) {
					//If the first index's 'queueType' is Solo, use index 0 for soloRank and index 1 for flexRank.
					if(rankInfoRoot.get(0).get("queueType").asText().equals("RANKED_SOLO_5x5")) {
						summoner = SetSoloRank.setSoloRankInfo(summoner, accId, rankInfoRoot, 0);
						summoner = SetFlexRank.setFlexRankInfo(summoner, accId, rankInfoRoot, 1);
					}
					else {
						summoner = SetSoloRank.setSoloRankInfo(summoner, accId, rankInfoRoot, 1);
						summoner = SetFlexRank.setFlexRankInfo(summoner, accId, rankInfoRoot, 0);
					}
				}
				//Summoner is only ranked in one mode (solo/duo OR flex)
				else {
					if( rankInfoRoot.get(0).get("queueType").asText().equals("RANKED_SOLO_5x5")) {
						summoner = SetSoloRank.setSoloRankInfo(summoner, accId, rankInfoRoot, 0);
					}
					else {
						summoner = SetFlexRank.setFlexRankInfo(summoner, accId, rankInfoRoot, 0);
					}
					
				}
			}
			return summoner;
			
		}  catch (Exception e) {
		    // code to handle any other exceptions goes here
			System.out.println("Error getting ranked information");
			System.out.println(e);
			summoner.setAccId("Error: Error getting ranked information.");
			return summoner;
		}

	}






}
