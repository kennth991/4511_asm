/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$('.message a').click(function () {
    $(this).closest('.form').find('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

