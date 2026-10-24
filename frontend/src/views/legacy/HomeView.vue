<script setup>
import Navigation from '@/components/Navigation.vue';
import { ref } from 'vue';

const msgs = ref([
  'Hello, Bootstrap and Vite!',
  'Thank You for click!',
  'Omg, really???',
  'Stooooooop XD',
]);
const id = ref(0);

const changeMessage = () => {
  id.value = ++id.value % 4;
  console.log(id.value);
};
</script>

<template>
  <h1 id="title-to-be-changed">{{ msgs[id] }}</h1>
  <p>{{ id }}</p>
  <button class="btn btn-info" @click="changeMessage">Primary button</button>
</template>
