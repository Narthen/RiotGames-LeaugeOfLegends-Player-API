package indp.nbarthen.proj.controller;


import indp.nbarthen.proj.calculations.CalcRecentChampionInfo;
import indp.nbarthen.proj.calculations.CalcRecentMatchInfo;
import indp.nbarthen.proj.getgeneralinfo.GetCurrentPatch;
import indp.nbarthen.proj.getgeneralinfo.GetRankInfo;
import indp.nbarthen.proj.getgeneralinfo.RetrieveAccountGenericInfo;
import indp.nbarthen.proj.getmatchhistory.*;
import indp.nbarthen.proj.repository.LoLMatch;
import indp.nbarthen.proj.repository.PlayerAcc;
import indp.nbarthen.proj.repository.PlayerRepository;
import indp.nbarthen.proj.storedinfo.GetStoredSummoners;
import indp.nbarthen.proj.storedinfo.StoreSummoner;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.*;


import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;



@Controller
public class MainController {
	private PlayerRepository playerRepository;
	private String fetchInitialSummonerApiError;
	private String fetchSummError;
	
	public MainController(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
		fetchInitialSummonerApiError = "none";
		fetchSummError = "none";
	}
	
	 @RequestMapping({"/"})
	    public String homePage(Model model) throws JsonMappingException, JsonProcessingException {
		 	
		 	String popupError = "none";
		 	
		 	//If user was redirected after Summoner Search. Show popup error on webpage
		 	if(getFetchInitialSummonerApiError().contains("Error")) {
		 		popupError = getFetchInitialSummonerApiError();
		 		setFetchInitialSummonerApiError("none");
		 		model.addAttribute("popupError", popupError);
		 		return "searchPage";
		 	}
		 	
		 	model.addAttribute("popupError", popupError);
		 	
	        return "searchPage";
	    }
	 
	 @RequestMapping({"summoner/{summonerName}"})
	    public String getSummoner(@PathVariable("summonerName") String summonerName, @RequestParam(value = "matchType", required = false) String matchType, @RequestParam(value = "loadMore", required = false) String loadMore, @RequestParam(value = "summRandomId", required = false) String summUniqueId, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		 	
		 	PlayerAcc summoner = new PlayerAcc();
		 	String popupError = "none";
		 	
		 	
		 	
		 
		 	//If just loading additional match history games OR filtering for game type OR catching previous redirect Error.
		 	if(summUniqueId != null && playerRepository.findById(summUniqueId).get().getAccName().equals(summonerName)) {
		 		summoner = playerRepository.findById(summUniqueId).get();
		 
		 		//If API does not return all information (but summoner already exists (was redirect + show popup).
			 	if(summoner.getApiError().contains("Error")) {
			 		popupError = summoner.getApiError();
			 		model.addAttribute("popupError", popupError);
				 	model.addAttribute("summoner", summoner);
				 	//Clear summoner's error msg for next request.
				 	summoner.setApiError("");
				 	playerRepository.save(summoner);
				 	
			        return "homePage";
			 	}
			 	
		 		if(loadMore != null) {
		 			//Just loading 10 more games
		 			summoner = GetMatchHistory.matchHistory(summoner, summoner.getMatchType(), true);
		 		}
		 		else if(!summoner.getMatchType().equals(matchType)) {
		 			//Reloading 20 games of a specific queue (new matchType)
		 			summoner = GetMatchHistory.matchHistory(summoner, matchType, false);
		 		}
		 		
		 		//If API does not return all information for a existing summoner - redirect to previous page + show popup
			 	if(summoner.getApiError().contains("Error")) {
			 		playerRepository.save(summoner);
			 		return "redirect:" + RedirectUrl.getRedirectUrl(request, summoner);
			 	}
			 	summoner.setMatchType(matchType);
		 	}
		 	
		 	
		 	//If request is a completely new summoner
		 	else {
		 		if(matchType != null) {
		 			summoner.setMatchType(matchType);
		 		}
			 	//Gets patch info from API + sets Icon URL
			 	summoner = GetCurrentPatch.currentPatch(summoner);
			 	
			 	//Gets the basic info if the given summoner 				Location: top of page
			 	summoner = RetrieveAccountGenericInfo.retrieveAccountGenericInfo(summoner, summonerName);
			 	
			 	//Gets the basic Ranked info if the given summoner 			Location: top of page
			 	summoner = GetRankInfo.rankInformation(summoner, summoner.getAccId());
			 	
			 	//GetMatchHistory.matchHistory calls all of the necessary APIs to get the matchHistory info
			 	summoner = GetMatchHistory.matchHistory(summoner, summoner.getMatchType(), false);
			 	
			 	//If API does not return information for a new summoner - redirect to / + show popup
			 	if(summoner.getApiError().contains("Error")) {
			 		setFetchInitialSummonerApiError(summoner.getApiError());
			 		return "redirect:/";
			 	}
		 	}
		 	
		 	
		 	//Set / Calc recent match summary info.
		 	summoner.setRecentMatchSummary(CalcRecentMatchInfo.calcRecentMatchInfo(summoner));
		 	//Set / Calc recent champion stats.
		 	summoner.setRecentChampions(CalcRecentChampionInfo.calcRecentChampionInfo(summoner));
		 	
		 	
		 	playerRepository.save(summoner);
		 	
		 	model.addAttribute("summoner", summoner);
		 	
	        return "homePage";
	    }

	 	
	 //Page containing storedSummoners.json
	 @RequestMapping({"/storedSummoners"})
		public String storedSummoners(Model model) {
		 	Vector<PlayerAcc> allStoredAccs = GetStoredSummoners.getAllStoredSummoners();
		 	
		 	String errorPopup = getFetchSummError();
		 	//User clicked 'view' on a summoner, but Error occurred (summoner does not exist in database) ( redirected from /storedSummoner/{summonerName} )
		 	if(!errorPopup.contains("none")) {
		 		setFetchSummError("none");
		 		model.addAttribute("popupError", errorPopup);
		 	}
		 	//Alphabetically A-Z
		 	Collections.sort(allStoredAccs, (acc1, acc2) -> acc1.getAccName().compareTo(acc2.getAccName()));
		 	model.addAttribute("allSummoners", allStoredAccs);
		 
		    return "storedSummoners";
	}
	 
	 
	 	
