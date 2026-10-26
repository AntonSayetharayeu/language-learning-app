<script setup>
defineProps({
  offer: {
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
      :src="offer.lecturer.imgUrl"
      alt="profile"
      class="big-image rounded-circle me-4 ms-2 border"
    />

    <div class="flex-grow-1 d-flex flex-column justify-content-center">
      <div class="d-flex align-items-center gap-2 mb-1">
        <h5 class="mb-0 fw-semibold text-dark">{{ offer.lecturer.username }}</h5>
        <span class="badge text-bg-primary rounded-pill">Lecturer</span>
      </div>

      <div class="text-secondary small mb-1">
        <strong>Teaching:</strong> {{ offer.teaching }} | <strong>Native:</strong>
        {{ offer.native }}
      </div>
      <div class="text-muted small text-truncate" style="max-width: 500px">
        {{ offer.description }}
      </div>
    </div>
  </div>
</template>
