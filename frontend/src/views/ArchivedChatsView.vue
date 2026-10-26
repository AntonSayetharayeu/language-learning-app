<script setup>
import { useRouter } from 'vue-router';
import ChatComponent from '@/components/ChatComponent.vue';
import { useChats } from '@/services/chatsStore';
import { useUsers } from '@/services/usersStore';
import { computed } from 'vue';

const router = useRouter();
const { getArchivedChatsByUserId } = useChats();
const { currentUserId } = useUsers();

const userArchivedChats = computed(() => getArchivedChatsByUserId(currentUserId.value));
</script>

<template>
  <div class="container py-4 flex-grow-1">
    <ChatComponent
      v-for="chat in userArchivedChats"
      :key="chat.id"
      :chat="chat"
      size="large"
      :isArchived="true"
    />
  </div>

  <button
    class="archieved-btn rounded-circle shadow-lg"
    aria-label="active chats"
    @click="router.push({ name: 'chats' })"
  >
    <img src="@/assets/chats.png" alt="icon" class="small-image" />
  </button>
</template>
