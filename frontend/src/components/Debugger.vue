<script setup>
const debug = ref(false);
defineProps({
  someVar: {
    type: String,
    required: false,
  },
});
</script>

<template v-if="debug">DEBUG — Current someVar: "{{ someVar }}"</template>

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
