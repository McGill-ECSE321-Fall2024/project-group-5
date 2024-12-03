<template>
  <main class="shop">
    <h1>Shop Games</h1>
    
    <!-- Search Bar -->
    <div class="search">
      <label for="search">Search by Name:</label>
      <input type="text" id="search" v-model="searchQuery" placeholder="Search for a game..." />
    </div>

    <!-- Filters Section -->
    <div class="filters">
      <label for="filter">Filter by Genre:</label>
      <select id="filter" v-model="selectedGenre">
        <option value="">All Genres</option>
        <option v-for="genre in genres" :key="genre" :value="genre">{{ genre }}</option>
      </select>

      <label for="console">Filter by Console:</label>
      <select id="console" v-model="selectedConsole">
        <option value="">All Consoles</option>
        <option v-for="console in consoles" :key="console" :value="console">{{ console }}</option>
      </select>
    </div>

    <!-- Games Grid -->
    <div class="games-grid">
      <div v-for="game in filteredGames" :key="game.id" class="game-card">
        <img :src="game.image" :alt="game.name" class="game-image"/>
        <div class="game-info">
          <h3>{{ game.name }}</h3>
          <p class="price">${{ game.price }}</p>
          <div class="game-details">
            <p><strong>Console:</strong> {{ game.console }}</p>
            <p><strong>Genre:</strong> {{ game.genre }}</p>
          </div>

          <!-- Show Reviews Button -->
          <button @click="toggleReviews(game.id)" class="show-reviews-btn">
            {{ game.showReviews ? "Hide Reviews" : "Show Reviews" }}
          </button>

          <!-- Reviews Section (conditionally rendered) -->
          <div v-if="game.showReviews" class="reviews">
            <h4>Reviews</h4>
            <div v-for="review in game.reviews" :key="review.id" class="review">
              <p>{{ review.text }}</p>
              <p>Rating: {{ review.rating }} / 5</p>

              <!-- Like/Dislike Buttons with Counters -->
              <div class="like-dislike-buttons">
                <button @click="likeReview(game.id, review.id)">👍 {{ review.likes }}</button>
                <button @click="dislikeReview(game.id, review.id)">👎 {{ review.dislikes }}</button>
              </div>
            </div>
          </div>

          <!-- Button Container (for spacing between buttons) -->
          <div class="button-container">
            <!-- Add to Cart Button -->
            <button @click="addToCart(game)" class="add-to-cart-btn">Add to Cart</button>

            <!-- Write a Review Button -->
            <button @click="writeReview(game.id)" class="write-review-btn">Write a Review</button>
          </div>
        </div>
      </div>
    </div>
  </main>
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
          price: 29.99,
          image: "/images/adventure-quest.jpg",
          reviews: [
            { id: 1, text: "Amazing game!", rating: 5, likes: 0, dislikes: 0 },
            { id: 2, text: "Good, but a bit repetitive.", rating: 3, likes: 0, dislikes: 0 }
          ],
          showReviews: false,
        },
        {
          id: 2,
          name: "Mystic RPG",
          genre: "RPG",
          console: "PlayStation",
          price: 49.99,
          image: "/images/mystic-rpg.jpg",
          reviews: [
            { id: 3, text: "Great graphics and story!", rating: 4, likes: 0, dislikes: 0 },
            { id: 4, text: "Too easy, not challenging enough.", rating: 2, likes: 0, dislikes: 0 }
          ],
          showReviews: false,
        }
      ],
      genres: ["Adventure", "RPG", "Action", "Strategy"],
      consoles: ["PC", "PlayStation", "Xbox", "Nintendo Switch"]
    };
  },
  computed: {
    filteredGames() {
      let games = this.games;

      // Filter by Name (searchQuery)
      if (this.searchQuery) {
        games = games.filter(game =>
          game.name.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }

      // Filter by Genre
      if (this.selectedGenre) {
        games = games.filter(game => game.genre === this.selectedGenre);
      }

      // Filter by Console
      if (this.selectedConsole) {
        games = games.filter(game => game.console === this.selectedConsole);
      }

      return games;
    }
  },
  methods: {
    addToCart(game) {
      console.log(`Added ${game.name} to the cart.`);
    },

    // Like Review
    likeReview(gameId, reviewId) {
      const game = this.games.find(g => g.id === gameId);
      const review = game.reviews.find(r => r.id === reviewId);
      review.likes++;
      console.log(`Liked review: ${review.text}`);
    },

    // Dislike Review
    dislikeReview(gameId, reviewId) {
      const game = this.games.find(g => g.id === gameId);
      const review = game.reviews.find(r => r.id === reviewId);
      if (review.dislikes < 10) {
        review.dislikes++;
      }
      console.log(`Disliked review: ${review.text}`);
    },

    // Toggle the visibility of reviews
    toggleReviews(gameId) {
      const game = this.games.find(g => g.id === gameId);
      game.showReviews = !game.showReviews;
    },

    // Write a Review
    writeReview(gameId) {
      const game = this.games.find(g => g.id === gameId);
      console.log(`Writing a review for ${game.name}`);
      // Add logic to trigger a review input form or dialog
    }
  }
};
</script>

