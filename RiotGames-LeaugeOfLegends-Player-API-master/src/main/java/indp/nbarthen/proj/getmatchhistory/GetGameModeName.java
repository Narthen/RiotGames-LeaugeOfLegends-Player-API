package indp.nbarthen.proj.getmatchhistory;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indp.nbarthen.proj.repository.PlayerAcc;

public class GetGameModeName {
	
	public static String findGameModeName(int gamemodeId) throws JsonMappingException, JsonProcessingException {
		try {
			
			String gameModeUrl = "https://static.developer.riotgames.com/docs/lol/queues.json";
			RestTemplate gameModeRestTemplate = new RestTemplate();
			
			String gameModeResponse = gameModeRestTemplate.getForObject(gameModeUrl, String.class);
			
			ObjectMapper gameModeMapper = new ObjectMapper();
			
			JsonNode gameModeRoot = gameModeMapper.readTree(gameModeResponse.toString());

			String gamemode = "Unknown";
			for (JsonNode obj : gameModeRoot) {
			    if (obj.get("queueId").asInt() == gamemodeId) {
			        gamemode = obj.get("description").asText();
			        //rename certain gamemode names to be shorter
			        if(gamemode.contains("5v5 Draft Pick games")) {
			        	gamemode = "Draft Pick";
			        }
			        else if(gamemode.contains("5v5 Blind Pick games")) {
			        	gamemode = "Blind Pick";
			        }
					else if(gamemode.contains("5v5 Ranked Solo games")) {
						gamemode = "Ranked Solo/Duo";
					}
					else if(gamemode.contains("5v5 Ranked Flex games")) {
						gamemode = "Flex Ranked";
					}
					else if(gamemode.contains("5v5 ARAM games")) {
						gamemode = "Aram";
					}
			        
			        
			        return gamemode;
			    }
			}
			
			return gamemode;
			
		}  catch (Exception e) {
			System.out.println("Error getting gamemode Name");
		    // code to handle any other exceptions goes here
			return "Unknown";
		}

	}






}
