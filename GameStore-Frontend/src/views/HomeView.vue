<template>
  <div class="gamestore-homepage">
    <!-- Header -->
    <header class="header">
      <div class="logo">
        <h1>GameTime</h1>
      </div>
      <nav class="nav-links">
      </nav>
      <div class="header-right">
        <input type="text" v-model="searchQuery" placeholder="Search..." class="search-input" />
      </div>
    </header>

    <!-- Hero Banner -->
    <section class="hero-banner">
      <div class="hero-content">
        <h2 class="hero-title">Explore the Latest Games</h2>
        <p class="hero-description">Discover exciting adventures, epic battles, and thrilling stories.</p>
        <button class="shop-now-btn" @click="navigateToShowPage">Shop Now</button>
      </div>
    </section>

    <!-- Categories -->
    <section class="categories">
      <h2>Shop by Category</h2>
      <div class="category-grid">
        <div class="category-item" v-for="category in categories" :key="category.name" @click="navigateToCategory(category.name)">
          <i :class="category.icon"></i>
          <p>{{ category.name }}</p>
        </div>
      </div>
    </section>


    <!-- Featured Games -->
    <section class="featured-games">
      <h2>Featured Games</h2>
      <div class="games-grid">
        <div class="game-card" v-for="game in featuredGames" :key="game.id">
          <img :src="game.image" alt="Game Image" class="game-image" />
          <h3 class="game-title">{{ game.title }}</h3>
          <p class="game-description">{{ game.description }}</p>
          <button class="add-to-cart-btn">Add to Cart</button>
        </div>
      </div>
    </section>

    <!-- Deals Section -->
    <section class="deals-section">
      <h2>Current Deals</h2>
      <div class="deals-grid">
        <div class="deal-card" v-for="deal in deals" :key="deal.id">
          <img :src="deal.image" alt="Deal Image" class="deal-image" />
          <h3 class="deal-title">{{ deal.title }}</h3>
          <p class="deal-price">{{ deal.price }}</p>
          <button class="add-to-cart-btn">Add to Cart</button> <!-- Add to Cart button -->
        </div>
      </div>
    </section>


    <!-- Footer -->
    <footer class="footer">
      <p>&copy; 2024 Game Store. All Rights Reserved.</p>
      <div class="footer-links">
        <a href="#">Customer Support</a>
        <a href="#">Terms of Service</a>
        <a href="#">Privacy Policy</a>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "GameStoreHomePage",
  data() {
    return {
      searchQuery: "",
      categories: [
        { name: "Action", icon: "fas fa-gamepad" },
        { name: "Adventure", icon: "fas fa-mountain" },
        { name: "RPG", icon: "fas fa-dragon" },
        { name: "Puzzle", icon: "fas fa-brain" },
        { name: "Strategy", icon: "fas fa-chess" },
      ],
      featuredGames: [
        { id: 1, title: "Game 1", description: "An epic action game.", image: "game1.jpg" },
        { id: 2, title: "Game 2", description: "A thrilling adventure.", image: "game2.jpg" },
        { id: 3, title: "Game 3", description: "An immersive RPG.", image: "game3.jpg" },
      ],
      deals: [
        { id: 1, title: "Game 4", price: "$19.99", image: "deal1.jpg" },
        { id: 2, title: "Game 5", price: "$29.99", image: "deal2.jpg" },
        { id: 3, title: "Game 6", price: "$39.99", image: "deal3.jpg" },
      ],
    };
  },
  methods: {
    navigateToShowPage() {
      this.$router.push({ name: "ShowView.vue" }); // Adjust 'ShowView' to the actual route name.
    },
  },
};
</script>

<style scoped>
/* General */
body {
  font-family: 'Arial', sans-serif;
  margin: 0;
  background-color: #f8f9fa;
  color: #333;
}

.gamestore-homepage {
  background-color: white;
}

/* Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #2980b9;
  color: white;
  padding: 1rem 2rem;
}

.header .logo h1 {
  font-size: 1.5rem;
  margin: 0;
}

.nav-links a {
  color: white;
  margin: 0 1rem;
  text-decoration: none;
}

.search-input {
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ddd;
  outline: none;
}

/* Hero Banner */
.hero-banner {
  background: linear-gradient(135deg, #d6e9fc, #2980b9);
  color: white;
  text-align: center;
  padding: 4rem 2rem;
}

.hero-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.hero-description {
  font-size: 1.2rem;
  margin: 1rem 0;
}

.shop-now-btn {
  background: white;
  color: #2980b9;
  border: none;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  border-radius: 4px;
  cursor: pointer;
}

/* Categories */
.categories {
  text-align: center;
  padding: 2rem 1rem;
  color: #333;
}

.categories h2 {
  margin-bottom: 2rem; /* Adds space below the title */
}

.category-grid {
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.category-item {
  text-align: center;
  font-size: 1.2rem;
}

.category-item i {
  font-size: 2rem;
  color: #2980b9; /* Consistent with header and other accents */
  margin-bottom: 0.5rem;
}

/* Featured Games */
.featured-games {
  padding: 2rem 1rem;
  color: black;
  text-align: center;  /* Centers the title */
}

.featured-games h2 {
  margin-bottom: 2rem; /* Adds space below the title */
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  justify-content: center;
  max-width: 1200px;
  margin: 0 auto;
}

.game-card {
  background: white;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  text-align: center;
}

.game-image {
  width: 100%;
  height: auto;
  border-radius: 4px;
}

.game-title {
  color: #2980b9; /* Title color consistent with theme */
}

.game-description {
  color: #555; /* Slightly lighter gray for description */
}

.add-to-cart-btn {
  background: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* Deals Section */
.deals-section {
  background: white;
  padding: 2rem 1rem;
  color: #333;
  text-align: center;  /* Centers the title */
}

.deals-section h2 {
  margin-bottom: 2rem; /* Adds space below the title */
}

.deals-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  justify-content: center;
  max-width: 1200px;
  margin: 0 auto;
}

.deal-card {
  background: white;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  text-align: center;
}

.deal-image {
  width: 100%;
  height: auto;
  border-radius: 4px;
}

.deal-title {
  color: #2980b9; /* Title color consistent with theme */
}

.deal-price {
  color: #555; /* Slightly lighter gray for price */
}

.add-to-cart-btn {
  background: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* Footer */
.footer {
  background: #2980b9;
  color: white;
  text-align: center;
  padding: 1rem 0;
}

.footer-links a {
  color: white;
  margin: 0 0.5rem;
  text-decoration: none;
}

.footer-links a:hover {
  text-decoration: underline; /* Underline on hover for better interaction */
}

</style>
