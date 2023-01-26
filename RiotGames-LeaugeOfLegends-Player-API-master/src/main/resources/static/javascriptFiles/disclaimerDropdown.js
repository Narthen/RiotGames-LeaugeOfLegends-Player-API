/**
 * Used to control the dropdown arrow (and content) of the Disclaimer section of the page.
 */

				const disclaimerContent = document.getElementById('disclaimerContent');
                var toggleDisclaimerButton = document.getElementById('toggleDisclaimerButton');
                
                disclaimerContent .style.display = 'none';
                toggleDisclaimerButton.addEventListener('click', function() {
                    // If the content is currently shown, hide it
                    if (disclaimerContent .style.display === 'flex') {
                    	disclaimerContent .style.display = 'none';
                    }
                    // Otherwise, show the content
                    else {
                    	disclaimerContent .style.display = 'flex';
                    }
                  });
                
             