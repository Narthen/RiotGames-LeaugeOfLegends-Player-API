/**
 * Used to control / create the doughnut chart for the recent game win loss.
 */

				document.getElementById("getSummoner").addEventListener("submit", function(event) {
					      event.preventDefault(); // prevent the form from being submitted
					      var summonerName = document.getElementById("summonerName").value; // get the value of the input field
					      this.action = "/summoner/" + summonerName; // update the form's action attribute
					      this.submit(); // submit the form
					   });