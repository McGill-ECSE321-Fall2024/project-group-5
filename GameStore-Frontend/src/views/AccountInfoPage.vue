<template>
  <div class="page-wrapper">
    <div class="account-container">
      <h1>Edit Your Account Details</h1>
      <div class="account-boxes">

        <div class="account-box" @click="navigateTo('view-information')">

          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#3498db" width="48px" height="48px">
    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 17.93c-4.95 0-8.93-4.02-8.93-8.93S7.05 4.07 12 4.07s8.93 4.02 8.93 8.93-4.02 8.93-8.93 8.93zM11 6h2v6h-2zm0 8h2v2h-2z"/>
  </svg>
          <h2>Manage Information</h2>
          <p>Update your name, email, and other personal details.</p>
        </div>

        <div class="account-box" @click="navigateTo('view-address')">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#3498db" width="48px" height="48px">
    <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5S10.62 6.5 12 6.5 14.5 7.62 14.5 9 13.38 11.5 12 11.5z"/>
  </svg>
          <h2>Manage Address</h2>
          <p>Change or add your shipping and billing addresses.</p>
        </div>

        <div class="account-box" @click="navigateTo('view-payment-info')">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#3498db" width="48px" height="48px">
    <path d="M21 4H3C1.9 4 1 4.9 1 6v12c0 1.1.9 2 2 2h18c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm-1 4H4v2h16V8zm0 8H4v-4h16v4z"/>
  </svg>
          <h2>Manage Payment Info</h2>
          <p>Manage your payment methods and billing details.</p>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AccountInfoPage",
  methods: {
    navigateTo(route) {
      // Navigate to the appropriate route
      this.$router.push(`/${route}`);
    },
  },
};
</script>

<style scoped>
.page-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f4f4f9;
  margin: 0;
  font-family: Arial, sans-serif;
}

.account-container {
  text-align: center;
  max-width: 800px;
}

h1 {
  font-size: 2rem;
  margin-bottom: 2rem;
  color: #333;
}

.account-boxes {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.account-box {
  flex: 1;
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  text-align: center;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.account-box:hover {
  transform: translateY(-5px);
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.2);
}

.box-icon {
  width: 50px;
  height: 50px;
  margin-bottom: 1rem;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

p {
  color: #666;
  font-size: 0.9rem;
}

.box-icon {
  width: 50px;
  height: 50px;
  margin-bottom: 1rem;
  fill: #3498db; /* Blue color from the Create Account page */
}
</style>