<style scoped>
.shop {
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 100vh;
  margin: 0 auto;
}

.filters {
  margin-bottom: 1.5rem;
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1.5rem;
  justify-items: center;
  width: 100%;
  max-width: 1200px;
}

.game-card {
  border: 1px solid #ddd;
  padding: 1rem;
  border-radius: 12px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  background-color: #f9f9f9;
  width: 100%;
  max-width: 280px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  height: auto; /* Allow height to grow */
  min-height: 400px; /* Ensure card has minimum height */
}

.game-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.game-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 1rem;
}

.game-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex-grow: 1; /* Allow game info to take remaining space */
}

h1 {
  text-align: center;
  width: 100%;
  margin-bottom: 2rem;
}

h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: #333;
}

.price {
  font-size: 1.1rem;
  color: #4caf50;
  font-weight: bold;
  margin: 0.5rem 0;
}

.game-details {
  margin: 1rem 0;
  font-size: 0.9rem;
  color: #777;
}

.game-details p {
  margin: 0.2rem 0;
}

.show-reviews-btn {
  background-color: #e67e22;
  border: 2px solid #e67e22;
  color: white;
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  font-weight: bold;
  margin-bottom: 1rem;
}

.show-reviews-btn:hover {
  background-color: #f1c40f;
  transform: scale(1.1);
}

.reviews {
  margin-top: 1rem;
  max-height: 200px;
  overflow-y: auto;
  flex-grow: 1; /* Allow reviews section to grow */
}

.review {
  border-top: 1px solid #ccc;
  margin-top: 0.5rem;
  padding-top: 0.5rem;
}

.review p {
  color: #333;
}

.like-dislike-buttons button {
  background-color: #4caf50;
  border: 2px solid #388e3c;
  border-radius: 5px;
  padding: 8px 12px;
  margin: 0.3rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  color: white;
  font-size: 16px;
  font-weight: bold;
}

.like-dislike-buttons button:hover {
  background-color: #66bb6a;
  transform: scale(1.1);
}

.like-dislike-buttons button:active {
  transform: scale(0.95);
}

.like-dislike-buttons button:nth-child(2) {
  background-color: #f44336;
  border: 2px solid #d32f2f;
}

.like-dislike-buttons button:nth-child(2):hover {
  background-color: #ef5350;
}

.like-dislike-buttons button:nth-child(2):active {
  transform: scale(0.95);
}

/* Add to Cart Button */
.add-to-cart-btn {
  background-color: #3498db;
  border: 2px solid #2980b9;
  padding: 10px 20px;
  font-weight: bold;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  margin-bottom: 1rem;
}

.add-to-cart-btn:hover {
  background-color: #2980b9;
  transform: scale(1.05);
}

.add-to-cart-btn:active {
  transform: scale(0.95);
}

/* Write Review Button */
.write-review-btn {
  background-color: #9b59b6;
  border: 2px solid #8e44ad;
  padding: 10px 20px;
  font-weight: bold;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  margin-top: 10px;
}

.write-review-btn:hover {
  background-color: #8e44ad;
  transform: scale(1.05);
}

.write-review-btn:active {
  transform: scale(0.95);
}

.button-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  margin-top: 1rem;
}
</style>