	 //Add summoner to local database (Stored-Summoners.json file)
	 @RequestMapping({"/saveSummoner"})
	 	public String addSummoner(@RequestParam(value = "summRandomId") String summUniqueId, Model model) {
		 	 PlayerAcc summoner = playerRepository.findById(summUniqueId).get();
		 	 
		 	 if(summoner.getAccId() != null && !summoner.getMatchHistoryList().isEmpty()) {
				 StoreSummoner.storeSummonerToFile(summoner);
			 }
			 return "redirect:/storedSummoners";
	  	}
	 
	//Removes summoner from local database (Stored-Summoners.json file)
		 @PostMapping({"/deleteSummoner"})
		 	public String deleteSummoner(@RequestParam String accName, @RequestParam long newestGameUnixTime, @RequestParam long oldestGameUnixTime) { 
				
				StoreSummoner.deletePassedSummoner(accName, newestGameUnixTime, oldestGameUnixTime);
				
				return "redirect:/storedSummoners";
		 	} 
	//Finds summoner from local database (Stored-Summoners.json file)
		 @RequestMapping({"/storedSummoner/{summonerName}"})
		 	public String fetchSummoner(@PathVariable("summonerName") String summonerName, @RequestParam String accName, @RequestParam long newestGameUnixTime, @RequestParam long oldestGameUnixTime, Model model) { 
			 	
			 	PlayerAcc storedSummoner = StoreSummoner.fetchSelectedSummoner(accName, newestGameUnixTime, oldestGameUnixTime);
				if(storedSummoner.getAccId().contains("Summoner not found")) {
					setFetchSummError("Error: Summoner not found. Retry or refresh page.");
					return "redirect:/storedSummoners";
				}
			 	
			 	
			 	model.addAttribute("summoner", storedSummoner);
			 	
				return "storedSummoner";
		 	} 
	
	 
	 
	 
	 

	
	public String getFetchInitialSummonerApiError() {
		return fetchInitialSummonerApiError;
	}

	public void setFetchInitialSummonerApiError(String fetchInitialSummonerApiError) {
		this.fetchInitialSummonerApiError = fetchInitialSummonerApiError;
	}

	public String getFetchSummError() {
		return fetchSummError;
	}

	public void setFetchSummError(String fetchSummError) {
		this.fetchSummError = fetchSummError;
	}
	 	
	 
}
