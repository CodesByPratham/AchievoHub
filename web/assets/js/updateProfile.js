/* 
 Created on : 28-Nov-2024, 5:25:06 pm
 Author     : PRATHAM
 */

// AJAX for filling the drop down of cities and states.
$(document).ready(function () {
    // Fetch all the states on page load.            
    $.ajax({
        url: 'GetStates',
        method: 'POST',
        success: function (states) {
            states.forEach(function (state) {
                $('#state').append(new Option(state.stateName, state.stateId));
            });
        }
    });

    // Fetch cities when a state is selected.
    $('#state').on('change', function () {
        const stateId = $(this).val();
        $('#city').empty().append(new Option('Select City', ''));
        if (stateId) {
            $.ajax({
                url: 'GetCities',
                method: 'POST',
                data: {state_id: stateId},
                success: function (cities) {
                    cities.forEach(function (city) {
                        $('#city').append(new Option(city.cityName, city.cityId));
                    });
                }
            });
        }
    });
});

// Previewing the image as the user uploads the file.
document.getElementById('formFile').addEventListener('change', function (event) {
    const file = event.target.files[0]; // Get the selected file
    if (file) {
        const reader = new FileReader(); // Create a FileReader instance

        // Event listener for when the file is read
        reader.onload = function (e) {
            document.getElementById('imgPhoto').src = e.target.result; // Set the src of the img tag
        };

        reader.readAsDataURL(file); // Read the file as a data URL
    }
});