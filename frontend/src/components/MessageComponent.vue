<script setup>
import { useUsers } from '@/services/usersStore';
import { computed } from 'vue';

const props = defineProps({
  isAdmin: {
    type: Boolean,
    default: false,
  },
  message: {
    type: Object,
    required: true,
  },
});

const { currentUserId, getUserById } = useUsers();

const isMessageSentVisibility = computed(() => {
  if (props.isAdmin) {
    return getUserById(props.message.senderId).role === 'lecturer';
  }
  return props.message.senderId === currentUserId.value;
});

const formatMessageTime = (timestamp) => {
  const date = new Date(timestamp);

  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();

  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');

  return `${day}/${month}/${year} ${hours}:${minutes}`;
};
</script>

<template>
  <div
    class="d-flex mb-3"
    :class="isMessageSentVisibility ? 'justify-content-end' : 'justify-content-start'"
  >
    <div
      class="message-bubble p-3 rounded-3 shadow-sm position-relative"
      :class="isMessageSentVisibility ? 'bg-light text-dark border' : 'bg-white text-dark border'"
    >
      <p class="mb-1 text-break">{{ message.text }}</p>
      <span v-if="message.time" class="message-time text-muted d-block text-end">{{
        formatMessageTime(message.time)
      }}</span>
    </div>
  </div>
</template>
