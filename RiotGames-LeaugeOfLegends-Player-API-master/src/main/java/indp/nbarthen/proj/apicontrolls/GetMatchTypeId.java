package indp.nbarthen.proj.apicontrolls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import indp.nbarthen.proj.repository.PlayerAcc;

public class GetMatchTypeId {
	public static String getId(String matchType) {
		//Use this link to check matchType ids if out-dated (https://static.developer.riotgames.com/docs/lol/queues.json)
		String matchTypeId = "420";
		if(matchType.contains("Blind")) {
			matchTypeId = "430";
		}
		else if(matchType.contains("Draft")) {
			matchTypeId = "400";
		}
		else if(matchType.contains("Ranked")) {
			matchTypeId = "420";
		}
		else if(matchType.contains("Flex")) {
			matchTypeId = "440";
		}
		else if(matchType.contains("ARAM")) {
			matchTypeId = "450";
		}
		
		return matchTypeId;
	}
}
