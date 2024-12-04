<template>
  <div class="manager-page">
    <h1>Manager Dashboard</h1>

    <!-- Employee Management Section -->
    <section class="employee-management">
      <h2>Manage Employees</h2>

      <!-- Employee List -->
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee in employees" :key="employee.id">
            <td>{{ employee.id }}</td>
            <td>{{ employee.name }}</td>
            <td>{{ employee.email }}</td>
            <td>{{ employee.role }}</td>
            <td>
              <button @click="editEmployee(employee)">Edit</button>
              <button @click="deleteEmployee(employee.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Add New Employee -->
      <h3>Add New Employee</h3>
      <form @submit.prevent="addEmployee">
        <div>
          <label for="name">Name:</label>
          <input v-model="newEmployee.name" type="text" id="name" required />
        </div>
        <div>
          <label for="email">Email:</label>
          <input v-model="newEmployee.email" type="email" id="email" required />
        </div>
        <div>
          <label for="role">Role:</label>
          <select v-model="newEmployee.role" id="role" required>
            <option value="Employee">Employee</option>
            <option value="Manager">Manager</option>
            <!-- Add more roles as needed -->
          </select>
        </div>
        <button type="submit">Add Employee</button>
      </form>

      <!-- Edit Employee Modal -->
      <div v-if="showEditModal" class="modal">
        <div class="modal-content">
          <h3>Edit Employee</h3>
          <form @submit.prevent="updateEmployee">
            <div>
              <label for="edit-name">Name:</label>
              <input v-model="currentEmployee.name" type="text" id="edit-name" required />
            </div>
            <div>
              <label for="edit-email">Email:</label>
              <input v-model="currentEmployee.email" type="email" id="edit-email" required />
            </div>
            <div>
              <label for="edit-role">Role:</label>
              <select v-model="currentEmployee.role" id="edit-role" required>
                <option value="Employee">Employee</option>
                <option value="Manager">Manager</option>
                <!-- Add more roles as needed -->
              </select>
            </div>
            <button type="submit">Save Changes</button>
            <button type="button" @click="cancelEdit">Cancel</button>
          </form>
        </div>
      </div>
    </section>

    <!-- Additional management sections can be added here -->

  </div>
</template>

<script>
export default {
  name: 'ManagerPage',
  data() {
    return {
      employees: [], // List of employees
      newEmployee: {
        name: '',
        email: '',
        role: 'Employee',
      },
      showEditModal: false,
      currentEmployee: null,
    };
  },
  methods: {
    fetchEmployees() {
      // Fetch the list of employees from the backend API
      // Example using axios:
      // axios.get('/api/employees')
      //   .then(response => {
      //     this.employees = response.data;
      //   })
      //   .catch(error => {
      //     console.error('Error fetching employees:', error);
      //   });
    },
    addEmployee() {
      // Send a request to add a new employee to the backend
      // axios.post('/api/employees', this.newEmployee)
      //   .then(response => {
      //     this.employees.push(response.data);
      //     // Reset the newEmployee form
      //     this.newEmployee = { name: '', email: '', role: 'Employee' };
      //   })
      //   .catch(error => {
      //     console.error('Error adding employee:', error);
      //   });
    },
    editEmployee(employee) {
      // Set the currentEmployee to the selected employee and show the edit modal
      this.currentEmployee = { ...employee }; // Create a copy to avoid direct mutation
      this.showEditModal = true;
    },
    updateEmployee() {
      // Send a request to update the employee details
      // axios.put(`/api/employees/${this.currentEmployee.id}`, this.currentEmployee)
      //   .then(response => {
      //     // Find and update the employee in the list
      //     const index = this.employees.findIndex(emp => emp.id === this.currentEmployee.id);
      //     if (index !== -1) {
      //       this.employees.splice(index, 1, response.data);
      //     }
      //     this.showEditModal = false;
      //   })
      //   .catch(error => {
      //     console.error('Error updating employee:', error);
      //   });
    },
    cancelEdit() {
      this.showEditModal = false;
      this.currentEmployee = null;
    },
    deleteEmployee(id) {
      // Send a request to delete the employee
      // axios.delete(`/api/employees/${id}`)
      //   .then(() => {
      //     // Remove the employee from the list
      //     this.employees = this.employees.filter(emp => emp.id !== id);
      //   })
      //   .catch(error => {
      //     console.error('Error deleting employee:', error);
      //   });
    },
  },
  created() {
    // Fetch employees when the component is created
    this.fetchEmployees();
  },
};
</script>

<style scoped>
.manager-page {
  padding: 20px;
}

.employee-management {
  margin-top: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

table th,
table td {
  padding: 8px;
  border: 1px solid #ccc;
}

modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  margin: auto;
  position: relative;
  top: 25%;
  width: 50%;
}

form div {
  margin-bottom: 10px;
}

form label {
  display: inline-block;
  width: 100px;
}

form input,
form select {
  width: calc(100% - 110px);
}
</style>
