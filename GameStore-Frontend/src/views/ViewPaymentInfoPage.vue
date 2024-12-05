<template>
  <div class="page-wrapper">
    <div class="payment-container">
      <h1>Your Payment Information</h1>

      <table class="payment-table">
        <thead>
          <tr>
            <th>Card Holder</th>
            <th>Card Number</th>
            <th>Expiry Date</th>
            <th>CVV</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(payment, index) in paymentMethods" :key="index">
            <td>{{ payment.cardHolder }}</td>
            <td>{{ maskCardNumber(payment.cardNumber) }}</td>
            <td>{{ payment.expiryDate }}</td>
            <td>{{ maskCVV(payment.cvv) }}</td>
            <td>
              <button class="edit-btn" @click="editPayment(index)">Edit</button>
              <button class="delete-btn" @click="deletePayment(index)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>

      <button class="add-btn" @click="addNewPayment">Add New Payment Method</button>
      
    </div>
  </div>
</template>

<script>
export default {
  name: "ViewPaymentInfoPage",
  data() {
    return {
      paymentMethods: [
        {
          cardHolder: "John Doe",
          cardNumber: "1234567812345678",
          expiryDate: "12/25",
          cvv: "123",
        },
        {
          cardHolder: "Jane Smith",
          cardNumber: "9876543212345678",
          expiryDate: "11/24",
          cvv: "456",
        },
      ],
    };
  },
  methods: {
    maskCardNumber(cardNumber) {
      return `**** **** **** ${cardNumber.slice(-4)}`;
    },
    maskCVV(cvv) {
      return '***'; // Always return masked CVV
    },
    editPayment(index) {
      const selectedPayment = this.paymentMethods[index];
      this.$router.push({
        name: "EditPaymentInfo",
        query: { ...selectedPayment, index },
      });
    },
    deletePayment(index) {
      if (confirm("Are you sure you want to delete this payment method?")) {
        this.paymentMethods.splice(index, 1);
      }
    },
    addNewPayment() {
      this.$router.push({ name: "EditPayment" });
    },
  },
};
</script>

<style scoped>
.page-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 50vh;
  background-color: #f4f4f9;
  margin: 0;
  font-family: Arial, sans-serif;
}

.payment-container {
  text-align: center;
  background: white;
  padding: 5rem;
  border-radius: 12px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  width: 100%;
}

h1 {
  margin-bottom: 2rem;
  font-size: 1.8rem;
  color: black; /* Set heading text color to black */
}

.payment-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1.5rem;
}

.payment-table th,
.payment-table td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: left;
  color: black; /* Set table text color to black */
}

.payment-table th {
  background-color: #f4f4f9;
  font-weight: bold;
}

.payment-table tr:hover {
  background-color: #f9f9f9;
}

.edit-btn,
.delete-btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-right: 0.5rem;
}

.edit-btn {
  background-color: #3498db;
  color: white;
}

.edit-btn:hover {
  background-color: #217dbb;
}

.delete-btn {
  background-color: #e74c3c;
  color: white;
}

.delete-btn:hover {
  background-color: #c0392b;
}

.add-btn {
  padding: 0.75rem 1.5rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
}

.add-btn:hover {
  background-color: #217dbb;
}
</style>
