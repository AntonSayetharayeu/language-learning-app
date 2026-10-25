import ArchivedChatsView from '@/views/ArchivedChatsView.vue';
import ChatsView from '@/views/ChatsView.vue';
import ChatView from '@/views/ChatView.vue';
import OfferFormView from '@/views/OfferFormView.vue';
import OffersView from '@/views/OffersView.vue';
import PingsView from '@/views/PingsView.vue';
import RequestsView from '@/views/RequestsView.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: { name: 'chats' },
    },
    {
      path: '/chats',
      name: 'chats',
      component: ChatsView,
      meta: {
        navType: 'chats',
        showNav: true, //use false to hide Navigation bar
      },
    },
    {
      path: '/chats/archived',
      name: 'archived-chats',
      component: ArchivedChatsView,
      meta: {
        navType: 'chats',
        showNav: true,
      },
    },
    {
      path: '/chat/:id',
      name: 'chat',
      component: ChatView,
      props: true, // Automatically passes parameters form URL e.g. route.params.id in ChatView.vue
      meta: {
        navType: 'chats',
        showNav: true,
      },
    },
    {
      path: '/offers',
      name: 'offers',
      component: OffersView,
      meta: {
        navType: 'offers',
        showNav: true,
      },
    },
    {
      path: '/offer',
      name: 'offer-creation',
      component: OfferFormView,
      meta: {
        navType: 'offers',
        showNav: true,
      },
    },
    {
      path: '/offer/edit/:id',
      name: 'offer-edition',
      component: OfferFormView,
      props: true,
      meta: {
        navType: 'offers',
        showNav: true,
      },
    },
    {
      path: '/requests',
      name: 'requests',
      component: RequestsView,
      meta: {
        navType: 'requests',
        showNav: true,
      },
    },
    {
      path: '/pings',
      name: 'pings',
      component: PingsView,
      meta: {
        navType: 'pings',
        showNav: true,
      },
    },
  ],
});

export default router;
