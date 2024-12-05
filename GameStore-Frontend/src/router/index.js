import { createRouter, createWebHistory } from 'vue-router';
// Import your components
import HomeView from '@/views/HomeView.vue';
import AboutView from '@/views/AboutView.vue';
import ShopView from '@/views/ShopView.vue';
import LogIn from '@/views/LogIn.vue';
import CartView from '@/views/CartView.vue';
import AccountInfoPage from '@/views/AccountInfoPage.vue';
import CreateAccountPage from '@/views/CreateAccountPage.vue';
import EditAddressPage from '@/views/EditAddressPage.vue';
import EditInformationPage from '@/views/EditInformationPage.vue';
import EditPaymentInfoPage from '@/views/EditPaymentInfoPage.vue';
import ViewAddressPage from '@/views/ViewAddressPage.vue';
import ViewInformationPage from '@/views/ViewInformationPage.vue';
import ViewPaymentInfoPage from '@/views/ViewPaymentInfoPage.vue';
import ManagerPage from '@/components/ManagerPage.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: HomeView, name: 'Home' },
  { path: '/about', component: AboutView, name: 'About' },
  { path: '/shop', component: ShopView, name: 'Shop' },
  { path: '/login', component: LogIn, name: 'LogIn' },
  { path: '/cart', component: CartView, name: 'Cart' },
  { path: '/profile', component: AccountInfoPage, name: 'Profile' },
  { path: '/create-account', component: CreateAccountPage, name: 'CreateAccount' },
  { path: '/edit-address', component: EditAddressPage, name: 'EditAddress' },
  { path: '/edit-info', component: EditInformationPage, name: 'EditInfo' },
  { path: '/edit-payment', component: EditPaymentInfoPage, name: 'EditPayment' },
  { path: '/view-address', component: ViewAddressPage, name: 'ViewAddress' },
  { path: '/view-info', component: ViewInformationPage, name: 'ViewInfo' },
  { path: '/view-payment', component: ViewPaymentInfoPage, name: 'ViewPayment' },
  ],
});

export default router;