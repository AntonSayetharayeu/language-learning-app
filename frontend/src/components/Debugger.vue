<script setup>
import { ref } from 'vue';

const debug = ref(false);
defineProps({
  someVar: {
    type: String,
    required: false,
  },
  showDebugger: {
    type: Boolean,
    required: false,
  },
});
</script>

<template>
  <p v-if="showDebugger">DEBUG — Current someVar: "{{ someVar }}"</p>
</template>

<style>
debugger {
  background: black;
  color: lime;
  padding: 10px;
  z-index: 9999;
  position: fixed;
  top: 0;
  left: 0;
}
</style>
