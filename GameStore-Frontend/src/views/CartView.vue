<template>
  <main>
    <h1>Your Cart</h1>
    <div v-if="cart.length">
      <div v-for="game in cart" :key="game.id" class="cart-item">
        <h3>{{ game.name }}</h3>
        <p>Price: ${{ game.price }}</p>
        <button @click="removeFromCart(game)">Remove</button>
      </div>
      <h2>Total: ${{ totalPrice }}</h2>
      <button @click="checkout">Checkout</button>
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
        { id: 1, name: "Game 1", price: 59.99 },
        { id: 2, name: "Game 2", price: 49.99 },
      ],
    };
  },
  computed: {
    totalPrice() {
      return this.cart.reduce((total, game) => total + game.price, 0);
    },
  },
  methods: {
    removeFromCart(game) {
      this.cart = this.cart.filter((item) => item.id !== game.id);
    },
    checkout() {
      console.log("Proceeding to checkout.");
    },
  },
};
</script>

<style scoped>
/* Add styles for the cart page here */
</style>
