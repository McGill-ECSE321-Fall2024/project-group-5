import { createRouter, createWebHistory } from 'vue-router';
import OrderView from '../views/OrderView.vue'; // Import the new component

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home', // You can change this to 'home' or whatever you prefer
      component: OrderView, // You can change this to the component you want to show on the home page
    },
    {
      path: '/order',
      name: 'order',
      component: OrderView,
    },
    // Add other routes as needed
  ],
});

export default router;
