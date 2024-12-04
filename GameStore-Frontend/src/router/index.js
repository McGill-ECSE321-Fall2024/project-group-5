import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import ShopView from '../views/ShopView.vue';
import CartView from '../views/CartView.vue';
import AboutView from '../views/AboutView.vue';
import EventsView from '../views/EventsView.vue'; // Add EventsView import
import ManagerPage from '@/components/ManagerPage.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/shop', name: 'shop', component: ShopView },
    { path: '/cart', name: 'cart', component: CartView },
    { path: '/about', name: 'about', component: AboutView },
    { path: '/events', name: 'events', component: EventsView }, // Add Events route
    { path: '/manager', name: 'Manager', component: ManagerPage }, 
  ],
});

export default router;
