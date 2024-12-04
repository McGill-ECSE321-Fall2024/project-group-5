<script setup>
import { RouterLink, RouterView } from 'vue-router';
import { ref } from 'vue';

// Sidebar visibility state
const isSidebarOpen = ref(false);

// Profile dropdown visibility state
const isProfileDropdownOpen = ref(false);

// Toggle the sidebar visibility
const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

// Toggle the profile dropdown visibility
const toggleProfileDropdown = () => {
  isProfileDropdownOpen.value = !isProfileDropdownOpen.value;
};
</script>

<template>
  <div class="container">
    <!-- Top Navigation Bar -->
    <header class="top-bar">
      <!-- Top-Right Icons -->
      <div class="top-right">
        <!-- Cart Icon -->
        <RouterLink to="/cart" class="cart-icon">
          <i class="fas fa-shopping-cart"></i>
        </RouterLink>

        <!-- Profile Icon -->
        <div class="profile-container">
          <i class="fas fa-user-circle profile-icon" @click="toggleProfileDropdown"></i>
          <div v-if="isProfileDropdownOpen" class="profile-dropdown">
            <RouterLink to="/profile">View Profile</RouterLink>
            <RouterLink to="/settings">Settings</RouterLink>
            <RouterLink to="/logout">Logout</RouterLink>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Layout -->
    <div class="main-layout">
      <!-- Sidebar -->
      <aside :class="['sidebar', { open: isSidebarOpen }]">
        <nav>
          <RouterLink to="/">Home</RouterLink>
          <RouterLink to="/about">About</RouterLink>
          <RouterLink to="/events">Events</RouterLink>
          <RouterLink to="/shop">Shop</RouterLink>
          <RouterLink to="/manager">Manager</RouterLink>
        </nav>
      </aside>

      <!-- Hamburger Menu Toggle -->
      <button class="toggle-button" @click="toggleSidebar">
        <span class="hamburger">
          <span></span>
          <span></span>
          <span></span>
        </span>
      </button>

      <!-- Main Content -->
      <main class="content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped>
/* Global Box Sizing */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* Container */
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  font-family: Arial, sans-serif;
  background-color: #333;
  color: white;
}

/* Top Navigation Bar */
.top-bar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: #222;
  position: sticky;
  top: 0;
  z-index: 1000;
}

/* Right Top Icons */
.top-right {
  display: flex;
  align-items: center;
  gap: 1rem;
}

/* Cart Icon */
.cart-icon {
  font-size: 1.8rem;
  color: white;
  text-decoration: none;
}

.cart-icon:hover {
  color: #ff5722;
}

/* Profile Container */
.profile-container {
  position: relative;
}

.profile-icon {
  font-size: 2rem;
  cursor: pointer;
}

.profile-dropdown {
  position: absolute;
  top: 45px;
  right: 0;
  background-color: #222;
  border: 1px solid #444;
  border-radius: 5px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2);
  z-index: 10;
  overflow: hidden;
}

.profile-dropdown a {
  display: block;
  padding: 0.5rem 1rem;
  color: white;
  text-decoration: none;
}

.profile-dropdown a:hover {
  background-color: #444;
}

/* Main Layout */
.main-layout {
  display: flex;
  flex-grow: 1;
}

/* Sidebar */
.sidebar {
  width: 200px;
  background-color: #222;
  padding: 1rem;
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  transform: translateX(-100%);
  transition: transform 0.3s ease;
  z-index: 1000;
  overflow-y: auto;
}

.sidebar.open {
  transform: translateX(0);
}

/* Hamburger Menu */
.toggle-button {
  position: absolute;
  top: 1rem;
  left: 1rem; /* Matches your preferred placement */
  background: #444;
  border: none;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1100;
}

.toggle-button:hover {
  background-color: #555;
}

.hamburger span {
  display: block;
  width: 25px;
  height: 3px;
  background-color: white;
  margin: 5px 0;
}

/* Sidebar Navigation Links */
.sidebar nav a {
  display: block;
  padding: 0.5rem;
  color: white;
  text-decoration: none;
}

.sidebar nav a:hover {
  background-color: #444;
}

/* Content */
.content {
  flex-grow: 1;
  padding: 1rem;
  background-color: #333;
  color: white;
}
</style>
