<template>
  <div class="edit-container">
    <h1>Add New Payment</h1>
    <p>Add your new payment details below:</p>

    <form @submit.prevent="handleSubmit">
      <!-- Cardholder Name -->
      <div class="form-control">
        <label for="cardholder-name">Cardholder Name</label>
        <input
          type="text"
          id="cardholder-name"
          v-model="cardholderName"
          placeholder="Enter cardholder's name"
          @input="validateFields"
          required
        />
      </div>

      <!-- Card Number -->
      <div class="form-control">
        <label for="card-number">Card Number</label>
        <input
          type="text"
          id="card-number"
          v-model="cardNumber"
          placeholder="Enter your card number"
          maxlength="16"
          @input="validateFields"
          required
        />
      </div>

      <!-- Expiry Date -->
      <div class="form-control">
        <label for="expiry-date">Expiry Date (MM/YY)</label>
        <input
          type="text"
          id="expiry-date"
          v-model="expiryDate"
          placeholder="MM/YY"
          pattern="^(0[1-9]|1[0-2])\/\d{2}$"
          title="Enter a valid expiry date (MM/YY)"
          @input="validateFields"
          required
        />
      </div>

      <!-- CVV -->
      <div class="form-control">
        <label for="cvv">CVV</label>
        <input
          type="password"
          id="cvv"
          v-model="cvv"
          placeholder="Enter CVV"
          maxlength="3"
          @input="validateFields"
          required
        />
      </div>

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <button type="submit" class="save-btn" :disabled="isSubmitDisabled">Save Payment Info</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "EditPaymentInfoPage",
  data() {
    return {
      cardholderName: "",
      cardNumber: "",
      expiryDate: "",
      cvv: "",
      errorMessage: "",
    };
  },
  computed: {
    isSubmitDisabled() {
      const hasPartialInput =
        this.cardholderName ||
        this.cardNumber ||
        this.expiryDate ||
        this.cvv;

      const allFieldsFilled =
        this.cardholderName &&
        this.cardNumber &&
        this.expiryDate &&
        this.cvv;

      if (hasPartialInput && !allFieldsFilled) {
        this.errorMessage = "All fields are required if updating payment info.";
        return true;
      }

      this.errorMessage = "";
      return false;
    },
  },
  methods: {
    validateFields() {
      // Triggers the computed property to display the error message dynamically
      this.isSubmitDisabled;
    },
    async handleSubmit() {
      if (this.isSubmitDisabled) {
        alert("Please fill in all required fields.");
        return;
      }

      try {
        const response = await fetch("http://localhost:3000/api/update-payment", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            cardholderName: this.cardholderName,
            cardNumber: this.cardNumber,
            expiryDate: this.expiryDate,
            cvv: this.cvv,
          }),
        });

        const result = await response.json();
        if (result.success) {
          alert("Payment info updated successfully!");
        } else {
          alert(result.message || "Payment info update failed.");
        }
      } catch (error) {
        console.error("Error updating payment info:", error);
        alert("An error occurred while updating payment info. Please try again.");
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

.error-message {
  color: red;
  font-size: 0.85rem;
  margin-bottom: 1rem;
}
</style>
