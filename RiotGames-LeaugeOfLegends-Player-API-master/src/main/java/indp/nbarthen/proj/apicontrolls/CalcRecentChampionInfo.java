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

import indp.nbarthen.proj.repository.Champion;
import indp.nbarthen.proj.repository.LoLMatch;
import indp.nbarthen.proj.repository.PlayerAcc;
import indp.nbarthen.proj.repository.RecentMatchSummary;

public class CalcRecentChampionInfo {
	
	public static Vector<Champion> calcRecentChampionInfo(PlayerAcc summoner) {
		Vector<Champion> champions = new Vector<Champion>();;
		
		//If user has a match history (not empty)
		if(!summoner.getMatchHistory().isEmpty()) {
			
			//Loop through every match and save champion data to champions vector.
			for(LoLMatch match : summoner.getMatchHistory()) {

				int wins = 0;
				int losses = 0;
				
				int kills = 0;
				int deaths= 0;
				int assists = 0;
				
				//Store win / loss
				if( match.getWinLoss().contains("Victory") ) {
					wins = wins + 1;
				}
				else {
					losses = losses + 1;
				}
				
				//Store kills
				kills = match.getSummonerKills();
				deaths = match.getSummonerDeaths();
				assists = match.getSummonerAssists();
				
				
				//Loop through champions Vector to see if champion already exists.
				boolean championAlreadyExists = false;
				for(Champion champion : champions) {
					//If champion already exists in champion vector.
					if( champion.getSummonerChampionName().contains(match.getSummonerChampionName()) ) {
						//Add match data to existing champion
						Vector<String> allMatchIds = champion.getMatchIds();
						allMatchIds.add(match.getMatchId());
						champion.setMatchIds( allMatchIds );
						
						champion.setWins( (champion.getWins() + wins) );
						champion.setLosses( (champion.getLosses() + losses) );
						champion.setKills( (champion.getKills() + kills ) );
						champion.setDeaths( (champion.getDeaths() + deaths) );
						champion.setAssists( (champion.getAssists() + assists) );
						
						championAlreadyExists = true;
					}
				}
				
				//Champion does not already exists in vector. Add a new champion to champions vector
				if(!championAlreadyExists) {
					Champion newChamp = new Champion();
					
					newChamp.setAccId(summoner.getAccId());
					
					Vector<String> matchIds = new Vector<String>();
					matchIds.add(match.getMatchId());
					newChamp.setMatchIds(matchIds);
					
					newChamp.setSummonerChampionId(match.getSummonerChampionId());
					newChamp.setSummonerChampionName(match.getSummonerChampionName());
					newChamp.setSummonerChampionUrl(match.getSummonerChampionUrl());
					newChamp.setWins(wins);
					newChamp.setLosses(losses);
					newChamp.setKills(kills);
					newChamp.setDeaths(deaths);
					newChamp.setAssists(assists);
					newChamp.setChampionExists("true");
					
					champions.add(newChamp);
				}
			}
			
			//Loop through champions and add summary data (games played, win rate, kda, kd)
			for(Champion champion: champions) {
			
				String strWinRate;
				String strAvgKills;
				String strAvgDeaths;
				String strAvgAssists;
				String strAvgRoundedKd;
				
				champion.setGamesPlayed( (champion.getWins() + champion.getLosses()) );
				
				double winrate = (double) champion.getWins() / (champion.getWins() + champion.getLosses()) * 100;
				//round to first decimal
				winrate = Math.round(winrate * 10) / 10.0;
				if (winrate % 1 == 0) {
					strWinRate = String.valueOf((int) winrate);
				}
				else {
					strWinRate = String.valueOf(winrate);
				}
				
				//Calc average KDA
				//Kills
				double avgKills = (double) champion.getKills() / champion.getGamesPlayed();
				//round to first decimal
				avgKills = Math.round(avgKills * 10) / 10.0;
				//If's omit decimal if it is a whole number.
				if (avgKills % 1 == 0) {
					strAvgKills = String.valueOf((int) avgKills);
				}
				else {
					strAvgKills = String.valueOf(avgKills);
				}
				
				//Deaths
				double avgDeaths = (double) champion.getDeaths() / champion.getGamesPlayed();
				//round to first decimal
				avgDeaths = Math.round(avgDeaths * 10) / 10.0;
				if (avgDeaths % 1 == 0) {
					strAvgDeaths = String.valueOf((int) avgDeaths);
				}
				else {
					strAvgDeaths = String.valueOf(avgDeaths);
				}
				
				//Assists
				double avgAssists = (double) champion.getAssists() / champion.getGamesPlayed();
				//round to first decimal
				avgAssists = Math.round(avgAssists * 10) / 10.0;
				//If's omit decimal if it is a whole number.
				if (avgAssists % 1 == 0) {
					strAvgAssists = String.valueOf((int) avgAssists);
				}
				else {
					strAvgAssists = String.valueOf(avgAssists);
				}
				
				//Calc kd
				int deaths = champion.getDeaths();
				if(champion.getDeaths() == 0) {
					deaths = 1;
				}
				double summonerKd = (champion.getKills() + champion.getAssists()) / (double) deaths;
				double roundedKd = Math.round(summonerKd * 10) / 10.0;
				//If's omit decimal if it is a whole number.
				if (roundedKd % 1 == 0) {
					strAvgRoundedKd = String.valueOf((int) roundedKd);
				}
				else {
					strAvgRoundedKd = String.valueOf(roundedKd);
				}
				
				//If's omit decimal if it is a whole number.
				
				champion.setWinRate(strWinRate);
				champion.setKda( strAvgKills + " - " + strAvgDeaths + " - " + strAvgAssists );
				champion.setKd( strAvgRoundedKd );
			}
			
			
		}
		
		//Sort champions array based on total games played (secondary criteria is wins. Third criteria is kda)
		champions.sort((champ1, champ2) -> {
			int compare = Integer.compare(champ2.getGamesPlayed(), champ1.getGamesPlayed());
			if (compare == 0) {
				compare = Integer.compare(champ2.getWins(), champ1.getWins());
				if (compare == 0) {
					double kda1 = (champ1.getKills() + champ1.getAssists()) / (double) champ1.getDeaths();
					double kda2 = (champ2.getKills() + champ2.getAssists()) / (double) champ2.getDeaths();
					compare = Double.compare(kda2, kda1);
				}
			}
			return compare;
			});
		
		
		//If less than 12 champions total were played. add empty champions (used in html for displaying info)
		while(champions.size() < 12) {
			Champion emptyChamp = new Champion();
			champions.add(emptyChamp);
		}
		champions.sort((champ1, champ2) -> Integer.compare(champ2.getGamesPlayed(), champ1.getGamesPlayed()));
		
		
		return champions;

	}






}
