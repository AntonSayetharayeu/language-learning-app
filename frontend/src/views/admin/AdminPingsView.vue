<script setup>
import { computed } from 'vue';
import { usePings } from '@/services/pingsStore';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import AdminPingComponent from '@/components/admin/AdminPingComponent.vue';

const { pings } = usePings();
const { getUserById } = useUsers();
const { DEFAULT_IMAGE } = useProfiles();

const enrichedPings = computed(() => {
  return pings.value
    .map((ping) => {
      const sender = getUserById(ping.senderId) || {};
      const receiver = getUserById(ping.receiverId) || {};

      return {
        ...ping,
        sender: {
          username: sender.username || sender.login || `User #${ping.senderId}`,
          imgUrl: sender.imgUrl || DEFAULT_IMAGE,
        },
        receiver: {
          username: receiver.username || receiver.login || `User #${ping.receiverId}`,
          imgUrl: receiver.imgUrl || DEFAULT_IMAGE,
        },
      };
    })
    .sort((a, b) => b.createdAt - a.createdAt);
});
</script>

<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">Pings List</h3>
    <div class="d-flex flex-column">
      <AdminPingComponent v-for="ping in enrichedPings" :key="ping.id" :ping="ping" />
      <div v-if="!enrichedPings.length" class="text-center py-5 text-muted">No pings found.</div>
    </div>
  </div>
</template>
