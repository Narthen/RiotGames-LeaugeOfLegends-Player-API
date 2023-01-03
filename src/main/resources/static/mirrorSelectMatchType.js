/**
 * Used to control / create the doughnut chart for the recent game win loss.
 */

			
	var select = document.getElementById("matchHistSelect");
    var mirrorSelect = document.getElementById("mirrorSelect");
    select.addEventListener("change", function() {
        mirrorSelect.value = select.value;
    });