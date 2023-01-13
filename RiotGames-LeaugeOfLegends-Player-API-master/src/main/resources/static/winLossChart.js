/**
 * Used to control / create the doughnut chart for the recent game win loss.
 */
				//Get # of wins and losses
				var div = document.getElementById("recentChartInfo");
				var wins = div.innerHTML.split("Wins: ")[1].split(" ")[0];
				var losses = div.innerHTML.split("Losses: ")[1].split(" ")[0];
				// Set the dimensions of the canvas element
                      var canvas = document.getElementById("recentGamesChart");
                      var ctx = canvas.getContext("2d");
                    
                      // Create the data for the chart
                      var data = {
                        labels: ["Wins", "Losses"],
                        datasets: [
                          {
                            data: [wins, losses], // The values for the chart
                            backgroundColor: ["#3cba9f", "#c45850"], // The colors for the chart
                            hoverBackgroundColor: ["#3cba9f", "#c45850"] // The hover colors for the chart
                          }
                        ]
                      };
                    
                      // Create the chart
                      var myDoughnutChart = new Chart(ctx, {
                        type: "doughnut",
                        data: data,
                        options: {
                            maintainAspectRatio: false, // Prevent the chart from filling the screen
                            legend: {
                              display: false // Hide the legend
                            }
                            
                        }
                      });