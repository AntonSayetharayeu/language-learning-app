<script setup>
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
</script>

<template>
  <Debugger :showDebugger="false" />
  <nav class="navbar bg-success-subtle p-2">
    <div class="container-fluid d-flex justify-content-start">
      <div class="d-flex align-items-center gap-4">
        <a class="navbar-brand" href="#">LLM</a>
        <ul class="nav nav-underline">
          <li class="nav-item">
            <RouterLink
              class="nav-link text-secondary"
              :class="{ active: navType === 'chats' }"
              to="/chats"
              >CHATS</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link text-secondary"
              :class="{ active: navType === 'offers' }"
              to="/offers"
              >OFFERS</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link text-secondary"
              :class="{ active: navType === 'requests' }"
              to="/requests"
              >REQUESTS</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link text-secondary"
              :class="{ active: navType === 'pings' }"
              to="/pings"
              >PINGS</RouterLink
            >
          </li>
        </ul>
      </div>
      <img src="@/assets/user.png" alt="profile" class="image ms-auto rounded-circle" />
    </div>
  </nav>
</template>
