<template>
  <div class="page-wrapper">
    <div class="info-container">
      <h1>Your Personal Information</h1>
      <div class="info-box">
        <h2>Current Information</h2>
        <p><strong>Name:</strong> John Doe</p>
        <p><strong>Username:</strong> johndoe123</p>
        <p><strong>Email:</strong> johndoe@example.com</p>
      </div>
      <button class="edit-btn" @click="navigateToEditInformation">Edit Information</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ViewInformationPage",
  methods: {
    navigateToEditInformation() {
      this.$router.push("/edit-info");
    },
  },
};
</script>

<style scoped>
.page-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 0vh;
  background-color: #f4f4f9;
  margin: 0;
  font-family: Arial, sans-serif;
}

.info-container {
  text-align: center;
  background: white;
  padding: 7rem 8rem; /* Increased padding for a wider look */
  border-radius: 12px;
  max-width: 7000px; /* Increased the max-width to take more space */
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
  width: 100%; /* Ensures responsiveness on smaller screens */
}

h1 {
  font-size: 2rem;
  margin-bottom: 2rem;
  color: black; /* Set heading text color to black */
}

.info-box {
  background: #f9f9f9;
  padding: 2rem;
  border-radius: 8px;
  border: 1px solid #ddd;
  text-align: left;
  margin-bottom: 2rem;
}

.info-box h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: black; /* Set subheading text color to black */
}

.info-box p {
  font-size: 1rem;
  margin: 0.5rem 0;
  color: black; /* Set paragraph text color to black */
}

.edit-btn {
  width: 100%;
  padding: 1rem;
  background-color: #3498db;
  color: white;
  font-size: 1.2rem; /* Slightly larger font size for better readability */
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.edit-btn:hover {
  background-color: #217dbb;
}
</style>
