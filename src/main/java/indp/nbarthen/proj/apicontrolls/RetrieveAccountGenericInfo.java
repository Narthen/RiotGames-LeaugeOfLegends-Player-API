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

public class RetrieveAccountGenericInfo {
	
	public static PlayerAcc retrieveAccountGenericInfo(PlayerAcc summoner, String summonerName) throws JsonMappingException, JsonProcessingException {
		try {
			Dotenv dotenv = Dotenv.load();
			String apiKey = dotenv.get("API_KEY");			
			String genericInfoUrl = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key="+ apiKey;	
			
			RestTemplate genericRestTemplate = new RestTemplate();
			
			
			String genericResponse = genericRestTemplate.getForObject(genericInfoUrl, String.class);
			
			ObjectMapper genericMapper = new ObjectMapper();
			ObjectMapper patchUrlMapper = new ObjectMapper();
			
			JsonNode genericInfoRoot = genericMapper.readTree(genericResponse.toString());
			
			summoner.setAccId(genericInfoRoot.path("accountId").asText());
			summoner.setSummonerId(genericInfoRoot.path("id").asText());
			summoner.setAccName(genericInfoRoot.path("name").asText());
			summoner.setPuuid(genericInfoRoot.path("puuid").asText());
			summoner.setIconId(genericInfoRoot.path("profileIconId").asInt());
			summoner.setSummonerLevel(genericInfoRoot.path("summonerLevel").asInt());
			
		
			summoner.setIconUrl("http://ddragon.leagueoflegends.com/cdn/"+summoner.getCurrPatch()+"/img/profileicon/" + summoner.getIconId() +".png");
			
			return summoner;
			
		} catch (HttpClientErrorException e) {
		    //Error 404 (summoner name not found)  OR  invalid api_key
			summoner.setAccId("404 Error - Invalid summoner name or api_key");
			return summoner;
		} catch (Exception e) {
		    // code to handle any other exceptions goes here
			summoner.setAccId("Error");
			return summoner;
		}

	}






}
