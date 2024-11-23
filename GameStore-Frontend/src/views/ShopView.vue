<template>
  <main class="shop">
    <h1>Shop Games</h1>
    <div class="filters">
      <label for="filter">Filter by Genre:</label>
      <select id="filter" v-model="selectedGenre">
        <option value="">All</option>
        <option v-for="genre in genres" :key="genre" :value="genre">{{ genre }}</option>
      </select>
    </div>
    <div class="games-grid">
      <div v-for="game in filteredGames" :key="game.id" class="game-card">
        <img :src="game.image" :alt="game.name" />
        <h3>{{ game.name }}</h3>
        <p>${{ game.price }}</p>
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
        { id: 1, name: "Adventure Quest", genre: "Adventure", price: 29.99, image: "/images/adventure-quest.jpg" },
        { id: 2, name: "Mystic RPG", genre: "RPG", price: 49.99, image: "/images/mystic-rpg.jpg" },
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
.shop {
  padding: 2rem;
}

.filters {
  margin-bottom: 1.5rem;
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1.5rem;
}

.game-card {
  border: 1px solid #ddd;
  padding: 1rem;
  border-radius: 8px;
  text-align: center;
}

.game-card img {
  width: 100%;
  height: 150px;
  object-fit: cover;
}
</style>
