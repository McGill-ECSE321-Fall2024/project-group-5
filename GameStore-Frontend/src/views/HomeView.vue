<template>
  <main>
    <h1>Welcome to GameStore</h1>
    <p>Discover and purchase your favorite games!</p>
    <div class="featured-games">
      <h2>Featured Games</h2>
      <div v-for="game in featuredGames" :key="game.id" class="game">
        <h3>{{ game.name }}</h3>
        <p>{{ game.description }}</p>
        <p>Price: ${{ game.price }}</p>
        <button @click="addToCart(game)">Add to Cart</button>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      featuredGames: [
        { id: 1, name: "Game 1", description: "An exciting adventure!", price: 59.99 },
        { id: 2, name: "Game 2", description: "A thrilling RPG!", price: 49.99 },
      ],
    };
  },
  methods: {
    addToCart(game) {
      console.log(`Added ${game.name} to the cart.`);
    },
  },
};
</script>

<style scoped>
/* Add styles for the home page here */
</style>
