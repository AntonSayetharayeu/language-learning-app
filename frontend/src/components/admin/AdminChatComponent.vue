<script setup>
defineProps({
  chat: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div
    class="chat-card-large d-flex align-items-center p-3 mb-3 rounded-4 shadow-sm bg-white border border-light cursor-pointer"
  >
    <div class="d-flex align-items-center me-4 ms-2">
      <img :src="chat.lecturer.imgUrl" alt="lecturer" class="image rounded-circle border me-2" />
      <span class="fw-semibold text-dark me-2">{{ chat.lecturer.username }}</span>
      <span class="text-muted small me-2">teaching</span>
      <span class="fw-semibold text-dark me-2">{{ chat.student.username }}</span>
      <img :src="chat.student.imgUrl" alt="student" class="image rounded-circle border" />
    </div>

    <div class="flex-grow-1 pt-1">
      <div class="text-secondary small">
        <span class="badge bg-success bg-opacity-10 text-success border border-success-subtle me-2">
          Learning: {{ chat.learning }}
        </span>
        <span class="badge bg-primary bg-opacity-10 text-primary border border-primary-subtle">
          Native: {{ chat.native }}
        </span>
      </div>
    </div>

    <div class="pe-2">
      <span
        class="badge rounded-pill fs-6 px-3 py-2 shadow-sm"
        :class="chat.isActive ? 'text-bg-success' : 'text-bg-secondary'"
      >
        {{ chat.isActive ? 'Active' : 'Archived' }}
      </span>
    </div>
  </div>
</template>
