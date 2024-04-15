document.addEventListener('DOMContentLoaded', function () {
    let cart = JSON.parse(sessionStorage.getItem('cart')) || {};
    if (!cart.items) {
        cart.items = [];
    }

    function addToCart(button) {
        let equipmentId = button.dataset.id;
        let itemIndex = cart.items.findIndex(item => item.equipmentId === equipmentId);

        // Toggle item in or out of cart based on its current state
        if (itemIndex > -1) {
            // If the item is already in the cart, remove it
            cart.items.splice(itemIndex, 1);
            button.innerText = 'Add to Cart'; // Changed 'element' to 'button'
        } else {
            // Add item to the cart if it's not there
            let newItem = {
                equipmentId: equipmentId,
                name: button.dataset.name, // Changed 'element' to 'button'
                description: button.dataset.description, // Changed 'element' to 'button'
                location: button.dataset.location // Changed 'element' to 'button'
            };
            cart.items.push(newItem);
            button.innerText = 'Added'; // Changed 'element' to 'button'
        }
        // Update cart storage and UI
        sessionStorage.setItem('cart', JSON.stringify(cart));
        updateCartDisplay();
        updateCartCount();
    }

    function updateCartDisplay() {
    const cartItems = document.getElementById('cartItems');  // This should be a part of your form
    cartItems.innerHTML = '';  // Clears current content
    cart.items.forEach(item => {
        const listItem = document.createElement('li');
        
        const inputHidden = document.createElement('input');
        inputHidden.type = 'hidden';
        inputHidden.name = 'equipmentId';
        inputHidden.value = item.equipmentId;

        cartItems.appendChild(inputHidden);  // Appending directly to a form or a div within a form

        listItem.textContent = `${item.name} - ${item.description} at ${item.location}`;
        cartItems.appendChild(listItem);
    });
}

    function updateCartCount() {
        const cartCount = document.getElementById('cartCount');
        cartCount.textContent = cart.items.length;
    }

    // Saving form data in the cart
    function saveFormData() {
        const location = document.getElementById('location').value;
        const startDate = document.getElementById('startDate').value;
        const returnDate = document.getElementById('returnDate').value;
        const userId = '<%= user.getUserID() %>';  // Assuming you have user data available as a JSP variable

        cart.location = location;
        cart.startDate = startDate;
        cart.returnDate = returnDate;
        cart.userId = userId;

        sessionStorage.setItem('cart', JSON.stringify(cart));
    }

    // Attach event listeners to buttons
    document.querySelectorAll('.add-to-cart-btn').forEach(button => {
        button.addEventListener('click', function () {
            addToCart(this);
            saveFormData();
        });
    });

    updateCartDisplay();
    updateCartCount();
});
document.getElementById('cartForm').addEventListener('submit', function(event) {
    console.log("Submitting form with data:");
    const formData = new FormData(this);
    for (const [key, value] of formData.entries()) {  // Use .entries() to iterate over FormData
        console.log(`${key}: ${value}`);
    }
//    event.preventDefault(); // Uncomment to debug without sending a request
});


