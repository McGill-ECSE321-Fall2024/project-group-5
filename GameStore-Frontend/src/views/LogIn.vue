
<template>
  <div class="page-wrapper">
    <div class="login-container">
      <h1>Welcome to Our Game Store!</h1>
      <p class="sub-heading">Sign in to access your account and explore our games</p>

      <form>
        <div class="form-control">
          <label for="email">Email Address</label>
          <input type="email" id="email" placeholder="Enter your email" />
        </div>
        <div class="form-control">
          <label for="password">Password</label>
          <input type="password" id="password" placeholder="Enter your password" />
        </div>

        <div class="actions">
          <label class="remember-me">
            <input type="checkbox" /> Keep me signed in
          </label>
          <a href="#" class="forgot-password">Forgot Password?</a>
        </div>

        <button type="submit" class="sign-in-btn">Sign In</button>
      </form>

      <div class="divider">
        <span>OR</span>
      </div>

      <!-- Link to Create Account Page -->
      <router-link to="/create-account" class="register-btn">Create an Account</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
};
</script>


<style scoped>
/* Center the entire page */
.page-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f4f4f9;
  margin: 0;
  font-family: Arial, sans-serif;
}

/* Styling the login container */
.login-container {
  background: white;
  padding: 2.5rem;
  border-radius: 12px;
  text-align: center;
  max-width: 420px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
}

/* Heading and subheading */
h1 {
  margin-bottom: 0.5rem;
  font-size: 1.8rem;
  color: black; /* Set heading text color to black */
}

.sub-heading {
  color: black; /* Set sub-heading text color to black */
  font-size: 0.9rem;
  margin-bottom: 1.5rem;
}

/* Form control styles */
.form-control {
  margin-bottom: 1.5rem;
  text-align: left;
}

.form-control label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  font-size: 0.9rem;
  color: black; /* Set label text color to black */
}

.form-control input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  color: black; /* Set input text color to black */
}

/* Actions (checkbox and forgot password link) */
.actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.remember-me {
  font-size: 0.85rem;
  display: flex;
  align-items: center;
  color: black; /* Set remember me text color to black */
}

.forgot-password {
  color: #3498db;
  font-size: 0.85rem;
  text-decoration: none;
}

.forgot-password:hover {
  text-decoration: underline;
}

/* Sign In button */
.sign-in-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #3498db;
  color: white;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-bottom: 1rem;
}

.sign-in-btn:hover {
  background-color: #217dbb;
}

/* Divider between sign-in and create account */
.divider {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 1.5rem 0;
}

.divider span {
  font-size: 0.9rem;
  color: black; /* Set divider text color to black */
  background: white;
  padding: 0 0.5rem;
  position: relative;
}

.divider:before,
.divider:after {
  content: "";
  flex: 1;
  height: 1px;
  background: #ccc;
  margin: 0 1rem;
}

/* Register button */
.register-btn {
  display: block;
  width: 100%;
  padding: 0.75rem;
  background: none;
  border: 2px solid #3498db;
  color: #3498db;
  font-size: 1rem;
  border-radius: 6px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
}

.register-btn:hover {
  background: #3498db;
  color: white;
  border-color: #3498db;
}
</style>
