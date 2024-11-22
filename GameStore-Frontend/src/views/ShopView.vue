<template>
  <main>
    <h1>Shop Games</h1>
    <div>
      <label for="filter">Filter by Genre:</label>
      <select id="filter" v-model="selectedGenre">
        <option value="">All</option>
        <option v-for="genre in genres" :key="genre" :value="genre">
          {{ genre }}
        </option>
      </select>
    </div>
    <div class="games-list">
      <div v-for="game in filteredGames" :key="game.id" class="game">
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
  name: "ShopView",
  data() {
    return {
      games: [
        { id: 1, name: "Game 1", genre: "Adventure", description: "An exciting adventure!", price: 59.99 },
        { id: 2, name: "Game 2", genre: "RPG", description: "A thrilling RPG!", price: 49.99 },
      ],
      selectedGenre: "",
      genres: ["Adventure", "RPG", "Action", "Strategy"],
    };
  },
  computed: {
    filteredGames() {
      if (!this.selectedGenre) return this.games;
      return this.games.filter((game) => game.genre === this.selectedGenre);
    },
  },
  methods: {
    addToCart(game) {
      console.log(`Added ${game.name} to the cart.`);
    },
  },
};
</script>

<style scoped>
/* Add styles for the shop page here */
</style>
