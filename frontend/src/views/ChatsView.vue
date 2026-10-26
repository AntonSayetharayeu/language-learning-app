<script setup>
import ChatComponent from '@/components/ChatComponent.vue';
import { useChats } from '@/services/chatsStore';
import { useUsers } from '@/services/usersStore';
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const { getActiveChatsByUserId } = useChats();
const { currentUserId } = useUsers();

const userChats = computed(() => getActiveChatsByUserId(currentUserId.value));
</script>

<template>
  <div class="container py-4 flex-grow-1">
    <ChatComponent v-for="chat in userChats" :key="chat.id" :chat="chat" size="large" />
  </div>
  <button
    class="archieved-btn rounded-circle shadow-lg"
    aria-label="archived chats"
    @click="router.push({ name: 'archived-chats' })"
  >
    <img src="@/assets/archive.png" alt="icon" class="small-image" />
  </button>
</template>
