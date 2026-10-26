<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useChats } from '@/services/chatsStore';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import AdminChatComponent from '@/components/admin/AdminChatComponent.vue';

const router = useRouter();
const { chats } = useChats();
const { getUserById } = useUsers();
const { DEFAULT_IMAGE } = useProfiles();

const enrichedChats = computed(() => {
  return chats.value.map((chat) => {
    const lecturer = getUserById(chat.lecturerId) || {};
    const student = getUserById(chat.studentId) || {};

    return {
      ...chat,
      lecturer: {
        username: lecturer.username || lecturer.login || `User #${chat.lecturerId}`,
        imgUrl: lecturer.imgUrl || DEFAULT_IMAGE,
      },
      student: {
        username: student.username || student.login || `User #${chat.studentId}`,
        imgUrl: student.imgUrl || DEFAULT_IMAGE,
      },
    };
  });
});

const openChat = (id) => {
  router.push({ name: 'admin-chat-details', params: { id } });
};
</script>

<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">Manage List</h3>
    <div class="d-flex flex-column">
      <AdminChatComponent
        v-for="chat in enrichedChats"
        :key="chat.id"
        :chat="chat"
        @click="openChat(chat.id)"
      />

      <div v-if="!enrichedChats.length" class="text-center py-5 text-muted">No chats found</div>
    </div>
  </div>
</template>
