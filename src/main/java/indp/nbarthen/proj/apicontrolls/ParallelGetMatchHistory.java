package indp.nbarthen.proj.apicontrolls;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

public class ParallelGetMatchHistory {
	//Will go through every game in gameIds array. This is done in parallel to decrease load times. 
	public static PlayerAcc parallellMatchHistory(PlayerAcc summoner, Vector<String> gameIds, boolean loadMore) throws JsonMappingException, JsonProcessingException {
		PlayerAcc unchangedSummoner = summoner;
		try {
			
			Dotenv dotenv = Dotenv.load();
			String apiKey = dotenv.get("API_KEY");		
			
			Vector<LoLMatch> matches = new Vector<LoLMatch>();
			
			// Create a fixed thread pool with a given number of threads
			ExecutorService executor = Executors.newFixedThreadPool(10);
			// Create a list of tasks to be executed concurrently
			List<Callable<LoLMatch>> tasks = new ArrayList<>();
			
			//Loop though array
			for(String gameId : gameIds) {
			 //Add task to be run in parallel
			 tasks.add(() -> {
				//Get API Json
				 String matchHistGameUrl = "https://americas.api.riotgames.com/lol/match/v5/matches/" + gameId + "?api_key=" + apiKey;

			    // Create an HTTP connection
			    URL url = new URL(matchHistGameUrl);
			    HttpURLConnection con = (HttpURLConnection) url.openConnection();
			    con.setRequestMethod("GET");

			    // Read the response
			    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			    String inputLine;
			    StringBuilder content = new StringBuilder();
			    while ((inputLine = in.readLine()) != null) {
			        content.append(inputLine);
			    }
			    in.close();

			    // Store the JSON response as a String
			    String matchHistGameResponse = content.toString();

			    // Parse the JSON response using an ObjectMapper
			    ObjectMapper matchHistGameMapper = new ObjectMapper();
			    JsonNode gameRoot = matchHistGameMapper.readTree(matchHistGameResponse);
				
			    
			    
				//Set values for LoLMatch from API's Json 
				LoLMatch match = new LoLMatch();
				
				//Stores the generic information about the match.
				match.setMatchId(gameRoot.path("metadata").path("matchId").asText());
				match.setGameDuration(gameRoot.path("info").path("gameDuration").asLong());
				//Converts Unix timestamp to real date in the setter method
				match.setGameEndTimestamp(gameRoot.path("info").path("gameEndTimestamp").asLong());
			
				//Stores gamemode (id)
				match.setQueueId(gameRoot.path("info").path("queueId").asInt());
				//Calls GetGameModeName class which calls the gameMode json to get the gamemode Name (QueueName)
				match.setQueueName(GetGameModeName.findGameModeName(match.getQueueId()));
				
				//Gets data for all participants in the game
				Vector<Participant> participants = new Vector<Participant>();
				
				JsonNode info = gameRoot.get("info");
				JsonNode players = info.get("participants");
				//Loops through all 10 participants (summoners) in the Riot API. Sets needed values.
				for (JsonNode player : players) {
					//If current participant is the main summoner (save additional information).
				    if( player.get("summonerName").asText().contains( summoner.getAccName()) ) {
				    	match.setSummonerAccName(player.get("summonerName").asText());
				    	match.setSummonerChampionId(player.get("championId").asInt());
				    	match.setSummonerChampionName(player.get("championName").asText());
				    	match.setSummonerChampionUrl(summoner.getCurrPatch());
				    	match.setSummonerKills(player.get("kills").asInt());
				    	match.setSummonerDeaths(player.get("deaths").asInt());
				    	match.setSummonerAssists(player.get("assists").asInt());
				    	match.setSummonerTotalMinionsKilled(player.get("totalMinionsKilled").asInt());
				    	
				    	//Stores win or loss
						if( player.get("win").asBoolean() ) {
							match.setWinLoss("Victory"); 
						}
						else {
							match.setWinLoss("Defeat"); 
						}
				    }
				   Participant participant = new Participant();
				   participant.setPuuid(player.get("puuid").asText());
				   participant.setAccName(player.get("summonerName").asText());
				   participant.setMapSide(player.get("teamId").asText());
				   participant.setChampionId(player.get("championId").asInt());
				   participant.setChampionName(player.get("championName").asText());
				   participant.setChampionUrl(summoner.getCurrPatch());
				   participant.setKills(player.get("kills").asInt());
				   participant.setDeaths(player.get("deaths").asInt());
				   participant.setAssists(player.get("assists").asInt());
				   participant.setGoldEarned(player.get("goldEarned").asInt());
				   participant.setTotalMinionsKilled(player.get("totalMinionsKilled").asInt());
				   //Add participant to the participants Vector
				   participants.add(participant);
				}
				//Add participants Vector to the current match
				match.setParticipants(participants);
				
				//Returns desired data for each match. (Does not exit parallellMatchHistory function). This is for task.add
				return match;
			 });
			}
			
			
			//Invoke all tasks concurrently and store the list of futures
			List<Future<LoLMatch>> futures = executor.invokeAll(tasks);

			//Iterate through the list of futures and get the result of each task (result = each match)
			for (Future<LoLMatch> future : futures) {
			    LoLMatch result = future.get();
			    
			    matches.add(result);
			}

			//Shut down the executor
			executor.shutdown();
			
			
			//Save the matches Vector to the summoners match history
			if(loadMore == true) {
				Vector<LoLMatch> allMatches = new Vector<LoLMatch>();
				allMatches = summoner.getMatchHistory();
				allMatches.addAll(matches);
				summoner.setMatchHistory(allMatches);
			}
			else {
				summoner.setMatchHistory(matches);
			}
			
			
			return summoner;
			
		}  catch (Exception e) {
			//Catch possible API request errors + reasons.
			 if (e.toString().contains("\"status_code\":400")) {
				 System.out.println("Error getting match history information. Reasons - 'syntax error in the request and the request has therefore been denied'");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Syntax Error - Invalid Summoner. Retry.");
				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":401")) {
				 System.out.println("Error getting match history information. Reason - API Key denied");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: API Key denied.");

				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":403")) {
				 System.out.println("Error getting match history information. Reason - server understood the request but refuses to authorize it");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Server refused request. Retry or fix.");

				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":404")) {
				 System.out.println("Error getting match history information. Reason - Summoner was likely not found. Check summoner ID used.");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Error getting summoner namn/id. Retry or fix.");

				 return unchangedSummoner;
			 } 
			 else if (e.toString().contains("\"status_code\":415")) {
				 System.out.println("Error getting match history information. Reasons: Unsupported Media Type. Content-Type header was not appropriately set.");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error - Unsupported Media Type. Retry or fix.");

				 return unchangedSummoner; 
			 } 
			 else if (e.toString().contains("HTTP response code: 429")) {
				 System.out.println("Error getting match history information. Rate Limit Exceeded. Wait a few seconds and retry request.");
				 System.out.println(e);
				 unchangedSummoner.setApiError("Error: Too many requests. Wait a few seconds and retry.");

				 return unchangedSummoner;
			 } 
			else {
			    // code to handle any other exceptions goes here
				System.out.println("Error getting match history information");
				System.out.println(e);
				summoner.setApiError("Error: Error getting match history information. Retry");
				return summoner;
			}
		}


	}






}
