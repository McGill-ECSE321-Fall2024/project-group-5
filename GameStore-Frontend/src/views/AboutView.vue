<template>
  <main class="about-container">
    <section class="about-header">
      <h1 class="about-title">About GameStore</h1>
      <p class="about-description">
        GameStore is your one-stop shop for the best video games across all genres and platforms!
      </p>
      <button @click="shopNow" class="primary-btn">Shop Now</button>
    </section>
  </main>
</template>

<script>
export default {
  name: "AboutView",
  methods: {
    shopNow() {
      this.$router.push("/shop");
    },
  },
};
</script>

<style scoped>
.about-container {
  max-width: 1200px;
  width: 90%;
  margin: auto;
  padding: 2rem;
  background: white;
  border-radius: 12px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.about-header {
  background-color: #f4f4f9;
  padding: 4rem;
  border-radius: 12px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
}

.about-title {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.about-description {
  font-size: 1.25rem;
  color: #777;
  margin-bottom: 2rem;
}

.primary-btn {
  background-color: #ff4500;
  color: white;
  border: none;
  padding: 1rem 2rem;
  font-size: 1.25rem;
  cursor: pointer;
  border-radius: 6px;
  transition: background-color 0.3s, transform 0.2s;
}

.primary-btn:hover {
  background-color: #e63e00;
  transform: scale(1.05);
}

.primary-btn:active {
  transform: scale(0.95);
}
</style>
