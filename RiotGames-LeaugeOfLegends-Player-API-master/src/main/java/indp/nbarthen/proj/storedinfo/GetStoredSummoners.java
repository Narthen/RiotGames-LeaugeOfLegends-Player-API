package indp.nbarthen.proj.storedinfo;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import indp.nbarthen.proj.repository.PlayerAcc;

public class GetStoredSummoners {
	//Reads the json provided to set the Summoner's Solo/Duo Rank information
	public static Vector<PlayerAcc> getAllStoredSummoners() {
		Vector<PlayerAcc> allStoredAccs = new Vector<PlayerAcc>();
		
		File file = new File("Stored-Summoners.json");
		if (file.length() != 0) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				allStoredAccs = mapper.readValue(file,
			    mapper.getTypeFactory().constructCollectionType(Vector.class, PlayerAcc.class));
			    
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		
		return allStoredAccs;

	}






}
