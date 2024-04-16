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
