<template>
  <div class="games-container">
    <!-- Header Section -->
    <header class="header">
      <div class="header-left">
        <h1 class="store-title">Shop Games</h1>
      </div>
      <div class="header-right">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="Search for games..."
          class="search-input"
        />
      </div>
    </header>

    <!-- Filters -->
    <div class="filter-console">
      <div class="console-icons">
        <button
          @click="setConsoleFilter('PC')"
          :class="{'active': selectedConsole === 'PC'}"
          class="console-icon"
        >
          <i class="fas fa-desktop"></i> PC
        </button>
        <button
          @click="setConsoleFilter('PlayStation')"
          :class="{'active': selectedConsole === 'PlayStation'}"
          class="console-icon"
        >
          <i class="fab fa-playstation"></i> PlayStation
        </button>
        <button
          @click="setConsoleFilter('Xbox')"
          :class="{'active': selectedConsole === 'Xbox'}"
          class="console-icon"
        >
          <i class="fab fa-xbox"></i> Xbox
        </button>
        <button
          @click="setConsoleFilter('Nintendo')"
          :class="{'active': selectedConsole === 'Nintendo'}"
          class="console-icon"
        >
          <i class="fab fa-nintendo-switch"></i> Nintendo
        </button>
      </div>
    </div>

    <div class="filter-category">
      <select v-model="selectedGenre" class="filter-select">
        <option value="">All Genres</option>
        <option v-for="genre in genres" :key="genre" :value="genre">{{ genre }}</option>
      </select>
    </div>

    <!-- Games Grid -->
    <div class="games-list">
      <div v-for="game in filteredGames" :key="game.id" class="game-card">
        <h3 class="game-title">{{ game.name }}</h3>
        <p class="game-description">
          <strong>Console:</strong> {{ game.console }} |
          <strong>Genre:</strong> {{ game.genre }}
        </p>
        <button @click="toggleReviews(game.id)" class="toggle-reviews-btn">
          {{ game.showReviews ? "Hide Reviews" : "Show Reviews" }}
        </button>

        <!-- Reviews Section -->
        <div v-if="game.showReviews" class="reviews">
          <h4 class="reviews-header">Reviews</h4>
          <div v-for="review in game.reviews" :key="review.id" class="review">
            <p class="review-text">{{ review.text }}</p>
            <p class="review-rating">Rating: {{ review.rating }} / 5</p>
            <div class="like-dislike-buttons">
              <button @click="likeReview(game.id, review.id)" class="like-btn">
                👍 {{ review.likes }}
              </button>
              <button @click="dislikeReview(game.id, review.id)" class="dislike-btn">
                👎 {{ review.dislikes }}
              </button>
            </div>
          </div>
          <button @click="writeReview(game.id)" class="write-review-btn">Write a Review</button>
        </div>

        <!-- Add to Cart Button -->
        <button @click="addToCart(game)" class="add-to-cart-btn">Add to Cart</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ShopView",
  data() {
    return {
      searchQuery: "",
      selectedGenre: "",
      selectedConsole: "",
      games: [
        {
          id: 1,
          name: "Adventure Quest",
          genre: "Adventure",
          console: "PC",
          reviews: [
            { id: 1, text: "Amazing game!", rating: 5, likes: 0, dislikes: 0 },
            { id: 2, text: "Good, but a bit repetitive.", rating: 3, likes: 0, dislikes: 0 },
          ],
          showReviews: false,
        },
        {
          id: 2,
          name: "Mystic RPG",
          genre: "RPG",
          console: "PlayStation",
          reviews: [
            { id: 3, text: "Great graphics and story!", rating: 4, likes: 0, dislikes: 0 },
            { id: 4, text: "Too easy, not challenging enough.", rating: 2, likes: 0, dislikes: 0 },
          ],
          showReviews: false,
        },
      ],
      genres: ["Adventure", "RPG", "Action", "Puzzle", "Strategy"],
    };
  },
  methods: {
    addToCart(game) {
      alert(`${game.name} has been added to your cart!`);
    },
    toggleReviews(gameId) {
      const game = this.games.find((g) => g.id === gameId);
      game.showReviews = !game.showReviews;
    },
    likeReview(gameId, reviewId) {
      const game = this.games.find((g) => g.id === gameId);
      const review = game.reviews.find((r) => r.id === reviewId);
      review.likes++;
    },
    dislikeReview(gameId, reviewId) {
      const game = this.games.find((g) => g.id === gameId);
      const review = game.reviews.find((r) => r.id === reviewId);
      if (review.dislikes < 10) {
        review.dislikes++;
      }
    },
    writeReview(gameId) {
      const game = this.games.find((g) => g.id === gameId);
      alert(`Write a review for ${game.name}`);
    },
    setConsoleFilter(consoleType) {
      this.selectedConsole = this.selectedConsole === consoleType ? "" : consoleType;
    },
  },
  computed: {
    filteredGames() {
      return this.games.filter((game) => {
        const matchesSearch = game.name.toLowerCase().includes(this.searchQuery.toLowerCase());
        const matchesGenre = this.selectedGenre ? game.genre === this.selectedGenre : true;
        const matchesConsole = this.selectedConsole ? game.console === this.selectedConsole : true;
        return matchesSearch && matchesGenre && matchesConsole;
      });
    },
  },
};
</script>

