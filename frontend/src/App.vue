<script setup>
import { RouterView, useRoute } from 'vue-router';
import Navigation from './components/Navigation.vue';
import Footer from './components/Footer.vue';
import { computed } from 'vue';

const route = useRoute();
const navType = computed(() => route.meta.navType);
</script>

<template>
  <Navigation :route="route" :nav-type="navType" v-if="route.meta.showNav" />
  <main role="main" class="container py-4 px-3 mx-auto">
    <RouterView />
  </main>
  <Footer />
</template>
