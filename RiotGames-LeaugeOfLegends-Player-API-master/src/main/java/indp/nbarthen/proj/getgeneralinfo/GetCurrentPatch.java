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

public class GetCurrentPatch {
	
	public static PlayerAcc currentPatch(PlayerAcc summoner) throws JsonMappingException, JsonProcessingException {
		try {
			
			String currPatchUrl = "https://ddragon.leagueoflegends.com/api/versions.json";
			RestTemplate currPatchRestTemplate = new RestTemplate();
			
			String currPatchResponse = currPatchRestTemplate.getForObject(currPatchUrl, String.class);
			
			ObjectMapper patchUrlMapper = new ObjectMapper();
			
			JsonNode currPatchRoot = patchUrlMapper.readTree(currPatchResponse.toString());

			
			summoner.setCurrPatch(currPatchRoot.get(0).asText());
			summoner.setIconUrl("http://ddragon.leagueoflegends.com/cdn/"+summoner.getCurrPatch()+"/img/profileicon/" + summoner.getIconId() +".png");
			
			return summoner;
			
		}  catch (Exception e) {
		    // code to handle any other exceptions goes here
			System.out.println(e);
			summoner.setApiError("Error: Error Getting Patch Name.");
			return summoner;
		}

	}




}
