<template>
  <main class="page-wrapper">
    <div class="cart-container">
      <h1>Your Cart</h1>
      <div v-if="cart.length" class="cart-items">
        <div v-for="game in cart" :key="game.id" class="cart-item">
          <div class="game-info">
            <h3>{{ game.name }}</h3>
            <p>${{ game.price }}</p>
          </div>
          <button class="remove-btn" @click="removeFromCart(game)">Remove</button>
        </div>
        <h2>Total: ${{ totalPrice }}</h2>
        <button class="checkout-btn" @click="checkout">Checkout</button>
      </div>
      <p v-else>Your cart is empty.</p>
    </div>
  </main>
</template>

<script>
export default {
  name: "CartView",
  data() {
    return {
      cart: [
        { id: 1, name: "Adventure Quest", price: 29.99 },
        { id: 2, name: "Fantasy Battle", price: 39.99 },
      ],
    };
  },
  computed: {
    totalPrice() {
      return this.cart.reduce((total, game) => total + game.price, 0).toFixed(2);
    },
  },
  methods: {
    removeFromCart(game) {
      this.cart = this.cart.filter((item) => item.id !== game.id);
    },
    checkout() {
      alert("Proceeding to checkout!");
    },
  },
};
</script>

<style scoped>
/* Wrapper to center the cart page content */
.page-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f4f4f9;
  margin: 0;
  font-family: Arial, sans-serif;
}

/* Cart container styling */
.cart-container {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
  max-width: 800px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 1.8rem;
  margin-bottom: 1.5rem;
  color: black;
}

/* Cart items styling */
.cart-items {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.cart-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #ddd;
  padding: 1rem;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.game-info {
  text-align: left;
  flex: 1;
}

h3, p {
  margin: 0;
  color: black;
}

h3 {
  font-size: 1.2rem;
}

p {
  color: #333;
}

/* Remove button styling */
.remove-btn {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  cursor: pointer;
  border-radius: 6px;
}

.remove-btn:hover {
  background-color: #c0392b;
}

/* Checkout button styling */
.checkout-btn {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 1rem;
  cursor: pointer;
  width: 100%;
  border-radius: 6px;
}

.checkout-btn:hover {
  background-color: #217dbb;
}

/* Empty cart message */
p {
  color: #555;
  font-size: 1.1rem;
  margin-top: 1rem;
}
</style>
