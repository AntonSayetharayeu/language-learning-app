<script setup>
import { computed, onMounted, onUnmounted, ref } from 'vue';
import { useUsers } from '@/services/usersStore';
import Debugger from './Debugger.vue';
import { useRouter } from 'vue-router';
import { useProfiles } from '@/services/profilesStore.js';

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

const router = useRouter();

const { getCurrentUser, logout } = useUsers();
const currentUser = computed(() => getCurrentUser());

const isDropdownOpen = ref(false);

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const closeDropdown = (e) => {
  if (!e.target.closest('.profile-dropdown')) {
    isDropdownOpen.value = false;
  }
};

const goToProfile = () => {
  isDropdownOpen.value = false;
  router.push('/profile');
};

onMounted(() => document.addEventListener('click', closeDropdown));
onUnmounted(() => document.removeEventListener('click', closeDropdown));

const handleLogout = () => {
  logout();
  router.push('/login');
};

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
        <a class="navbar-brand fs-5 fw-bold text-success" href="/">LLM</a>
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
      <div
        class="ms-auto position-relative profile-dropdown d-flex align-items-center gap-2 cursor-pointer"
        @click="toggleDropdown"
      >
        <span class="fw-semibold text-secondary d-none d-sm-block">
          {{ currentUser?.username || currentUser?.login || 'Profile' }}
        </span>

        <img
          src="@/assets/user.png"
          alt="profile"
          class="image rounded-circle border border-2 border-white shadow-sm"
        />

        <ul
          class="dropdown-menu dropdown-menu-end shadow border-0 mt-2 position-absolute"
          :class="{ show: isDropdownOpen }"
          style="top: 100%; right: 0; left: auto; min-width: 150px"
        >
          <li>
            <RouterLink
              to="/profile"
              class="dropdown-item d-flex align-items-center gap-2 py-2"
              @click.prevent="goToProfile"
            >
              <i class="bi bi-person"></i> My Profile
            </RouterLink>
          </li>
          <li><hr class="dropdown-divider" /></li>
          <li>
            <a
              href="#"
              class="dropdown-item d-flex align-items-center gap-2 py-2 text-danger"
              @click.prevent="handleLogout"
            >
              <i class="bi bi-box-arrow-right"></i> Log out
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
