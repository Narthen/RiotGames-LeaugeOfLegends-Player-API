package indp.nbarthen.proj.getmatchhistory;

import java.util.Vector;

import org.springframework.http.HttpStatus;
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
		PlayerAcc unchangedSummoner = summoner;
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
				end = 10;
			}
			
			String matchHistListUrl;
			
			if(matchType.contains("All")) {
				matchHistListUrl = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summoner.getPuuid() + "/ids?start="+ start + "&count="+ end +"&api_key=" + apiKey ;
			}
			//Select only a certain queue type.
			else {
				matchHistListUrl = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summoner.getPuuid() + "/ids?queue="+GetMatchTypeId.getId(summoner.getMatchType())+"&start="+ start + "&count="+ end +"&api_key=" + apiKey;
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
			
			return ParallelGetMatchHistory.parallellMatchHistory(summoner, gameIds, loadMore, summoner.getMatchType());
			
		} catch (Exception e) {
			//Catch possible API request errors + reasons.
			 if (e.toString().contains("\"status_code\":400")) {
				 System.out.println("Error getting match history information array. Reasons - 'syntax error in the request and the request has therefore been denied'");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Syntax Error - Invalid Summoner. Retry.");
				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":401")) {
				 System.out.println("Error getting match history information array. Reason - API Key denied");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: API Key denied.");

				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":403")) {
				 System.out.println("Error getting match history information array. Reason - server understood the request but refuses to authorize it");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Server refused request. Retry or fix.");

				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":404")) {
				 System.out.println("Error getting match history information array. Reason - Summoner was likely not found. Check summoner ID used.");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Error getting summoner namn/id. Retry or fix.");

				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":415")) {
				 System.out.println("Error getting match history information array. Reasons: Unsupported Media Type. Content-Type header was not appropriately set.");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error - Unsupported Media Type. Retry or fix.");

				 return unchangedSummoner; 
			 } 
			 else if (e.toString().contains("HTTP response code: 429")) {
				 System.out.println("Error getting match history information array. Rate Limit Exceeded. Wait a few seconds and retry request.");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Too many requests. Wait a few seconds and retry.");

				 return unchangedSummoner;
			 } 
			else {
			    // code to handle any other exceptions goes here
				System.out.println("Error getting match history information array");
				System.out.println(e);
				summoner.setApiError("Error: Error getting match history information. Retry");
				return summoner;
			}
		}


	}






}
