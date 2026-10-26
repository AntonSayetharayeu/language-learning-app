<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import ManageRequestComponent from '@/components/ManageRequestComponent.vue';
import RequestComponent from '@/components/RequestComponent.vue';
import { useRequests } from '@/services/requestsStore';
import { useUsers } from '@/services/usersStore';

const router = useRouter();
const { requests, getRequestByStudentId, deleteRequest } = useRequests();
const { currentUserId, getCurrentUser } = useUsers();

// Students manage the requests they created; lecturers browse every
// student request and send a ping to the ones they want to teach.
const isStudent = computed(() => getCurrentUser()?.role === 'student');

const goToCreateRequest = () => {
  router.push({ name: 'request-creation' });
};

const handleEdit = (id) => {
  router.push({ name: 'request-edition', params: { id } });
};

const handleDelete = (id) => {
  if (confirm('Are you sure you want to delete this request?')) {
    deleteRequest(id);
  }
};
</script>

<template>
  <!-- Student: manage own requests -->
  <div v-if="isStudent" class="container py-4">
    <div class="mb-4">
      <button
        @click="goToCreateRequest"
        class="add-offer-btn btn btn-success bg-success-subtle text-dark border border-success-subtle w-100 py-3 rounded-pill fw-semibold shadow-sm"
      >
        Add New Request
      </button>
    </div>

    <div class="row g-4">
      <div
        v-for="req in getRequestByStudentId(currentUserId)"
        :key="req.id"
        class="col-12 col-sm-6 col-lg-3"
      >
        <ManageRequestComponent :request="req" @edit="handleEdit" @delete="handleDelete" />
      </div>
    </div>
  </div>

  <!-- Lecturer: browse all requests -->
  <div v-else class="container-fluid flex-grow-1 d-flex flex-column h-100 overflow-hidden">
    <div
      class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 align-content-start overflow-y-auto pb-4 px-2 m-0 flex-grow-1"
    >
      <div class="col" v-for="req in requests" :key="req.id">
        <RequestComponent :request="req" />
      </div>
    </div>
  </div>
</template>
