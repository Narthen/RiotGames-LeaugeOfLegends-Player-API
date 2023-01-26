


		//Controls the dropdown arrows for each indiviaual match history game
				const dropdownContainers = document.querySelectorAll('.dropdown-container-matchHistorygame');

                dropdownContainers.forEach((container) => {
                  // Select the button within the current div
                  const button = container.querySelector('.dropdown-button-matchHistorygame');
                
                  button.addEventListener('click', () => {
                    // Get the div with the class name 'dropdown-content' inside the container
                    const dropdownContent = container.querySelector('.dropdown-content-matchHistorygame');
                    
                    if (dropdownContent.style.display === 'none') {
                      dropdownContent.style.display = 'flex';
                    } else {
                      // Otherwise, hide the content if it is currently shown
                      dropdownContent.style.display = 'none';
                    }
                  });
                });
                
        //Controls the dropdown arrows for all of the games (using two buttons) 
                const controlCollapseButton = document.querySelector('.dropdown-button-Controller-Collapse');
                
                // Add an event listener to the control button
                controlCollapseButton.addEventListener('click', () => {
                  // Get all of the dropdown-content divs
                  const dropdownContents = document.querySelectorAll('.dropdown-content-matchHistorygame');
                
                  // Iterate over the dropdown-content divs
                  dropdownContents.forEach((content) => {
                      // Otherwise, show the content if it is currently hidden
                      content.style.display = 'none';
                    
                  });
                });
                const controlExpandButton = document.querySelector('.dropdown-button-Controller-Expand');
                
                // Add an event listener to the control button
                controlExpandButton.addEventListener('click', () => {
                  // Get all of the dropdown-content divs
                  const dropdownContents = document.querySelectorAll('.dropdown-content-matchHistorygame');
                
                  // Iterate over the dropdown-content divs
                  dropdownContents.forEach((content) => {
                      // Otherwise, show the content if it is currently hidden
                      content.style.display = 'flex';
                    
                  });
                });
                
                
                
		//Sets the background color of each game in match history based on weather the button div's text content includes "Victory" or "Defeat"
                // loop through each container div
                for (const dropdownContainer of dropdownContainers) {
                  // find the corresponding button div for the current container div
                  const buttonDiv = dropdownContainer.querySelector('.matchHistorygame-ButtonDivClass');
                
                  // check if the button div's text content includes "Victory" or "Defeat"
                  if (buttonDiv.textContent.includes('Victory')) {
                    // if it does, set the container div's background-color to green
                    dropdownContainer.style.backgroundColor = 'rgb(102, 255, 102)';
                  } else if (buttonDiv.textContent.includes('Defeat')) {
                    // otherwise, if it includes "Defeat", set the background-color to red
                    dropdownContainer.style.backgroundColor = 'rgb(255, 153, 153)';
                  }
                }
                
                
        
                
                
                
                
                