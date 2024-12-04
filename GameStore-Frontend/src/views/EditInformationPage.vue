<template>
  <div class="edit-container">
    <h1>Edit Information</h1>
    <p>Update your personal details below:</p>

    <form @submit.prevent="handleSubmit">
      <!-- Update Name -->
      <div class="form-control">
        <label for="name">Update Name</label>
        <input
          type="text"
          id="name"
          v-model="name"
          placeholder="Enter your updated name"
        />
      </div>

      <!-- Update Username -->
      <div class="form-control">
        <label for="username">Update Username</label>
        <input
          type="text"
          id="username"
          v-model="username"
          placeholder="Enter your new username"
          @blur="validateUsername"
        />
        <p v-if="usernameError" class="error-message">{{ usernameError }}</p>
      </div>

      <!-- Confirm Old Password -->
      <div class="form-control">
        <label for="old-password">Confirm Old Password</label>
        <input
          type="password"
          id="old-password"
          v-model="oldPassword"
          placeholder="Enter your old password"
        />
        <p v-if="oldPasswordError" class="error-message">{{ oldPasswordError }}</p>
      </div>

      <!-- Add New Password -->
      <div class="form-control">
        <label for="new-password">New Password</label>
        <input
          type="password"
          id="new-password"
          v-model="newPassword"
          placeholder="Enter your new password"
          @input="validatePasswords"
        />
      </div>

      <!-- Confirm New Password -->
      <div class="form-control">
        <label for="confirm-new-password">Confirm New Password</label>
        <input
          type="password"
          id="confirm-new-password"
          v-model="confirmNewPassword"
          placeholder="Re-enter your new password"
          @input="validatePasswords"
        />
        <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
      </div>

      <button type="submit" class="save-btn" :disabled="isSubmitDisabled">Save Changes</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "EditInformationPage",
  data() {
    return {
      name: "",
      username: "",
      oldPassword: "",
      newPassword: "",
      confirmNewPassword: "",
      usernameError: "",
      oldPasswordError: "",
      passwordError: "",
    };
  },
  computed: {
    isSubmitDisabled() {
      return this.usernameError || this.oldPasswordError || this.passwordError;
    },
  },
  methods: {
    async validateUsername() {
      if (!this.username) {
        this.usernameError = ""; // Clear error if username is empty
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
    validatePasswords() {
      if (this.newPassword || this.confirmNewPassword) {
        // Require old password if a new password is entered
        if (!this.oldPassword) {
          this.oldPasswordError = "Old password is required to set a new password.";
        } else {
          this.oldPasswordError = "";
        }

        // Validate new password matching
        if (this.newPassword !== this.confirmNewPassword) {
          this.passwordError = "New passwords do not match.";
        } else {
          this.passwordError = "";
        }
      } else {
        this.oldPasswordError = ""; // Clear error if no new password is entered
        this.passwordError = ""; // Clear error if no new password is entered
      }
    },
    async handleSubmit() {
      this.validatePasswords();

      if (this.isSubmitDisabled) {
        alert("Please fix the errors before submitting.");
        return;
      }

      const payload = {
        ...(this.name && { name: this.name }),
        ...(this.username && { username: this.username }),
        ...(this.oldPassword && { oldPassword: this.oldPassword }),
        ...(this.newPassword && { newPassword: this.newPassword }),
      };

      if (!Object.keys(payload).length) {
        alert("No changes to save.");
        return;
      }

      try {
        const response = await fetch("http://localhost:3000/api/update-information", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload),
        });
        const result = await response.json();
        if (result.success) {
          alert("Information updated successfully!");
        } else {
          alert(result.message || "Update failed.");
        }
      } catch (error) {
        console.error("Error during update:", error);
        alert("An error occurred while updating information. Please try again.");
      }
    },
  },
};
</script>

<style scoped>
.edit-container {
  padding: 2rem;
  text-align: center;
  background: white;
  max-width: 400px;
  margin: auto;
  border-radius: 12px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 1.8rem;
  margin-bottom: 1rem;
}

p {
  font-size: 1rem;
  margin-bottom: 2rem;
}

.form-control {
  margin-bottom: 1.5rem;
  text-align: left;
}

.form-control label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-control input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.error-message {
  color: red;
  font-size: 0.85rem;
  margin-top: 0.5rem;
}

.save-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.save-btn:hover {
  background-color: #217dbb;
}
</style>
