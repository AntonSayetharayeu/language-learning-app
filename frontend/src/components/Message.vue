<script setup>
defineProps({
  message: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <h3>{{ message == null ? 'Omg, give me message!' : message }}</h3>
</template>
