<template>
  <main>
    <h1>Event Registration</h1>
    <h2>New Event</h2>
    <div>
      <select v-model="newEventType">
        <option value="IN_PERSON">In person</option>
        <option value="ONLINE">Online</option>
      </select>
      <input type="text" placeholder="Name" v-model="newEventName" />
      <input type="date" placeholder="Date" v-model="newEventDate" />
      <input type="time" placeholder="Start Time" v-model="newEventStartTime" />
      <input type="time" placeholder="End Time" v-model="newEventEndTime" />
      <input type="text" placeholder="Registration Limit" v-model="newEventRegLimit" />
      <input type="text" placeholder="Location" v-model="newEventLocation" />
      <button id="create-btn" @click="createEvent" :disabled="!isEventValid()">Create Event</button>
      <button class="danger-btn" @click="clearInputs">Clear</button>
    </div>
    <h2>Events</h2>
    <table>
      <tbody>
        <tr>
          <th>Name</th>
          <th>Date</th>
          <th>Type</th>
        </tr>
        <tr v-for="e in events" :key="e.name">
          <td>{{ e.name }}</td>
          <td>{{ e.date }}</td>
          <td>{{ e.type === "IN_PERSON" ? "In person" : "Online" }}</td>
        </tr>
      </tbody>
    </table>
  </main>
</template>

<script>
import axios from "axios";

// Create an Axios client
const axiosClient = axios.create({
  baseURL: "http://localhost:5173", // Backend base URL
});

export default {
  name: "EventsView",
  data() {
    return {
      events: [],
      newEventType: "IN_PERSON",
      newEventName: null,
      newEventDate: null,
      newEventStartTime: null,
      newEventEndTime: null,
      newEventRegLimit: null,
      newEventLocation: null,
    };
  },
  async created() {
    try {
      const response = await axiosClient.get("/events");
      this.events = response.data; // Update events with data from backend
    } catch (e) {
      console.error("Error loading events:", e);
      // Optionally display a user-friendly error message
    }
  },
  methods: {
    async createEvent() {
      const newEvent = {
        type: this.newEventType,
        name: this.newEventName,
        date: this.newEventDate,
        startTime: this.newEventStartTime,
        endTime: this.newEventEndTime,
        registrationLimit: this.newEventRegLimit,
        location: this.newEventLocation,
      };
      try {
        const response = await axiosClient.post("/events", newEvent);
        this.events.push(response.data); // Add the new event to the list
      } catch (e) {
        console.error("Error creating event:", e);
        // Optionally display a user-friendly error message
      }
      this.clearInputs();
    },
    clearInputs() {
      this.newEventType = "IN_PERSON";
      this.newEventName = null;
      this.newEventDate = null;
      this.newEventStartTime = null;
      this.newEventEndTime = null;
      this.newEventRegLimit = null;
      this.newEventLocation = null;
    },
    isEventValid() {
      return (
        this.newEventName &&
        this.newEventDate &&
        this.newEventStartTime &&
        this.newEventRegLimit &&
        this.newEventLocation
      );
    },
  },
};
</script>

<style>
main {
  display: flex;
  flex-direction: column;
  align-items: stretch;
}

h2 {
  padding-top: 1em;
  text-decoration: underline;
}

table {
  border-collapse: collapse;
  width: 100%;
}

td,
th {
  border: 1px solid var(--color-border);
  padding: 0.25em;
}

.danger-btn {
  border: 1px solid red;
  color: red;
}
</style>
