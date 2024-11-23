<template>
  <main class="cart">
    <h1>Your Cart</h1>
    <div v-if="cart.length" class="cart-items">
      <div v-for="game in cart" :key="game.id" class="cart-item">
        <img :src="game.image" :alt="game.name" />
        <h3>{{ game.name }}</h3>
        <p>${{ game.price }}</p>
        <button @click="removeFromCart(game)">Remove</button>
      </div>
      <h2>Total: ${{ totalPrice }}</h2>
      <button class="checkout-btn" @click="checkout">Checkout</button>
    </div>
    <p v-else>Your cart is empty.</p>
  </main>
</template>

<script>
export default {
  name: "CartView",
  data() {
    return {
      cart: [
        { id: 1, name: "Adventure Quest", price: 29.99, image: "/images/adventure-quest.jpg" },
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
.cart-items {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.cart-item {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  padding: 1rem;
  border-radius: 8px;
}

.cart-item img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 1rem;
}

.checkout-btn {
  background-color: #ff4500;
  color: white;
  border: none;
  padding: 1rem;
  cursor: pointer;
}
</style>
