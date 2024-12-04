<template>
  <div class="page-wrapper">
    <div class="register-container">
      <h1>Create an Account</h1>
      <p class="sub-heading">Fill in the details below to register:</p>

      <form @submit.prevent="handleSubmit">
        <div class="form-control">
          <label for="first-name">First Name*</label>
          <input type="text" id="first-name" v-model="firstName" placeholder="Enter your first name" required />
        </div>
        <div class="form-control">
          <label for="last-name">Last Name*</label>
          <input type="text" id="last-name" v-model="lastName" placeholder="Enter your last name" required />
        </div>
        <div class="form-control">
          <label for="username">Username*</label>
          <input
            type="text"
            id="username"
            v-model="username"
            placeholder="Enter your Username"
            @blur="validateUsername"
            required
          />
          <p v-if="usernameError" class="error-message">{{ usernameError }}</p>
        </div>
        <div class="form-control">
          <label for="email">Email Address*</label>
          <input
            type="email"
            id="email"
            v-model="email"
            placeholder="Enter your email"
            @blur="validateEmail"
            required
          />
          <p v-if="emailError" class="error-message">{{ emailError }}</p>
        </div>

        <div class="form-control">
          <label for="password">Password*</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="Enter your password"
            required
          />
        </div>
        <div class="form-control">
          <label for="confirm-password">Re-enter Password*</label>
          <input
            type="password"
            id="confirm-password"
            v-model="confirmPassword"
            placeholder="Re-enter your password"
            required
          />
        </div>
        <p v-if="passwordError" class="error-message">{{ passwordError }}</p>

        <button type="submit" class="register-btn" :disabled="isSubmitDisabled">Register</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "CreateAccountPage",
  data() {
    return {
      firstName: "",
      lastName: "",
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
      usernameError: "",
      emailError: "",
      passwordError: "",
    };
  },
  computed: {
    isSubmitDisabled() {
      return this.usernameError || this.emailError || this.passwordError;
    },
  },
  methods: {
    async validateUsername() {
      if (!this.username) {
        this.usernameError = "Username is required.";
        return;
      }
      try {
        const response = await fetch("http://localhost:3000/api/check-username", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ username: this.username }),
        });
        const result = await response.json();
        if (result.exists) {
          this.usernameError = "Username already exists.";
        } else {
          this.usernameError = "";
        }
      } catch (error) {
        console.error("Error validating username:", error);
        this.usernameError = "Error checking username. Please try again.";
      }
    },
    async validateEmail() {
      if (!this.email) {
        this.emailError = "Email is required.";
        return;
      }
      try {
        const response = await fetch("http://localhost:3000/api/check-email", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email }),
        });
        const result = await response.json();
        if (result.exists) {
          this.emailError = "Email already exists.";
        } else {
          this.emailError = "";
        }
      } catch (error) {
        console.error("Error validating email:", error);
        this.emailError = "Error checking email. Please try again.";
      }
    },
    validatePasswords() {
      if (this.password !== this.confirmPassword) {
        this.passwordError = "Passwords do not match.";
      } else {
        this.passwordError = "";
      }
    },
    async handleSubmit() {
      this.validatePasswords();
      if (this.isSubmitDisabled) {
        alert("Please fix the errors before submitting.");
        return;
      }
      try {
        const response = await fetch("http://localhost:3000/api/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            firstName: this.firstName,
            lastName: this.lastName,
            username: this.username,
            email: this.email,
            password: this.password,
          }),
        });
        const result = await response.json();
        if (result.success) {
          alert("Account created successfully!");
        } else {
          alert(result.message || "Registration failed.");
        }
      } catch (error) {
        console.error("Error during registration:", error);
        alert("An error occurred during registration. Please try again.");
      }
    },
  },
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

/* Styling the register container */
.register-container {
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
  color: #000; /* Change text color to black */
}

.sub-heading {
  color: #000; /* Change text color to black */
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
  color: #000; /* Change label text color to black */
}

.form-control input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  color: #000; /* Change input text color to black */
}

/* Error message */
.error-message {
  color: red;
  font-size: 0.85rem;
  margin-bottom: 1rem;
}

/* Register button */
.register-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #3498db;
  color: white;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.register-btn:hover {
  background-color: #217dbb;
}
</style>
