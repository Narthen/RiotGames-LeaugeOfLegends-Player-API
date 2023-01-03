package indp.nbarthen.proj.apicontrolls;

import java.util.Vector;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indp.nbarthen.proj.repository.LoLMatch;
import indp.nbarthen.proj.repository.Participant;
import indp.nbarthen.proj.repository.PlayerAcc;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMatchHistory {
	
	public static PlayerAcc matchHistory(PlayerAcc summoner, String matchType, boolean loadMore) throws JsonMappingException, JsonProcessingException {
		try {
			Dotenv dotenv = Dotenv.load();
			String apiKey = dotenv.get("API_KEY");		
			
			int start;
			int end;
			//Get most recent 20 games
			if(!loadMore) {
				start = 0;
				end = 20;
			}
			else {
				//Get 10 more games from match history of current queue type.
				start = summoner.getMatchHistoryList().size();
				end = start+10;
			}
			
			String matchHistListUrl;
			
			if(matchType.contains("All")) {
				matchHistListUrl = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summoner.getPuuid() + "/ids?start="+ start + "&count="+ end +"&api_key=" + apiKey ;
			}
			//Select only a certain queue type.
			else {
				matchHistListUrl = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summoner.getPuuid() + "/ids?queue="+GetMatchTypeId.getId(matchType)+"&start="+ start + "&count="+ end +"&api_key=" + apiKey;
			}
			RestTemplate  matchHistRestTemplate = new RestTemplate();
			String matchHistResponse = matchHistRestTemplate.getForObject(matchHistListUrl, String.class);
			ObjectMapper  matchHistMapper = new ObjectMapper();
			JsonNode matchHistInfoRoot = matchHistMapper.readTree(matchHistResponse.toString());
			
			//Take the array of matchHistory id's and stores them in a vector. Saves vector to summoner.setMatchHistoryList();
			Vector<String> gameIds = new Vector<String>();
			for(JsonNode gameIdNode : matchHistInfoRoot) {
				  String gameId = gameIdNode.asText();
				  gameIds.add(gameId);
			}
			
			//Save gameIds to summoner
			
			if(loadMore == true) {
				Vector<String> allGameIds = summoner.getMatchHistoryList();
				allGameIds.addAll(gameIds);
				summoner.setMatchHistoryList(allGameIds);
			}
			else {
				summoner.setMatchHistoryList(gameIds);
			}
			
			return ParallelGetMatchHistory.parallellMatchHistory(summoner, gameIds, loadMore);
			
		}  catch (Exception e) {
		    // code to handle any other exceptions goes here
			System.out.println("Error getting match history information");
			System.out.println(e);
			summoner.setAccId("Error");
			return summoner;
		}

	}






}
