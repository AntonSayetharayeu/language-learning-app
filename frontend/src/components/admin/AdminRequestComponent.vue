<script setup>
defineProps({
  request: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div
    class="d-flex align-items-center p-3 mb-3 rounded-pill shadow-sm bg-white border border-light"
  >
    <img
      :src="request.student.imgUrl"
      alt="profile"
      class="big-image rounded-circle me-4 ms-2 border"
    />

    <div class="flex-grow-1 d-flex flex-column justify-content-center">
      <div class="d-flex align-items-center gap-2 mb-1">
        <h5 class="mb-0 fw-semibold text-dark">{{ request.student.username }}</h5>
        <span class="badge text-bg-success rounded-pill">Student</span>
      </div>

      <div class="text-secondary small mb-1">
        <strong>Learning:</strong> {{ request.learning }} | <strong>Native:</strong>
        {{ request.native }}
      </div>
      <div class="text-muted small text-truncate" style="max-width: 500px">
        {{ request.description }}
      </div>
    </div>
  </div>
</template>
