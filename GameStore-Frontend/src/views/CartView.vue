<template>
  <div class="order-page">
    <!-- Left Side: Order Form -->
    <div class="form-container">
      <h1>Checkout</h1>

      <!-- Shipping Address -->
      <h2>Shipping Address</h2>
      <div class="form-group">
        <label>Full Name</label>
        <input type="text" placeholder="Enter your full name" />
      </div>
      <div class="form-group">
        <label>Email</label>
        <input type="email" placeholder="Enter your email" />
      </div>
      <div class="form-group">
        <label>Address</label>
        <input type="text" placeholder="Enter your address" />
      </div>
      <div class="form-row">
        <div class="form-group half-width">
          <label>City</label>
          <input type="text" placeholder="City" />
        </div>
        <div class="form-group half-width">
          <label>Postal Code</label>
          <input
            type="text"
            placeholder="Postal Code"
            v-model="postalCode"
            @blur="validatePostalCode"
          />
        </div>
      </div>
      <div class="form-row">
        <div class="form-group half-width">
          <label>Province</label>
          <select v-model="province">
            <option disabled value="">Province</option>
            <option v-for="province in provinces" :key="province" :value="province">{{ province }}</option>
          </select>
        </div>
        <div class="form-group half-width">
          <label>Country</label>
          <input
            type="text"
            value="Canada"
            readonly
            v-model="country"
          />
        </div>
      </div>
      <p v-if="countryError" class="error-message">The country must be "Canada".</p>

      <div class="checkbox">
        <label>
          <input type="checkbox" v-model="isBillingSameAsShipping" />
          Billing address is the same as shipping address
        </label>
      </div>

      <!-- Billing Address (conditionally rendered) -->
      <div v-if="!isBillingSameAsShipping">
        <h2>Billing Address</h2>
        <div class="form-group">
          <label>Address</label>
          <input type="text" placeholder="Enter your billing address" />
        </div>
        <div class="form-row">
          <div class="form-group half-width">
            <label>City</label>
            <input type="text" placeholder="City" />
          </div>
          <div class="form-group half-width">
            <label>Postal Code</label>
            <input type="text" placeholder="Postal Code" />
          </div>
        </div>
        <div class="form-row">
          <div class="form-group half-width">
            <label>Province</label>
            <select v-model="billingProvince">
              <option disabled value="">Province</option>
              <option v-for="province in provinces" :key="province" :value="province">{{ province }}</option>
            </select>
          </div>
          <div class="form-group half-width">
            <label>Country</label>
            <input type="text" value="Canada" readonly />
          </div>
        </div>
      </div>

      <!-- Payment Information -->
      <h2>Payment Information</h2>
      <div class="form-group">
        <label>Cardholder Name</label>
        <input type="text" placeholder="Cardholder's name" />
      </div>
      <div class="form-group">
        <label>Card Number</label>
        <input type="text" placeholder="Card number" />
      </div>
      <div class="form-row">
        <div class="form-group half-width">
          <label>EXP (MM/YY)</label>
          <input
            type="text"
            placeholder="Expiry date"
            v-model="expirationDate"
            @blur="validateExpirationDate"
          />
        </div>
        <div class="form-group half-width">
          <label>CVV</label>
          <input
            type="text"
            placeholder="CVV"
            v-model="cvv"
            maxlength="3"
            @blur="validateCVV"
          />
        </div>
      </div>

      <button class="primary-button">Confirm Order</button>
    </div>

    <!-- Right Side: Order Summary -->
    <div class="order-summary">
      <h2>Order Summary</h2>
      <div v-for="(item, index) in cartItems" :key="index" class="order-item">
        <p>{{ item.name }}</p>
        <div class="quantity-controls">
          <button @click="updateQuantity(index, 1)">+</button>
          <span>{{ item.quantity }}</span>
          <button @click="updateQuantity(index, -1)">-</button>
        </div>
        <button @click="deleteItem(index)" class="delete-button">Delete</button>
        <span class="item-price">${{ (item.price * item.quantity).toFixed(2) }}</span>
      </div>

      <!-- Taxes and Total -->
      <div class="order-total">
        <div class="order-subtotal">
          <span>Subtotal:</span>
          <span>${{ subtotal.toFixed(2) }}</span>
        </div>
        <div class="order-tax">
          <span>Tax (15%):</span>
          <span>${{ tax.toFixed(2) }}</span>
        </div>
        <div class="order-total-amount">
          <span>Total:</span>
          <span>${{ (subtotal + tax).toFixed(2) }}</span>
        </div>
      </div>
      <button class="secondary-button">Continue Shopping</button>
    </div>
  </div>
