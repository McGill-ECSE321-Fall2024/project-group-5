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
  isProfileDropdown.value = !isProfileDropdown.value;
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
          <RouterLink to="/shop">Shop</RouterLink>
          <RouterLink to="/login">Log In</RouterLink>
          <RouterLink to="/cart">Cart</RouterLink>
          <RouterLink to="/profile">Account Info</RouterLink>
          <RouterLink to="/create-account">Create Account</RouterLink>
          <RouterLink to="/edit-address">Edit Address</RouterLink>
          <RouterLink to="/edit-info">Edit Info</RouterLink>
          <RouterLink to="/edit-payment">Edit Payment Info</RouterLink>
          <RouterLink to="/view-address">View Address</RouterLink>
          <RouterLink to="/view-info">View Info</RouterLink>
          <RouterLink to="/view-payment">View Payment Info</RouterLink>
          <RouterLink to="/about">About</RouterLink>
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
  background-color: white; /* Set background to white */
  color: black; /* Text color is black */
}

/* Top Navigation Bar */
.top-bar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: #fff; /* Set taskbar background to white */
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 1px solid #ddd; /* Subtle border for separation */
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
  color: #3498db;
  text-decoration: none;
}

.cart-icon:hover {
  color: #217dbb;
}

/* Profile Container */
.profile-container {
  position: relative;
}

.profile-icon {
  font-size: 2rem;
  cursor: pointer;
  color: #222; /* Dark profile icon */
}

.profile-dropdown {
  position: absolute;
  top: 45px;
  right: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 10;
  overflow: hidden;
}

.profile-dropdown a {
  display: block;
  padding: 0.5rem 1rem;
  color: black; /* Black text for links */
  text-decoration: none;
}

.profile-dropdown a:hover {
  background-color: #f4f4f9;
}

/* Main Layout */
.main-layout {
  display: flex;
  flex-grow: 1;
  justify-content: center; /* Center content horizontally */
  margin-top: 1rem; /* Space at the top */
}

/* Sidebar */
.sidebar {
  width: 220px;
  background-color: #f4f4f9; /* Light background for sidebar */
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
  left: 1rem;
  background: #3498db;
  border: none;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1100;
  transition: left 0.3s ease; /* Add transition for moving the button */
}

.toggle-button:hover {
  background-color: #217dbb;
}

/* Move the hamburger button within the sidebar */
.sidebar.open + .toggle-button {
  left: 220px; /* Keep button aligned to the right side of the sidebar */
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
  padding: 0.75rem;
  color: black;
  text-decoration: none;
  border-radius: 6px;
}

.sidebar nav a:hover {
  background-color: #e7e7e7;
}

/* Content */
.content {
  flex-grow: 1;
  display: flex;
  justify-content: center; /* Horizontally center */
  align-items: center; /* Vertically center */
  padding: 1rem;
  background-color: white; /* Set content background to white */
  color: black; /* Text color is black */
  width: 100%;
  box-sizing: border-box;
  margin: 0 auto; /* Center the content horizontally */
  min-height: 100vh; /* Ensure the content takes at least full screen height */
  position: relative; /* Ensure the container has a defined position */
}

/* Ensure the child content area (e.g. RouterView) is properly centered */
.content > * {
  max-width: 100%; /* Ensure the content doesn't overflow */
  width: 100%; /* Set full width of the container */
  display: flex;
  justify-content: center; /* Center child content horizontally */
  align-items: center; /* Center child content vertically */
  flex-direction: column; /* Stack content if needed */
}

/* Optional: To limit the width of the content, you can set a max-width */
.content > * > .container {
  max-width: 1200px; /* Constrain width for larger screens */
  width: 100%; /* Ensure it scales properly */
}


/* Responsive adjustments */
@media (max-width: 768px) {
  .sidebar {
    position: relative;
    transform: none;
  }

  .toggle-button {
    z-index: 1200;
  }

  .content {
    margin-left: 0; /* No left margin for mobile screens */
    max-width: 100%; /* Full width on mobile */
  }
}
</style>
