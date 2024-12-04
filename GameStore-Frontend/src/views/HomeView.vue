<template>
  <main class="home-container">
    <section class="hero-header">
      <h1 class="hero-title">Welcome to GameStore</h1>
      <p class="hero-description">
        Your ultimate destination for the best games!
      </p>
      <button @click="shopNow" class="primary-btn">Shop Now</button>
    </section>
    <section class="featured-games">
      <h2 class="featured-games-title">Featured Games</h2>
      <div class="games-grid">
        <div v-for="game in featuredGames" :key="game.id" class="game-card">
          <h3 class="game-name">{{ game.name }}</h3>
          <p class="game-price">${{ game.price }}</p>
          <button @click="addToCart(game)" class="add-to-cart-btn">Add to Cart</button>
        </div>
      </div>
    </section>
  </main>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      featuredGames: [
        { id: 1, name: "Adventure Quest", price: 29.99 },
        { id: 2, name: "Mystic RPG", price: 49.99 },
      ],
    };
  },
  methods: {
    shopNow() {
      this.$router.push("/shop");
    },
    addToCart(game) {
      console.log(`Added ${game.name} to the cart.`);
    },
  },
};
</script>

<style scoped>
.home-container {
  max-width: 1200px;
  width: 90%;
  margin: auto;
  padding: 2rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.hero-header {
  background-color: #f4f4f9;
  padding: 4rem;
  border-radius: 12px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
}

.hero-title {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.hero-description {
  font-size: 1.25rem;
  color: #777;
  margin-bottom: 2rem;
}

.featured-games {
  padding: 2rem;
}

.featured-games-title {
  font-size: 2rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1rem;
}

.game-card {
  text-align: center;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.game-name {
  color: #333;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.game-price {
  color: #777;
  font-size: 1.25rem;
  margin-bottom: 1.5rem;
}

.add-to-cart-btn {
  padding: 10px 20px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  background-color: #3498db;
  color: white;
  transition: background-color 0.3s, transform 0.2s;
}

.add-to-cart-btn:hover {
  background-color: #2980b9;
  transform: scale(1.05);
}

.add-to-cart-btn:active {
  transform: scale(0.95);
}

.primary-btn {
  background-color: #ff4500;
  color: white;
  border: none;
  padding: 1rem 2rem;
  font-size: 1.25rem;
  cursor: pointer;
  border-radius: 6px;
  transition: background-color 0.3s, transform 0.2s;
}

.primary-btn:hover {
  background-color: #e63e00;
  transform: scale(1.05);
}

.primary-btn:active {
  transform: scale(0.95);
}
</style>
