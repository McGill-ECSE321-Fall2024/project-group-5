<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/shop">Shop</RouterLink>
      <RouterLink to="/cart">Cart</RouterLink>
      <RouterLink to="/about">About</RouterLink>
      <RouterLink to="/events">Events</RouterLink>
    </nav>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  background-color: #333;
  color: white;
  padding: 1rem;
}

nav {
  display: flex;
  justify-content: space-around;
}

nav a {
  color: white;
  text-decoration: none;
}

nav a.router-link-active {
  font-weight: bold;
  text-decoration: underline;
}
</style>
