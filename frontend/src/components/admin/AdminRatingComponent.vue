<script setup>
defineProps({
  rating: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div
    class="d-flex align-items-center p-3 mb-3 rounded-pill shadow-sm bg-white border border-light"
  >
    <div class="d-flex align-items-center me-4 ms-2">
      <img :src="rating.reporter.imgUrl" alt="reporter" class="image rounded-circle border me-2" />
      <span class="fw-semibold text-dark me-2">{{ rating.reporter.username }}</span>
      <span class="text-muted small me-2">rated</span>
      <span class="fw-semibold text-dark me-2">{{ rating.user.username }}</span>
      <img :src="rating.user.imgUrl" alt="user" class="image rounded-circle border" />
    </div>

    <div class="flex-grow-1 me-4 pt-1">
      <div class="text-muted small text-truncate" style="max-width: 500px">
        "{{ rating.description || 'No description provided' }}"
      </div>
    </div>

    <div class="pe-2 pt-1">
      <span class="badge text-bg-warning rounded-pill fs-6 px-3 py-2 text-dark shadow-sm">
        {{ rating.value.toFixed(1) }}
        <i class="bi bi-star-fill text-dark ms-1" style="font-size: 0.8rem"></i>
      </span>
    </div>
  </div>
</template>