</template>
<script>
export default {
  name: "OrderView",
  data() {
    return {
      isBillingSameAsShipping: true,
      country: "Canada", // Locked on Canada
      countryError: false,
      postalCode: "",
      cvv: "",
      expirationDate: "",
      cartItems: [
        { name: "Item 1", price: 50, quantity: 1 },
        { name: "Item 2", price: 75, quantity: 1 },
      ],
      provinces: [
        "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan"
      ],
      province: "",
      billingProvince: "",
    };
  },
  computed: {
    subtotal() {
      return this.cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);
    },
    tax() {
      return this.subtotal * 0.15; // 15% tax
    },
  },
  methods: {
    validatePostalCode() {
      const postalCodePattern = /^[A-Za-z]\d[A-Za-z] \d[A-Za-z]\d$/;
      if (!postalCodePattern.test(this.postalCode)) {
        alert("Postal code must be in the format X0X 0X0");
      }
    },
    validateExpirationDate() {
      const expirationPattern = /^(0[1-9]|1[0-2])\/\d{2}$/;
      if (!expirationPattern.test(this.expirationDate)) {
        alert("Expiration date must be in MM/YY format");
      }
    },
    validateCVV() {
      if (this.cvv.length !== 3) {
        alert("CVV must be 3 digits long");
      }
    },
    updateQuantity(index, change) {
      const item = this.cartItems[index];
      if (item.quantity + change >= 1) {
        item.quantity += change;
      }
    },
    deleteItem(index) {
      this.cartItems.splice(index, 1);
    },
  },
};
</script>

<style scoped>
/* Layout: Split between form and order summary */
.order-page {
  display: flex;
  justify-content: center; /* Center horizontally */
  gap: 2rem;
  padding: 2rem;
  min-height: 100vh; /* Make the container take full height of the viewport */
  background-color: #f4f8fc;
  font-family: Arial, sans-serif;
}

.form-container,
.order-summary {
  width: 45%; /* Adjust to fit both sections */
  background: #ffffff;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 1.8rem;
  color: #000000;
  margin-bottom: 1rem;
}

h2 {
  font-size: 1.4rem;
  margin-bottom: 0.5rem;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.2rem;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.form-row {
  display: flex;
  gap: 1rem;
}

.form-group.half-width {
  flex: 1 1 45%;
}

.checkbox {
  margin: 1rem 0;
}

.error-message {
  color: red;
  font-size: 0.9rem;
}

.primary-button,
.secondary-button {
  width: 100%;
  padding: 1rem;
  margin-top: 1rem;
  border: none;
  background-color: #3498db;
  color: white;
  font-size: 1.1rem;
  border-radius: 8px;
  cursor: pointer;
}

.primary-button:hover,
.secondary-button:hover {
  background-color: #2f5aa8;
}

.order-summary {
  padding: 1rem;
  background: white;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* Order Item Styling */
.order-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.quantity-controls button {
  padding: 0.5rem;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  cursor: pointer;
}

.quantity-controls span {
  padding: 0.5rem;
}

.delete-button {
  background-color: #ff4d4f;
  color: white;
  padding: 0.5rem;
  border-radius: 5px;
  border: none;
}

.order-total {
  margin-top: 2rem;
  font-size: 1.2rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.order-total div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-total-amount {
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Align money values to the right */
.money-amount {
  font-weight: bold;
  text-align: right;
  min-width: 100px;
}

</style>

