<script setup>
import Debugger from './Debugger.vue';

defineProps({
  route: {
    type: RouteLocationNormalizedLoaded,
    required: true,
  },
  navType: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <Debugger />
  <nav>
    <div class="bg-success-subtle p-2">
      <div class="nav nav-fill nav-justified nav-underline w-75">
        <div class="nav-item">
          <RouterLink
            class="nav-link text-secondary"
            :class="{ active: navType === 'public' }"
            to="/"
            >Home</RouterLink
          >
        </div>
        <div class="nav-item">
          <RouterLink
            class="nav-link text-secondary"
            :class="{ active: navType === 'new' }"
            to="/new-view"
            >New View</RouterLink
          >
        </div>
        <div class="nav-item">
          <RouterLink
            class="nav-link text-secondary"
            :class="{ active: navType === 'test' }"
            to="/test"
            >Test something</RouterLink
          >
        </div>
      </div>
    </div>
  </nav>
</template>
