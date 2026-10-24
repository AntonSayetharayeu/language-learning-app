<script setup>
defineProps({
  message: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div
    class="d-flex mb-3"
    :class="message.isOutgoing ? 'justify-content-end' : 'justify-content-start'"
  >
    <div
      class="message-bubble p-3 rounded-3 shadow-sm position-relative"
      :class="message.isOutgoing ? 'bg-light text-dark border' : 'bg-white text-dark border'"
    >
      <p class="mb-1 text-break">{{ message.text }}</p>
      <span v-if="message.time" class="message-time text-muted d-block text-end">{{
        message.time
      }}</span>
    </div>
  </div>
</template>
