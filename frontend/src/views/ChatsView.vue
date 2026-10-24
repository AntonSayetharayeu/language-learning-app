<script setup>
import ChatComponent from '@/components/ChatComponent.vue';
import { ref } from 'vue';

//TODO: Mock list should be fetched from backend
const chats = ref([
  { id: 1, username: 'Username1', learning: 'German' },
  { id: 2, username: 'Username2', learning: 'Polish' },
  { id: 3, username: 'Username3', learning: 'English' },
  { id: 4, username: 'Username1', learning: 'German' },
  { id: 5, username: 'Username2', learning: 'Polish' },
  { id: 6, username: 'Username3', learning: 'English' },
  { id: 7, username: 'Username1', learning: 'German' },
  { id: 8, username: 'Username2', learning: 'Polish' },
  { id: 9, username: 'Username3', learning: 'English' },
]);
</script>

<template>
  <div class="container py-4 flex-grow-1">
    <ChatComponent v-for="chat in chats" :key="chat.id" :chat="chat" size="large" />
  </div>
  <button class="archieved-btn rounded-circle shadow-lg" aria-label="archived chats">
    <img src="@/assets/archive.png" alt="icon" class="smaller-img-for-button" />
  </button>
</template>
