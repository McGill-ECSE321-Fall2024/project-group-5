<template>
  <main class="home">
    <section class="hero">
      <h1>Welcome to GameStore</h1>
      <p>Your ultimate destination for the best games!</p>
      <button @click="shopNow">Shop Now</button>
    </section>
    <section class="featured-games">
      <h2>Featured Games</h2>
      <div class="games-grid">
        <div v-for="game in featuredGames" :key="game.id" class="game-card">
          <img :src="game.image" :alt="game.name" />
          <h3>{{ game.name }}</h3>
          <p>${{ game.price }}</p>
          <button @click="addToCart(game)">Add to Cart</button>
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
        { id: 1, name: "Adventure Quest", price: 29.99, image: "/images/adventure-quest.jpg" },
        { id: 2, name: "Mystic RPG", price: 49.99, image: "/images/mystic-rpg.jpg" },
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
.home {
  font-family: 'Arial', sans-serif;
}

.hero {
  background: url('/images/hero-bg.jpg') center/cover no-repeat;
  color: white;
  text-align: center;
  padding: 4rem;
}

.hero h1 {
  font-size: 3rem;
}

.hero button {
  background-color: #ff4500;
  color: white;
  border: none;
  padding: 1rem 2rem;
  font-size: 1.25rem;
  cursor: pointer;
}

.featured-games {
  padding: 2rem;
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

.game-card img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 4px;
}
</style>
