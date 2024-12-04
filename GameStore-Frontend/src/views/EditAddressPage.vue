<template>
  <div class="edit-container">
    <h1 v-if="isEditing">Edit Address</h1>
    <h1 v-else>Add New Address</h1>
    <form @submit.prevent="handleSubmit">
      <div class="form-control">
        <label for="address">Address</label>
        <input
          type="text"
          id="address"
          v-model="address"
          placeholder="Enter your address"
          required
        />
      </div>
      <div class="form-control">
        <label for="city">City</label>
        <input
          type="text"
          id="city"
          v-model="city"
          placeholder="Enter your city"
          required
        />
      </div>
      <div class="form-control">
        <label for="postalCode">Postal Code</label>
        <input
          type="text"
          id="postalCode"
          v-model="postalCode"
          placeholder="Enter your postal code"
          required
        />
      </div>
      <div class="form-control">
        <label for="province">Province</label>
        <input
          type="text"
          id="province"
          v-model="province"
          placeholder="Enter your province"
          required
        />
      </div>
      <div class="form-control">
        <label for="country">Country</label>
        <input
          type="text"
          id="country"
          v-model="country"
          value="Canada"
          disabled
        />
      </div>
      <button type="submit" class="save-btn">
        {{ isEditing ? "Save Changes" : "Add Address" }}
      </button>
    </form>
  </div>
</template>

<script>
export default {
  name: "EditAddressPage",
  data() {
    return {
      address: "",
      city: "",
      postalCode: "",
      province: "",
      country: "Canada",
      isEditing: false,
    };
  },
  mounted() {
    const query = this.$route.query;
    if (query.address) {
      this.address = query.address;
      this.city = query.city;
      this.postalCode = query.postalCode;
      this.province = query.province;
      this.country = query.country || "Canada";
      this.isEditing = true;
    }
  },
  methods: {
    handleSubmit() {
      if (this.isEditing) {
        alert("Address updated successfully!");
      } else {
        alert("New address added successfully!");
      }
      this.$router.push("/view-address");
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
  color: #000; /* Change text color to black */
}

.form-control {
  margin-bottom: 1.5rem;
  text-align: left;
}

.form-control label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #000; /* Change label text color to black */
}

.form-control input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  color: #000; /* Change input text color to black */
}

.save-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #3498db;
  color: white;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.save-btn:hover {
  background-color: #217dbb;
}
</style>
