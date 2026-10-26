<script setup>
import { computed } from 'vue';
import { RouterView, useRoute } from 'vue-router';
import Footer from './components/Footer.vue';
import Navigation from './components/Navigation.vue';

const route = useRoute();
const navType = computed(() => route.meta.navType);

const isAdminRoute = computed(() => route.path.startsWith('/admin'));
</script>

<template>
  <template v-if="!isAdminRoute">
    <Navigation :route="route" :nav-type="navType" v-if="route.meta.showNav" />
    <main role="main" class="px-3">
      <RouterView />
    </main>
    <Footer />
  </template>
  <template v-else>
    <RouterView />
  </template>
</template>
