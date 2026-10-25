<script setup>
import ChatComponent from '@/components/ChatComponent.vue';
import { useChats } from '@/services/chatsStore';
import { useRouter } from 'vue-router';

const router = useRouter();
//TODO: Mock list should be fetched from backend
const { activeChats } = useChats();
</script>

<template>
  <div class="container py-4 flex-grow-1">
    <ChatComponent v-for="chat in activeChats" :key="chat.id" :chat="chat" size="large" />
  </div>
  <button
    class="archieved-btn rounded-circle shadow-lg"
    aria-label="archived chats"
    @click="router.push({ name: 'archived-chats' })"
  >
    <img src="@/assets/archive.png" alt="icon" class="small-image" />
  </button>
</template>
