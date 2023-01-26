//This is for the select at the bottom by the match history. 
               //If the user has not entered their name at the top, there will be an error.
                //This function makes it so they cannot reload if accName is empty (they have not loaded a page yet)
              
                	var formElement = document.getElementById("changeMatchType");
                	var actionValue = formElement.getAttribute("action");
                	
                	formElement.addEventListener("change", function() {
                		if (actionValue === '/summoner/') {
                    	  }
                    	else{
                    		document.getElementById('changeMatchType').submit();
                    	}
                    });
                	
                	      