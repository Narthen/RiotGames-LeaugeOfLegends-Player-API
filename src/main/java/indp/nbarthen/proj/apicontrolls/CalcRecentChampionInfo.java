package indp.nbarthen.proj.apicontrolls;

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
	//Reads the json provided to set the Summoner's Flex Rank information
	
	public static RecentMatchSummary calcRecentMatchInfo(PlayerAcc summoner) {
		RecentMatchSummary summary = new RecentMatchSummary();
		Champion[] champions = new Champion[12];
		
		//If user has a match history (not empty)
		if(!summoner.getMatchHistory().isEmpty()) {
			
			for(LoLMatch match : summoner.getMatchHistory()) {

				int summonerChampionId = 0;
				String summonerChampionName = "";
				int wins = 0;
				int losses = 0;
				int gamesPlayed = 0;
				
				int kills = 0;
				int deaths= 0;
				int assists = 0;
				
				String winRate = "0";
				String kda = "0-0-0";
				String kd = "0.0";
				String championExists = "false";
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
				
				
				
			}
			
			
			/*
			 
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
			if (avgAssists % 1 == 0) {
				strAvgAssists = String.valueOf((int) avgAssists);
			}
			else {
				strAvgAssists = String.valueOf(avgAssists);
			}
			
			//Calc kd
			double summonerKd = (kills + assists) / (double) deaths;
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
			
			//If's omit decimal if it is a whole number.
			
			summary.setWinRate(strWinRate);
			summary.setKda( strAvgKills + " - " + strAvgDeaths + " - " + strAvgAssists );
			summary.setKd( strAvgRoundedKd );
			*/
		}
		summary.setAccId(summoner.getAccId());
		
		
		return summary;

	}






}
