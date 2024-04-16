/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function validateForm() {
    var oldPassword = document.getElementById('oldPassword').value;
    var newPassword = document.getElementById('newPassword').value;
    var confirmNewPassword = document.getElementById('confirmNewPassword').value;

    if (!oldPassword) {
        alert('Please enter your old password to confirm changes.');
        return false;
    }
    if (newPassword && newPassword !== confirmNewPassword) {
        alert('New passwords do not match.');
        return false;
    }
    return true;
}
function submitProfileUpdate() {
    var formData = {
        userID: document.getElementById('userID').value,
        userName: document.getElementById('userName').value,
        oldPassword: document.getElementById('oldPassword').value,
        newPassword: document.getElementById('newPassword').value,
        confirmNewPassword: document.getElementById('confirmNewPassword').value
    };

    // Check passwords match if new password is provided
    if (formData.newPassword && formData.newPassword !== formData.confirmNewPassword) {
        alert('New passwords do not match.');
        return;
    }

    // Send data to the server using AJAX
    $.ajax({
        url: '${pageContext.request.contextPath}/UpdateUserProfileServlet',
        type: 'POST',
        data: formData,
        success: function (response) {
            alert('Update successful');
            // Optionally, refresh the page or update parts of it
        },
        error: function (xhr, status, error) {
            alert('Update failed: ' + xhr.responseText);
        }
    });
}

