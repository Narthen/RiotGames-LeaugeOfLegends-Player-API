/**
 * Used to control the dropdown arrow (and content) of the Most Played Champions section of the page.
 */

				const championStatsContent = document.getElementById('championContent');
                var toggleChampionButton = document.getElementById('toggleChampionButton');
                
                championStatsContent.style.display = 'flex';
                   toggleChampionButton.addEventListener('click', function() {
                    // If the content is currently shown, hide it
                    if (championStatsContent.style.display === 'flex') {
                      championStatsContent.style.display = 'none';
                    }
                    // Otherwise, show the content
                    else {
                      championStatsContent.style.display = 'flex';
                    }
                  });
                
                
                const matchHistStatsContent = document.getElementById('matchHistStats');
                var toggleMatchHistStatsButton = document.getElementById('toggleMatchHistStatsButton');
                
                matchHistStatsContent.style.display = 'flex';
                   toggleMatchHistStatsButton.addEventListener('click', function() {
                    // If the content is currently shown, hide it
                    if (matchHistStatsContent.style.display === 'flex') {
                      matchHistStatsContent.style.display = 'none';
                    }
                    // Otherwise, show the content
                    else {
                      matchHistStatsContent.style.display = 'flex';
                    }
                  });
                
                
                // Attach a click event listener to the dropdown button
                dropdownButtonRecentGames.addEventListener('click', function() {
                  // Toggle the 'show' class on the dropdown content
                  dropdownContentRecentGames.classList.toggle('show');
                  
                });