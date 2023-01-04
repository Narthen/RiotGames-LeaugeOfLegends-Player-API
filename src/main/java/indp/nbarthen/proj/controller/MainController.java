package indp.nbarthen.proj.controller;


import indp.nbarthen.proj.apicontrolls.*;
import indp.nbarthen.proj.repository.PlayerAcc;
import indp.nbarthen.proj.repository.PlayerRepository;
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
	private int invalidSummoner;
	private String previousURL;
	private PlayerAcc currSummoner;
	private String currMatchType;
	
	public MainController(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
		//TO DO: INITIALIZE VARIABLES  (can this be done in PlayerAcc constructor instead?)
		invalidSummoner = 0;
		previousURL = "/";
		currSummoner = new PlayerAcc();
		currMatchType = "All";
	}
	
	 @RequestMapping({"/"})
	    public String homePage(Model model) throws JsonMappingException, JsonProcessingException {
		 	PlayerAcc summoner = new PlayerAcc();
		 	//Gets patch info from API + sets Icon URL
		 	summoner = GetCurrentPatch.currentPatch(summoner);
		 	//Show invalid summoner name popup
		 	if(getInvalidSummoner() == 1) {
		 		setInvalidSummoner(0);
		 		model.addAttribute("popup", 1);
		 	}
		 	else {
		 		//Dont show invalid summoner name popup
		 		model.addAttribute("popup", 0);
		 	}
		 	
		 	
		 	model.addAttribute("summoner", summoner);
		 	model.addAttribute("prevURL", getPreviousURL());
		 	
		 	
	        return "homePage";
	    }
	 
	 @RequestMapping({"summoner/{summonerName}"})
	    public String getSummoner(@PathVariable("summonerName") String summonerName, @RequestParam(value = "matchType", required = false) String matchType, @RequestParam(value = "loadMore", required = false) String loadMore, Model model) throws JsonMappingException, JsonProcessingException {
		 	
		 	PlayerAcc summoner = new PlayerAcc();
		 	//If just loading additional match history games OR filtering for game type.
		 	if(getCurrSummoner().getAccName().equals(summonerName)) {
		 		summoner = getCurrSummoner();
		 		if(loadMore != null) {
		 			//Just loading 10 more games
		 			summoner = GetMatchHistory.matchHistory(summoner, getCurrMatchType(), true);
		 		}
		 		else {
		 			//Reloading 20 games of a specific queue
		 			summoner = GetMatchHistory.matchHistory(summoner, matchType, false);
		 			setCurrMatchType(matchType);
		 		}
		 	}
		 	//If request is a completely new summoner
		 	else {
			 	//Gets patch info from API + sets Icon URL
			 	summoner = GetCurrentPatch.currentPatch(summoner);
			 	
			 	//Gets the basic info if the given summoner 				Location: top of page
			 	summoner = RetrieveAccountGenericInfo.retrieveAccountGenericInfo(summoner, summonerName);
			 	
			 	//Gets the basic Ranked info if the given summoner 			Location: top of page
			 	summoner = GetRankInfo.rankInformation(summoner, summoner.getAccId());
			 	
			 	//GetMatchHistory.matchHistory calls all of the necessary APIs to get the matchHistory info
			 	summoner = GetMatchHistory.matchHistory(summoner, matchType, false);
			 	
			 	
			 	//If API does not return information - redirect + show popup
			 	if(summoner.getAccId().contains("Error")) {
			 		setInvalidSummoner(1);
			 		return "redirect:/";
			 	}
			 	else {
			 		setInvalidSummoner(0);
			 	}
			 	setCurrMatchType(matchType);
		 	}
		 	
		 	//Set / Calc recent match summary info.
		 	summoner.setRecentMatchSummary(CalcRecentMatchInfo.calcRecentMatchInfo(summoner));
		 	
		 	setPreviousURL("/summoner/" + summoner.getAccName());
		 	setCurrSummoner(summoner);
		 	
		 	model.addAttribute("prevURL", getPreviousURL());
		 	model.addAttribute("summoner", summoner);
		 
		 	
		 	
		 	
	        return "homePage";
	    }

	 
	 
	 
	public int getInvalidSummoner() {
		return invalidSummoner;
	}

	public void setInvalidSummoner(int invalidSummoner) {
		this.invalidSummoner = invalidSummoner;
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
	 	
	 
}
