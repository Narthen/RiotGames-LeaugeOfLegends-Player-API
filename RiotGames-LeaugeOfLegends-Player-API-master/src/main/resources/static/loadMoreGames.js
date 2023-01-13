//This is for the Load More Games form at the bottom of the match history. 
               //If the user has not entered their name at the top, there will be an error.
                //This function makes it so they cannot load more games if accName is empty (they have not loaded a summoner's page yet)
              
                	var formElement = document.getElementById("loadMoreGamesForm");
                	var actionValue = formElement.getAttribute("action");
                	
                	formElement.addEventListener("click", function() {
                		if (actionValue === '/summoner/') {
                			
                    	 }
                    	else{
                    		document.getElementById('loadMoreGamesForm').submit();
                    	}
                    });
                	
                	
           

                	      