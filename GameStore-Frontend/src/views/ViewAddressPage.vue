<template>
  <div class="page-wrapper">
    <div class="address-container">
      <h1>Your Address Information</h1>
      <table class="address-table">
        <thead>
          <tr>
            <th>Address</th>
            <th>City</th>
            <th>Postal Code</th>
            <th>Province</th>
            <th>Country</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(address, index) in addresses" :key="index">
            <td>{{ address.address }}</td>
            <td>{{ address.city }}</td>
            <td>{{ address.postalCode }}</td>
            <td>{{ address.province }}</td>
            <td>{{ address.country }}</td>
            <td>
              <button @click="editAddress(index)" class="edit-btn">Edit</button>
              <button @click="removeAddress(index)" class="remove-btn">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
      <button class="add-btn" @click="navigateToAddNewAddress">Add New Address</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ViewAddressPage",
  data() {
    return {
      addresses: [
        {
          address: "123 Main Street",
          city: "Toronto",
          postalCode: "M1A 1A1",
          province: "Ontario",
          country: "Canada",
        },
        {
          address: "456 Elm Street",
          city: "Vancouver",
          postalCode: "V5K 0A1",
          province: "British Columbia",
          country: "Canada",
        },
      ],
    };
  },
  methods: {
    navigateToAddNewAddress() {
      this.$router.push("/edit-address"); // Redirects to edit-address page for adding a new address
    },
    editAddress(index) {
      const selectedAddress = this.addresses[index];
      // Navigate to edit-address page with selected address details as query parameters
      this.$router.push({
        path: "/edit-address",
        query: { ...selectedAddress },
      });
    },
    removeAddress(index) {
      this.addresses.splice(index, 1);
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

.address-container {
  text-align: center;
  background: white;
  padding: 2rem;
  border-radius: 12px;
  max-width: 800px;
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 1.8rem;
  margin-bottom: 1.5rem;
  color: black; /* Set heading text color to black */
}

.address-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1.5rem;
}

.address-table th,
.address-table td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: left;
  color: black; /* Set table text color to black */
}

.address-table th {
  background-color: #f9f9f9;
  font-weight: bold;
}

.address-table tbody tr:hover {
  background-color: #f4f4f9;
}

.edit-btn {
  padding: 0.5rem 1rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 0.5rem;
}

.edit-btn:hover {
  background-color: #217dbb;
}

.remove-btn {
  padding: 0.5rem 1rem;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.remove-btn:hover {
  background-color: #c0392b;
}

.add-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #3498db;
  color: white;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.add-btn:hover {
  background-color: #217dbb;
}
</style>
