<script setup>
import { useUsers } from '@/services/usersStore';

defineProps({
  message: {
    type: Object,
    required: true,
  },
});

const { currentUserId } = useUsers();

const formatMessageTime = (timestamp) => {
  const date = new Date(timestamp);

  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
  const year = date.getFullYear();

  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');

  return `${day}/${month}/${year} ${hours}:${minutes}`;
};
</script>

<template>
  <div
    class="d-flex mb-3"
    :class="message.senderId === currentUserId ? 'justify-content-end' : 'justify-content-start'"
  >
    <div
      class="message-bubble p-3 rounded-3 shadow-sm position-relative"
      :class="
        message.senderId === currentUserId
          ? 'bg-light text-dark border'
          : 'bg-white text-dark border'
      "
    >
      <p class="mb-1 text-break">{{ message.text }}</p>
      <span v-if="message.time" class="message-time text-muted d-block text-end">{{
        formatMessageTime(message.time)
      }}</span>
    </div>
  </div>
</template>
