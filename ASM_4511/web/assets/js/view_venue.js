/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function setBookingDetails(venueId, venueName) {
    $('#venueId').val(venueId);
    $('#venueName').val(venueName); // Adjust if using text input or another method to display
    $('#bookingModal').modal('show'); // Show the booking modal
    var today = new Date();
    today.setDate(today.getDate() + 2);
    var tomorrow = today.toISOString().split('T')[0];
    $('#bookingDate').val(tomorrow).attr('min', tomorrow);// Set minimum date as today
    $('#startTime').empty(); // Clear previous time slots

    // Optionally trigger loading available slots when the modal opens if a default date is set
    updateAvailableSlots(venueId, $('#bookingDate').val());
}

function updateAvailableSlots(venueId, bookingDate) {
    if (!bookingDate)
        return; // Do nothing if booking date is not set

    $.ajax({
        url: '/ASM_4511/AvailableSlotsServlet', // Ensure the path is correct
        type: 'GET',
        data: {venueId: venueId, bookingDate: bookingDate},
        success: function (response) {
            var availableSlots = response; // Assuming response is already parsed JSON
            $('#startTime').empty(); // Clear previous options
            if (availableSlots.length > 0) {
                availableSlots.forEach(function (slot) {
                    $('#startTime').append($('<option>', {
                        value: slot,
                        text: slot // Adjust text if needed for AM/PM formatting
                    }));
                });
            } else {
                $('#startTime').append($('<option>', {
                    value: '',
                    text: 'No slots available'
                }));
            }
        },
        error: function () {
            alert('Failed to fetch available slots.');
            $('#startTime').append($('<option>', {
                value: '',
                text: 'Failed to load'
            }));
        }
    });
}

function submitBooking() {
    var formData = {
        venueId: $('#venueId').val(),
        bookingDate: $('#bookingDate').val(),
        startTime: $('#startTime').val(),
        endTime: calculateEndTime($('#startTime').val()) // Assumes calculateEndTime is defined elsewhere or remove if not needed
    };

    $.ajax({
        url: '/ASM_4511/BookVenueServlet', // Adjust URL as necessary
        type: 'POST',
        data: formData,
        success: function (data) {
            alert('Booking successful!');
            $('#bookingModal').modal('hide');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: Unable to book the venue.');
        }
    });
}

// Example calculateEndTime function if needed
function calculateEndTime(startTime) {
    var start = new Date('1970-01-01T' + startTime);
    var end = new Date(start.getTime() + 2 * 60 * 60 * 1000); // Add 2 hours
    return end.toISOString().substring(11, 16); // Returns time in HH:MM format
}
