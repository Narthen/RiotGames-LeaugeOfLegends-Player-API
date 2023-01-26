package indp.nbarthen.proj.calculations;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.cdimascio.dotenv.Dotenv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indp.nbarthen.proj.repository.LoLMatch;
import indp.nbarthen.proj.repository.PlayerAcc;
import indp.nbarthen.proj.repository.RecentMatchSummary;

public class CalcRecentMatchInfo {
	
	public static RecentMatchSummary calcRecentMatchInfo(PlayerAcc summoner) {
		RecentMatchSummary summary = new RecentMatchSummary();
		
		//If user has a match history (not empty)
		if(!summoner.getMatchHistory().isEmpty()) {
			int wins = 0;
			int losses = 0;
			
			int kills = 0;
			int deaths= 0;
			int assists = 0;
			
			int blindGames = 0;
			int draftGames = 0;
			int rankedGames = 0;
			int flexGames = 0;
			int aramGames = 0;
			int otherGames = 0;
			
			
			for(LoLMatch match : summoner.getMatchHistory()) {
				//Store win / loss
				if( match.getWinLoss().contains("Victory") ) {
					wins++;
				}
				else {
					losses++;
				}
				
				//Store kills
				kills = kills + match.getSummonerKills();
				deaths = deaths + match.getSummonerDeaths();
				assists = assists + match.getSummonerAssists();
				
				//Store gamemodes
				if(match.getQueueName().contains("Blind Pick")) {
					blindGames++;
				}
				else if(match.getQueueName().contains("Draft Pick")) {
					draftGames++;
				}
				else if(match.getQueueName().contains("Ranked Solo/Duo")) {
					rankedGames++;
				}
				else if(match.getQueueName().contains("Flex Ranked")) {
					flexGames++;
				}
				else if(match.getQueueName().contains("Aram")) {
					aramGames++;
				}
				else {
					otherGames++;
				}
				
			}
			
			
			
			//Calc win rate
			String strWinRate;
			String strAvgKills;
			String strAvgDeaths;
			String strAvgAssists;
			String strAvgRoundedKd;
			
			double winrate = (double) wins / (wins + losses) * 100;
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
			double avgKills = (double) kills / summoner.getMatchHistory().size();
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
			double avgDeaths = (double) deaths / summoner.getMatchHistory().size();
			//round to first decimal
			avgDeaths = Math.round(avgDeaths * 10) / 10.0;
			//If's omit decimal if it is a whole number.
			if (avgDeaths % 1 == 0) {
				strAvgDeaths = String.valueOf((int) avgDeaths);
			}
			else {
				strAvgDeaths = String.valueOf(avgDeaths);
			}
			
			//Assists
			double avgAssists = (double) assists / summoner.getMatchHistory().size();
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
			int newDeaths = deaths;
			if(deaths == 0) {
				newDeaths = 1;
			}
			double summonerKd = (kills + assists) / (double) newDeaths;
			double roundedKd = Math.round(summonerKd * 10) / 10.0;
			if (roundedKd % 1 == 0) {
				strAvgRoundedKd = String.valueOf((int) roundedKd);
			}
			else {
				strAvgRoundedKd = String.valueOf(roundedKd);
			}
			
			
			
			
			//Store Values
			summary.setWins(wins);
			summary.setLosses(losses);
			summary.setBlindGames(blindGames);
			summary.setDraftGames(draftGames);
			summary.setRankedGames(rankedGames);
			summary.setFlexGames(flexGames);
			summary.setAramGames(aramGames);
			summary.setOtherGames(otherGames);
			
			
			
			summary.setWinRate(strWinRate);
			summary.setKda( strAvgKills + " - " + strAvgDeaths + " - " + strAvgAssists );
			summary.setKd( strAvgRoundedKd );
		}
		
		
		return summary;

	}






}
