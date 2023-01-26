package indp.nbarthen.proj.storedinfo;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import indp.nbarthen.proj.repository.PlayerAcc;

/* Contains Functions: 
 * storeSummonerToFile(PlayerAcc summoner) - Added passed summoner to database / file.
 * deletePassedSummoner(PlayerAcc delSummoner) - Summoner to be deleted from database / file.
 */

public class StoreSummoner {
	
	//Added passed summoner to database / file
	public static void storeSummonerToFile(PlayerAcc summoner) {
		
		if(summoner.getAccId() != null) {
			File file = new File("Stored-Summoners.json");
			//If file is empty, make new list, add summoner, save.
		    if (file.length() == 0) {
		        // Create a new list with the new summoner and write it to the file
		        List<PlayerAcc> summoners = List.of(summoner);
		        ObjectMapper mapper = new ObjectMapper();
		        try {
		            mapper.writeValue(file, summoners);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    } 
		  //File is NOT empty, make list containing stored summoners, add summoner, save.
		    else {
		        // Read the existing summoners from the file
		        ObjectMapper mapper = new ObjectMapper();
		        try {
		            List<PlayerAcc> existingSummoners = mapper.readValue(file,
		                mapper.getTypeFactory().constructCollectionType(List.class, PlayerAcc.class));
		            // Add the new summoner to the list
		            boolean exactSummonerAlreadyExists = false;
		            for(PlayerAcc summ : existingSummoners) {
		            	//Checks to see if summoner already exsists. Does this by checking if the names are equal. And if the first and last games' unix date.
		            	if(summ.getAccName().contains(summoner.getAccName()) 
		            		&& summ.getMatchHistory().get(summ.getMatchHistory().size() - 1).getGameEndTimestampUnix() == summoner.getMatchHistory().get(summoner.getMatchHistory().size() - 1).getGameEndTimestampUnix()
		            		&& summ.getMatchHistory().get(0).getGameEndTimestampUnix() == summoner.getMatchHistory().get(0).getGameEndTimestampUnix()) 
		            	{
		            		System.out.println("Exact summoner already added.");
		            		exactSummonerAlreadyExists = true;
		            	}
		            }
		            if(!exactSummonerAlreadyExists) {
		            	existingSummoners.add(summoner);
		            }
		            // Write the updated list to the file
		            mapper.writeValue(file, existingSummoners);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
			
				
		}

	}

	
	//Deletes passed summoner from database / file.
	public static void deletePassedSummoner(String accName, long newestGameUnixTime, long oldestGameUnixTime) {
		File file = new File("Stored-Summoners.json");	
		
		 ObjectMapper mapper = new ObjectMapper();
	        try {
	            List<PlayerAcc> existingSummoners = mapper.readValue(file,
	                mapper.getTypeFactory().constructCollectionType(List.class, PlayerAcc.class));
	            
	            //Removed desired summoner from the list
	            for(PlayerAcc summ : existingSummoners) {
	            	//If current summ matches desired summoner
	            	if(summ.getAccName().contains(accName) 
	            		&& summ.getMatchHistory().get(summ.getMatchHistory().size() - 1).getGameEndTimestampUnix() == oldestGameUnixTime
	            		&& summ.getMatchHistory().get(0).getGameEndTimestampUnix() == newestGameUnixTime) 
	            	{
	            		existingSummoners.remove(summ);
	            		break;
	            	}
	            }
	            
	            if(existingSummoners.isEmpty()) {
	            	file.delete();
	            }
	            else {
		            // Write the updated list to the file
		            mapper.writeValue(file, existingSummoners);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
				
				
	}

	
	//Finds the fetched summoner from database / file.
		public static PlayerAcc fetchSelectedSummoner(String accName, long newestGameUnixTime, long oldestGameUnixTime) {
			File file = new File("Stored-Summoners.json");	
			
			 ObjectMapper mapper = new ObjectMapper();
		        try {
		            List<PlayerAcc> existingSummoners = mapper.readValue(file,
		                mapper.getTypeFactory().constructCollectionType(List.class, PlayerAcc.class));
		            
		            //Find desired summoner from the list
		            for(PlayerAcc summ : existingSummoners) {
		            	//If current summ matches desired summoner
		            	if(summ.getAccName().contains(accName) 
		            		&& summ.getMatchHistory().get(summ.getMatchHistory().size() - 1).getGameEndTimestampUnix() == oldestGameUnixTime
		            		&& summ.getMatchHistory().get(0).getGameEndTimestampUnix() == newestGameUnixTime) 
		            	{
		            		return summ;
		            		
		            	}
		            }
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
					
			PlayerAcc summoner = new PlayerAcc();
			//If summoner is not found, return empty summoner.
			summoner.setAccId("Summoner not found");
			return summoner;
		}


}
