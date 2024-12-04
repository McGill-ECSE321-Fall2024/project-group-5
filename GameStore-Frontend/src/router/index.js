import { createRouter, createWebHistory } from 'vue-router';

import OrderView from '../views/OrderView.vue'; // Import the new component

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/order',
      name: 'order',
      component: OrderView,
    }
     // Add the new route
  ],
});

export default router;
