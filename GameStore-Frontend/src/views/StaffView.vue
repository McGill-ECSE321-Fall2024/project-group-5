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
            <th>Console</th> <!-- Added Console Column -->
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in paginatedGames" :key="game.id">
            <td>{{ game.title }}</td>
            <td>{{ game.category }}</td>
            <td>{{ game.price }}</td>
            <td>{{ game.console }}</td> <!-- Display Console -->
            <td>
              <button @click="editGame(game)" class="btn edit-btn">Edit</button>
              <button @click="deleteGame(game.id)" class="btn delete-btn">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Pagination -->
      <div class="pagination">
        <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 1" class="pagination-btn">Previous</button>
        <span class="pagination-info">Page {{ currentPage }} of {{ totalPages }}</span>
        <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages" class="pagination-btn">Next</button>
      </div>

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
            <label for="category">Category:</label>
            <select v-model="newGame.category" id="category" required>
              <option value="Action">Action</option>
              <option value="Adventure">Adventure</option>
              <option value="RPG">RPG</option>
            </select>
          </div>
          <div>
            <label for="console">Console:</label> <!-- New Console Field -->
            <select v-model="newGame.console" id="console" required>
              <option value="PlayStation">PlayStation</option>
              <option value="Xbox">Xbox</option>
              <option value="PC">PC</option>
              <option value="Nintendo Switch">Nintendo Switch</option>
            </select>
          </div>
          <button type="submit" class="btn submit-btn">Add Game</button>
          <button type="button" @click="cancelAddGame" class="btn cancel-btn">Cancel</button>
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
            <label for="category">Category:</label>
            <select v-model="editGameData.category" id="category" required>
              <option value="Action">Action</option>
              <option value="Adventure">Adventure</option>
              <option value="RPG">RPG</option>
            </select>
          </div>
          <div>
            <label for="console">Console:</label> <!-- Console Field for Edit Form -->
            <select v-model="editGameData.console" id="console" required>
              <option value="PlayStation">PlayStation</option>
              <option value="Xbox">Xbox</option>
              <option value="PC">PC</option>
              <option value="Nintendo Switch">Nintendo Switch</option>
            </select>
          </div>
          <button type="submit" class="btn submit-btn">Update Game</button>
          <button type="button" @click="cancelEditGame" class="btn cancel-btn">Cancel</button>
        </form>
      </div>
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
        category: 'Action',
        console: 'PlayStation', // Added Console to new game data
      },
      editGameData: null,
      showAddGameForm: false,
      showEditGameForm: false,
      currentPage: 1,
      pageSize: 5,
    };
  },
  computed: {
    filteredGames() {
      if (this.searchQuery === "") {
        return this.games;
      }
      return this.games.filter(game =>
        game.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        game.category.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        game.console.toLowerCase().includes(this.searchQuery.toLowerCase()) // Filter by console
      );
    },
    paginatedGames() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredGames.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredGames.length / this.pageSize);
    }
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
          this.newGame = { title: '', description: '', price: 0, category: 'Action', console: 'PlayStation' }; // Reset form
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
    goToPage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
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
/* Global Styles */
body {
  font-family: 'Arial', sans-serif;
  background-color: #f5faff;
  color: #333;
}

h1, h2 {
  /* color: #2980b9; */
  color: white;
}

/* Header Styling */
.header {
  background-color: #2980b9;
  color: white;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header .logo h1 {
  margin: 0;
}

.header .search-input {
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
  width: 200px;
}

/* Manage Games Section */
.manage-games {
  padding: 2rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  color: black;
}

.manage-games h2 {
  color: black; /* Ensures "Manage Games" header is black */
}

.manage-games button {
  background-color: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.manage-games button:hover {
  background-color: #1c6c8c;
}

.game-list {
  width: 100%;
  margin-top: 2rem;
  border-collapse: collapse;
}

.game-list th, .game-list td {
  padding: 1rem;
  text-align: left;
}

.game-list th {
  background-color: #2980b9;
  color: white;
}

.pagination {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination .pagination-btn {
  background-color: #2980b9;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.pagination .pagination-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.game-form {
  background-color: #ffffff;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-top: 2rem;
}

.game-form label {
  font-weight: bold;
}

.game-form input, .game-form select {
  width: 100%;
  padding: 0.5rem;
  margin-top: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-btn {
  background-color: #2980b9;
  color: white;
}

.submit-btn:hover {
  background-color: #1c6c8c;
}

.cancel-btn {
  background-color: #ccc;
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}

.pagination-info {
  font-size: 1rem;
  color: #333;
}
</style>