<style scoped>
/* Body and Container */
body, html {
  height: 100%;
  margin: 0;
  background-color: #f4f4f9;
}

.games-container {
  max-width: 1200px;
  width: 90%;
  margin: auto;
  padding: 2rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* Header */
.header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
}

.store-title {
  font-size: 2.5rem;
  color: #333;
  text-align: center;
  margin-bottom: 1rem;
}

.search-input {
  padding: 0.75rem;
  font-size: 1rem;
  width: 350px;
  border-radius: 6px;
  border: 1px solid #ddd;
}

.search-input:focus {
  border-color: #3498db;
  outline: none;
}

/* Filters */
.filter-console {
  margin-bottom: 2rem;
}

.console-icons {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.console-icon {
  padding: 0.75rem;
  font-size: 1.5rem;
  border: 2px solid transparent;
  border-radius: 8px;
  cursor: pointer;
}

.console-icon:hover,
.console-icon.active {
  background-color: #3498db;
  color: white;
}

.filter-category {
  margin-bottom: 1.5rem;
}

.filter-select {
  padding: 0.75rem;
  font-size: 1rem;
  width: 80%;
  border-radius: 6px;
  border: 1px solid #ddd;
}

/* Games Grid */
.games-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.game-card {
  padding: 1.5rem;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 100%;
}

.game-title {
  font-size: 1.5rem;
  color: #333;
}

.game-description {
  font-size: 1rem;
  color: #777;
  margin-bottom: 1rem;
}

.toggle-reviews-btn {
  margin: 1rem 0;
  padding: 0.75rem;
  background-color: #f39c12;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.reviews {
  margin-top: 1rem;
  text-align: left;
}

.reviews-header {
  color: #333;
}

.review-text {
  color: #333;
}

.review-rating {
  color: #333;
}

.like-dislike-buttons {
  display: flex;
  justify-content: flex-start;
  gap: 1rem;
  margin-top: 0.5rem;
}

.like-btn, .dislike-btn, .write-review-btn, .add-to-cart-btn {
  padding: 10px 20px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-weight: bold;
  transition: transform 0.2s, background-color 0.3s;
}

.like-btn {
  background-color: #4caf50;
  color: white;
}

.like-btn:hover {
  background-color: #66bb6a;
  transform: scale(1.05);
}

.like-btn:active {
  transform: scale(0.95);
}

.dislike-btn {
  background-color: #f44336;
  color: white;
}

.dislike-btn:hover {
  background-color: #ef5350;
  transform: scale(1.05);
}

.dislike-btn:active {
  transform: scale(0.95);
}

.write-review-btn {
  background-color: #9b59b6;
  color: white;
}

.write-review-btn:hover {
  background-color: #8e44ad;
  transform: scale(1.05);
}

.write-review-btn:active {
  transform: scale(0.95);
}

.add-to-cart-btn {
  background-color: #3498db;
  color: white;
}

.add-to-cart-btn:hover {
  background-color: #2980b9;
  transform: scale(1.05);
}

.add-to-cart-btn:active {
  transform: scale(0.95);
}
</style>
