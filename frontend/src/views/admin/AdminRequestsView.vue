<script setup>
import { computed } from 'vue';
import { useRequests } from '@/services/requestsStore';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import AdminRequestComponent from '@/components/admin/AdminRequestComponent.vue';

const { requests } = useRequests();
const { getUserById } = useUsers();
const { DEFAULT_IMAGE } = useProfiles();

const enrichedRequests = computed(() => {
  return requests.value.map((request) => {
    const student = getUserById(request.studentId) || {};
    return {
      ...request,
      student: {
        username: student.username || student.login || `User #${request.studentId}`,
        imgUrl: student.imgUrl || DEFAULT_IMAGE,
      },
    };
  });
});
</script>

<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">Requests List</h3>
    <div class="d-flex flex-column">
      <AdminRequestComponent v-for="req in enrichedRequests" :key="req.id" :request="req" />
      <div v-if="!enrichedRequests.length" class="text-center py-5 text-muted">
        No requests found
      </div>
    </div>
  </div>
</template>
