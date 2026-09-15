import { createRouter, createWebHistory } from 'vue-router';
import TestView from '@/views/TestView.vue';
import NewView from '@/views/NewView.vue';
import HomeView from '@/views/HomeView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/test',
      name: 'Test',
      component: TestView,
      meta: {
        navType: 'test',
        showNav: true, //use false to hide Navigation bar
      },
    },
    {
      path: '/',
      name: 'Home',
      component: HomeView,
      meta: {
        navType: 'public',
        showNav: true,
      },
    },
    {
      path: '/new-view',
      name: 'New View',
      component: NewView,
      meta: {
        navType: 'new',
        showNav: true,
      },
    },
  ],
});

export default router;
