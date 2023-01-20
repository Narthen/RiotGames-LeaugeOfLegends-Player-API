package indp.nbarthen.proj.controller;


import indp.nbarthen.proj.apicontrolls.*;
import indp.nbarthen.proj.repository.LoLMatch;
import indp.nbarthen.proj.repository.PlayerAcc;
import indp.nbarthen.proj.repository.PlayerRepository;
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
	private String previousURL;
	private PlayerAcc currSummoner;
	private String currMatchType;
	private String fetchInitialSummonerApiError;
	private String fetchSummError;
	
	public MainController(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
		previousURL = "/";
		currSummoner = new PlayerAcc();
		currMatchType = "All";
		fetchInitialSummonerApiError = "none";
		fetchSummError = "none";
	}
	
	 @RequestMapping({"/"})
	    public String homePage(Model model) throws JsonMappingException, JsonProcessingException {
		 	
		 	PlayerAcc summoner = new PlayerAcc();
		 	String popupError = "none";
		 	//Gets patch info from API + sets Icon URL
		 	summoner = GetCurrentPatch.currentPatch(summoner);
		 	
		 	//If user was redirected after Summoner Search. Show popup error on webpage
		 	if(getFetchInitialSummonerApiError().contains("Error")) {
		 		popupError = getFetchInitialSummonerApiError();
		 		setFetchInitialSummonerApiError("none");
		 		model.addAttribute("summoner", summoner);
		 		model.addAttribute("popupError", popupError);
			 	model.addAttribute("prevURL", getPreviousURL()); //	REMOVE
		 		summoner.setApiError(""); //REMOVE
		 		setCurrSummoner(summoner); //REMOVE
		 		return "homePage";
		 	}
		 	
		 	setPreviousURL("/"); //REMOVE
		 	model.addAttribute("summoner", summoner);
		 	model.addAttribute("popupError", popupError);
		 	model.addAttribute("prevURL", getPreviousURL()); //REMOVE
		 	
	        return "homePage";
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
			 		String urlOptionalParameters = request.getQueryString();
			 		int index = urlOptionalParameters.indexOf("matchType=");
			 		if(index != -1) {
			 		    int endIndex = urlOptionalParameters.indexOf("&", index);
			 		    if(endIndex == -1) {
			 		        endIndex = urlOptionalParameters.length();
			 		    }
			 		   urlOptionalParameters = urlOptionalParameters.substring(0, index) + "matchType=" + summoner.getMatchType() + urlOptionalParameters.substring(endIndex);
			 		}
			 		String url = request.getRequestURL().toString() + "?" + urlOptionalParameters;
			 		System.out.println("my url for redirect: " + url);
			 		return "redirect:" + url;
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
		 
		 	
		 	setPreviousURL("/summoner/" + summoner.getAccName());
		 	
		 	
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
		 	
		 	model.addAttribute("allSummoners", allStoredAccs);
		 
		 	setPreviousURL("/storedSummoners");
		    return "storedSummoners";
	}
	 
	 
	 	
	 //Add summoner to local database (Stored-Summoners.json file)
	 @RequestMapping({"/saveSummoner"})
	 	public String addSummoner(Model model) {
			 PlayerAcc summoner = getCurrSummoner();
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
			 	
			 	
			 	setPreviousURL("/storedSummoners");
			 	
			 	model.addAttribute("prevURL", getPreviousURL());
			 	model.addAttribute("summoner", storedSummoner);
			 	
				return "storedSummoner";
		 	} 
	
	 
	 
	 
	 

	public String getPreviousURL() {
		return previousURL;
	}

	public void setPreviousURL(String previousURL) {
		this.previousURL = previousURL;
	}

	public PlayerAcc getCurrSummoner() {
		return currSummoner;
	}

	public void setCurrSummoner(PlayerAcc currSummoner) {
		this.currSummoner = currSummoner;
	}

	public String getCurrMatchType() {
		return currMatchType;
	}

	public void setCurrMatchType(String currMatchType) {
		this.currMatchType = currMatchType;
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
