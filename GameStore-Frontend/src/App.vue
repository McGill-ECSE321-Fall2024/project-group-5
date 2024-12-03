<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <div class="container">
    <!-- Left Sidebar (Navigation) -->
    <aside class="sidebar">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/cart">Cart</RouterLink>
        <RouterLink to="/about">About</RouterLink>
        <RouterLink to="/events">Events</RouterLink>
        <RouterLink to="/shop">Shop</RouterLink>
      </nav>
    </aside>

    <!-- Right Main Content -->
    <main class="content">
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
/* Container for Layout */
.container {
  display: flex;
  min-height: 100vh;
  background-color: #333;
}

/* Sidebar (Left Navigation) */
.sidebar {
  width: 200px;
  background-color: #222;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.sidebar nav {
  display: flex;
  flex-direction: column;
}

.sidebar a {
  color: white;
  padding: 0.5rem;
  text-decoration: none;
  margin-bottom: 1rem;
  font-weight: bold;
}

.sidebar a.router-link-active {
  text-decoration: underline;
}

/* Main Content (Right Side) */
.content {
  flex-grow: 1;
  padding: 2rem;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
}

.content h1, .content p {
  margin-bottom: 2rem;
}

/* Style for Links */
a:hover {
  color: #ff5722;
  cursor: pointer;
}
</style>
