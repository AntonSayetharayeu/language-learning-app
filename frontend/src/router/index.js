import AdminLayout from '@/layouts/AdminLayout.vue';
import AdminChatsView from '@/views/admin/AdminChatsView.vue';
import AdminDashboardView from '@/views/admin/AdminDashboardView.vue';
import AdminLanguagesView from '@/views/admin/AdminLanguagesView.vue';
import AdminOffersView from '@/views/admin/AdminOffersView.vue';
import AdminPingsView from '@/views/admin/AdminPingsView.vue';
import AdminRatingsView from '@/views/admin/AdminRatingsView.vue';
import AdminRequestsView from '@/views/admin/AdminRequestsView.vue';
import AdminTopicsView from '@/views/admin/AdminTopicsView.vue';
import AdminUserFormView from '@/views/admin/AdminUserFormView.vue';
import AdminUsersView from '@/views/admin/AdminUsersView.vue';
import ArchivedChatsView from '@/views/ArchivedChatsView.vue';
import ChatsView from '@/views/ChatsView.vue';
import ChatView from '@/views/ChatView.vue';
import LoginView from '@/views/LoginView.vue';
import OfferFormView from '@/views/OfferFormView.vue';
import OffersView from '@/views/OffersView.vue';
import PingsView from '@/views/PingsView.vue';
import ProfileView from '@/views/ProfileView.vue';
import RegisterView from '@/views/RegisterView.vue';
import RequestFormView from '@/views/RequestFormView.vue';
import RequestsView from '@/views/RequestsView.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
      meta: { showNav: false },
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { showNav: false },
    },
    {
      path: '/',
      name: 'home',
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
      path: '/request',
      name: 'request-creation',
      component: RequestFormView,
      meta: {
        navType: 'requests',
        showNav: true,
      },
    },
    {
      path: '/request/edit/:id',
      name: 'request-edition',
      component: RequestFormView,
      props: true,
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
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
      meta: {
        navType: 'profile',
        showNav: true,
      },
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminLayout,
      meta: {
        navType: 'admin',
        showNav: false,
      },
      children: [
        {
          path: '',
          name: 'admin-dashboard',
          component: AdminDashboardView,
          meta: {
            navType: 'admin',
          },
        },
        {
          path: 'chats',
          name: 'admin-chats',
          component: AdminChatsView,
          meta: {
            navType: 'admin-chats',
          },
        },
        {
          path: 'chat/:id',
          name: 'admin-chat-details',
          component: ChatView,
          meta: {
            navType: 'admin-chats',
          },
        },
        {
          path: 'users',
          name: 'admin-users',
          component: AdminUsersView,
          meta: {
            navType: 'admin-users',
          },
        },
        {
          path: 'users/create',
          name: 'admin-user-create',
          component: AdminUserFormView,
          meta: {
            navType: 'admin-users',
          },
        },
        {
          path: 'users/edit/:id',
          name: 'admin-user-edit',
          component: AdminUserFormView,
          props: true,
          meta: {
            navType: 'admin-users',
          },
        },
        {
          path: 'offers',
          name: 'admin-offers',
          component: AdminOffersView,
          meta: {
            navType: 'admin-offers',
          },
        },
        {
          path: 'requests',
          name: 'admin-requests',
          component: AdminRequestsView,
          meta: {
            navType: 'admin-requests',
          },
        },
        {
          path: 'pings',
          name: 'admin-pings',
          component: AdminPingsView,
          meta: {
            navType: 'admin-pings',
          },
        },
        {
          path: 'ratings',
          name: 'admin-ratings',
          component: AdminRatingsView,
          meta: {
            navType: 'admin-ratings',
          },
        },
        {
          path: 'topics',
          name: 'admin-topics',
          component: AdminTopicsView,
          meta: {
            navType: 'admin-topics',
          },
        },
        {
          path: 'languages',
          name: 'admin-languages',
          component: AdminLanguagesView,
          meta: {
            navType: 'admin-languages',
          },
        },
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('app_session');

  // Not logged in
  if (!isAuthenticated && to.name !== 'login' && to.name !== 'register') {
    next({ name: 'login' });
  }
  // Logged in
  else if (isAuthenticated && (to.name === 'login' || to.name === 'register')) {
    next({ name: 'chats' });
  } else {
    next();
  }
});

export default router;
