/**
 * Used to hide the 'Save Summoner' button if summoner's acc name is empty (no summoner has been searched yet)
 */
			 	
				   
				    var summonerName = document.getElementById('saveSummonerNameInput').value;
				   
				    if(summonerName === "") {
				      document.getElementById("saveSummoner").style.display = "none";
				    }
				  
				
				
				