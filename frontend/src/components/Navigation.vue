<script setup>
import { computed } from 'vue';
import { useUsers } from '@/services/usersStore';
import Debugger from './Debugger.vue';

defineProps({
  route: {
    type: Object,
    required: true,
  },
  navType: {
    type: String,
    required: true,
  },
});

const { getCurrentUser } = useUsers();

const navLinks = computed(() => {
  const isStudent = getCurrentUser()?.role === 'student';

  const offersLink = { navType: 'offers', to: '/offers', label: 'OFFERS' };
  const requestsLink = { navType: 'requests', to: '/requests', label: 'REQUESTS' };

  const middleLinks = isStudent ? [requestsLink, offersLink] : [offersLink, requestsLink];

  return [
    { navType: 'chats', to: '/chats', label: 'CHATS' },
    ...middleLinks,
    { navType: 'pings', to: '/pings', label: 'PINGS' },
  ];
});
</script>

<template>
  <Debugger :showDebugger="false" />
  <nav class="navbar bg-success-subtle p-2">
    <div class="container-fluid d-flex justify-content-start">
      <div class="d-flex align-items-center gap-4">
        <a class="navbar-brand" href="#">LLM</a>
        <ul class="nav nav-underline">
          <li class="nav-item" v-for="link in navLinks" :key="link.navType">
            <RouterLink
              class="nav-link text-secondary"
              :class="{ active: navType === link.navType }"
              :to="link.to"
              >{{ link.label }}</RouterLink
            >
          </li>
        </ul>
      </div>
      <RouterLink to="/profile" class="ms-auto">
        <img src="@/assets/user.png" alt="profile" class="image rounded-circle cursor-pointer" />
      </RouterLink>
    </div>
  </nav>
</template>
