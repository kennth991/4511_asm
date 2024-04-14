/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let cart = JSON.parse(sessionStorage.getItem('cart')) || [];
document.addEventListener('DOMContentLoaded', function () {
    updateCartDisplay();
});

function addToCart(element) {
    let equipmentId = element.dataset.id;
    let item = cart.find(item => item.equipmentId === equipmentId);

    if (item) {
        cart = cart.filter(item => item.equipmentId !== equipmentId);  // Remove from cart
        element.innerText = 'Add';
    } else {
        item = {
            equipmentId: equipmentId,
            name: element.dataset.name,
            description: element.dataset.description,
            location: element.dataset.location
        };
        cart.push(item);  // Add to cart
        element.innerText = 'Added';
    }

    sessionStorage.setItem('cart', JSON.stringify(cart));
    updateCartCount();
}

function updateCartDisplay() {
    const cartItems = document.getElementById('cartItems');
    cartItems.innerHTML = ''; // Clear existing items
    const cart = JSON.parse(sessionStorage.getItem('cart')) || [];

    if (cart.length === 0) {
        cartItems.innerHTML = '<p>No devices currently in the cart.</p>';
    } else {
        const list = document.createElement('ul');
        cart.forEach(item => {
            const listItem = document.createElement('li');
            listItem.textContent = `${item.name} - ${item.description} at ${item.location}`;
            list.appendChild(listItem);
        });
        cartItems.appendChild(list);
    }
    updateCartCount();
}

function updateCartCount() {
    const cartCount = document.getElementById('cartCount');
    const cart = JSON.parse(sessionStorage.getItem('cart')) || [];
    cartCount.textContent = cart.length;
}

document.addEventListener('DOMContentLoaded', function () {
    updateCartDisplay();  // To update cart on page load
});

function checkoutCart() {
    const cart = JSON.parse(sessionStorage.getItem('cart')) || [];
    if (cart.length === 0) {
        alert("No items in cart to checkout.");
        return;
    }

    const formData = new FormData();
    cart.forEach(item => {
        formData.append('equipmentIds', item.equipmentId);
    });

    console.log('Submitting form data:', formData); // Add this line to log form data

    fetch('/CheckoutServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        console.log('Checkout successful:', data); // Add this line to log successful checkout
        document.getElementById('cartItems').innerHTML = '<p>Submission complete. Please wait for confirmation.</p>';
        // Optionally, clear the cart in session storage if checkout is successful
        sessionStorage.removeItem('cart');
        updateCartCount(); // Ensure cart count is updated
    })
    .catch(error => {
        console.error('Error:', error);
    });
}





