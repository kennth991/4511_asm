document.addEventListener('DOMContentLoaded', function () {
    let cart = JSON.parse(sessionStorage.getItem('cart')) || {};
    if (!cart.items) {
        cart.items = [];
    }

    function addToCart(element) {
        let equipmentId = element.dataset.id;
        let item = cart.items.find(item => item.equipmentId === equipmentId);

        if (!item) {
            item = {
                equipmentId: equipmentId,
                name: element.dataset.name,
                description: element.dataset.description,
                location: element.dataset.location
            };
            cart.items.push(item);
            element.innerText = 'Added';
            sessionStorage.setItem('cart', JSON.stringify(cart));
            updateCartDisplay();
            updateCartCount();
        } else {
            console.log('Item already in cart');
        }
    }

    function updateCartDisplay() {
        const cartItems = document.getElementById('cartItems');
        cartItems.innerHTML = '';
        cart.items.forEach(item => {
            const listItem = document.createElement('li');
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
        const userId = '<%= user.getId() %>';  // Assuming you have user data available as a JSP variable

        cart.location = location;
        cart.startDate = startDate;
        cart.returnDate = returnDate;
        cart.userId = userId;

        sessionStorage.setItem('cart', JSON.stringify(cart));
    }

    // Attach event listeners
    document.querySelectorAll('.add-to-cart-btn').forEach(button => {
        button.addEventListener('click', function () {
            addToCart(this);
            saveFormData();
        });
    });

    updateCartDisplay();
    updateCartCount();
});
