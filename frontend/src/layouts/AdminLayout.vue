<script setup>
import { RouterView, useRoute } from 'vue-router';
import AdminNavigation from '@/components/admin/AdminNavigation.vue';

const route = useRoute();
</script>

<template>
  <div class="d-flex w-100 h-100 overflow-hidden bg-light">
    <AdminNavigation :navType="route.meta.navType" />
    <main class="flex-grow-1 d-flex flex-column h-100 overflow-auto bg-white p-4">
      <RouterView />
    </main>
  </div>
</template>
