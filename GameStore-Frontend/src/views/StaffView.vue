<template>
  <div class="staff-dashboard">
    <header class="header">
      <div class="logo">
        <h1>GameTime - Staff Dashboard</h1>
      </div>
      <div class="header-right">
        <input type="text" v-model="searchQuery" placeholder="Search..." class="search-input" />
      </div>
    </header>

    <!-- Manage Games Section -->
    <section class="manage-games">
      <h2>Manage Games</h2>
      <button class="add-game-btn" @click="showAddGameForm = true">Add New Game</button>

      <!-- Game List -->
      <table class="game-list">
        <thead>
          <tr>
            <th>Game Title</th>
            <th>Category</th>
            <th>Price</th>
            <th>Inventory</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in filteredGames" :key="game.id">
            <td>{{ game.title }}</td>
            <td>{{ game.category }}</td>
            <td>{{ game.price }}</td>
            <td>{{ game.inventory }}</td>
            <td>
              <button @click="editGame(game)">Edit</button>
              <button @click="deleteGame(game.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Add New Game Form -->
      <div v-if="showAddGameForm" class="game-form">
        <h3>Add New Game</h3>
        <form @submit.prevent="addGame">
          <div>
            <label for="title">Title:</label>
            <input v-model="newGame.title" type="text" id="title" required />
          </div>
          <div>
            <label for="description">Description:</label>
            <input v-model="newGame.description" type="text" id="description" required />
          </div>
          <div>
            <label for="price">Price:</label>
            <input v-model="newGame.price" type="number" id="price" required />
          </div>
          <div>
            <label for="inventory">Inventory:</label>
            <input v-model="newGame.inventory" type="number" id="inventory" required />
          </div>
          <div>
            <label for="category">Category:</label>
            <select v-model="newGame.category" id="category" required>
              <option value="Action">Action</option>
              <option value="Adventure">Adventure</option>
              <option value="RPG">RPG</option>
            </select>
          </div>
          <button type="submit">Add Game</button>
          <button type="button" @click="cancelAddGame">Cancel</button>
        </form>
      </div>

      <!-- Edit Game Form -->
      <div v-if="showEditGameForm" class="game-form">
        <h3>Edit Game</h3>
        <form @submit.prevent="updateGame">
          <div>
            <label for="title">Title:</label>
            <input v-model="editGameData.title" type="text" id="title" required />
          </div>
          <div>
            <label for="description">Description:</label>
            <input v-model="editGameData.description" type="text" id="description" required />
          </div>
          <div>
            <label for="price">Price:</label>
            <input v-model="editGameData.price" type="number" id="price" required />
          </div>
          <div>
            <label for="inventory">Inventory:</label>
            <input v-model="editGameData.inventory" type="number" id="inventory" required />
          </div>
          <div>
            <label for="category">Category:</label>
            <select v-model="editGameData.category" id="category" required>
              <option value="Action">Action</option>
              <option value="Adventure">Adventure</option>
              <option value="RPG">RPG</option>
            </select>
          </div>
          <button type="submit">Update Game</button>
          <button type="button" @click="cancelEditGame">Cancel</button>
        </form>
      </div>
    </section>

    <!-- Customer Orders Section -->
    <section class="customer-orders">
      <h2>Customer Orders</h2>
      <table class="order-list">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer Name</th>
            <th>Order Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.customerName }}</td>
            <td>{{ order.status }}</td>
            <td>
              <button @click="viewOrderDetails(order)">View Details</button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>

    <!-- Customer Reviews Section -->
    <section class="customer-reviews">
      <h2>Customer Reviews</h2>
      <table class="review-list">
        <thead>
          <tr>
            <th>Game Title</th>
            <th>Customer</th>
            <th>Review</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="review in reviews" :key="review.id">
            <td>{{ review.gameTitle }}</td>
            <td>{{ review.customerName }}</td>
            <td>{{ review.comment }}</td>
            <td>
              <button @click="respondToReview(review)">Respond</button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'StaffDashboard',
  data() {
    return {
      searchQuery: "",
      games: [],
      orders: [],
      reviews: [],
      newGame: {
        title: '',
        description: '',
        price: 0,
        inventory: 0,
        category: 'Action',
      },
      editGameData: null, // Store the game being edited
      showAddGameForm: false,
      showEditGameForm: false,
    };
  },
  computed: {
    // Filtered games based on the search query
    filteredGames() {
      if (this.searchQuery === "") {
        return this.games;
      }
      return this.games.filter(game =>
        game.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        game.category.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    fetchGames() {
      axios.get('/api/games')
        .then(response => {
          this.games = response.data;
        })
        .catch(error => {
          console.error('Error fetching games:', error);
        });
    },
    fetchOrders() {
      axios.get('/api/orders')
        .then(response => {
          this.orders = response.data;
        })
        .catch(error => {
          console.error('Error fetching orders:', error);
        });
    },
    fetchReviews() {
      axios.get('/api/reviews')
        .then(response => {
          this.reviews = response.data;
        })
        .catch(error => {
          console.error('Error fetching reviews:', error);
        });
    },
    addGame() {
      axios.post('/api/games', this.newGame)
        .then(() => {
          this.fetchGames();
          this.showAddGameForm = false;
          this.newGame = { title: '', description: '', price: 0, inventory: 0, category: 'Action' }; // Reset form
        })
        .catch(error => {
          console.error('Error adding game:', error);
        });
    },
    editGame(game) {
      this.editGameData = { ...game }; // Copy game data into edit form
      this.showEditGameForm = true;
    },
    updateGame() {
      axios.put(`/api/games/${this.editGameData.id}`, this.editGameData)
        .then(() => {
          this.fetchGames();
          this.showEditGameForm = false;
        })
        .catch(error => {
          console.error('Error updating game:', error);
        });
    },
    deleteGame(id) {
      axios.delete(`/api/games/${id}`)
        .then(() => {
          this.fetchGames();
        })
        .catch(error => {
          console.error('Error deleting game:', error);
        });
    },
    viewOrderDetails(order) {
      // Logic to view detailed order information
    },
    respondToReview(review) {
      // Logic to respond to a customer review
    },
    cancelAddGame() {
      this.showAddGameForm = false;
    },
    cancelEditGame() {
      this.showEditGameForm = false;
      this.editGameData = null;
    },
  },
  created() {
    this.fetchGames();
    this.fetchOrders();
    this.fetchReviews();
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

.staff-dashboard {
  background-color: white;
  padding: 20px;
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

.search-input {
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ddd;
  outline: none;
}

/* Manage Games */
.manage-games {
  padding: 2rem 0;
}

.manage-games h2 {
  margin-bottom: 1.5rem;
  color: #2980b9;
}

.add-game-btn {
  background: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-game-btn:hover {
  background-color: #1c6691;
}

/* Tables */
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 2rem;
}

th, td {
  padding: 1rem;
  text-align: center;
  border: 1px solid #ddd;
}

th {
  background-color: #2980b9;
  color: white;
}

td {
  background-color: #f9f9f9;
}

button {
  background: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #1c6691;
}

/* Add Game Form */
.game-form {
  background-color: #fff;
  padding: 1.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-top: 1rem;
}

.game-form form {
  display: flex;
  flex-direction: column;
}

.game-form label {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.game-form input, .game-form select {
  padding: 0.5rem;
  border: 1px solid #ddd;
  margin-bottom: 1rem;
}

.game-form button {
  background: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.game-form button:hover {
  background-color: #1c6691;
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
  text-decoration: underline;
}
</style>
