<script setup>
import { computed } from 'vue';

const props = defineProps({
  request: {
    type: Object,
    required: true,
  },
});

//Passing to the parent
defineEmits(['edit', 'delete']);

const MAX_LENGTH = 210;
const TRUNCATED_SIGN = '...';

const truncatedDescription = computed(() => {
  if (!props.request.description) return '';
  if (props.request.description.length <= MAX_LENGTH) {
    return props.request.description;
  }
  return props.request.description.slice(0, MAX_LENGTH).trim() + TRUNCATED_SIGN;
});
</script>

<template>
  <!-- Request -->
  <div
    class="offer-card card bg-success-subtle border-0 rounded-4 p-3 shadow-sm d-flex flex-column justify-content-between h-100"
  >
    <div>
      <div class="d-flex mb-1">
        <span class="offer-card-label fw-bold me-2">Learning</span>
        <span class="text-dark">{{ request.learning }}</span>
      </div>
      <div class="d-flex mb-3">
        <span class="offer-card-label fw-bold me-2">Native</span>
        <span class="text-dark">{{ request.native }}</span>
      </div>

      <p class="text-secondary small mb-4 text-break">
        {{ truncatedDescription }}
      </p>
    </div>

    <!-- Buttons -->
    <div class="d-flex justify-content-end align-items-center gap-2 mt-auto">
      <button
        @click="$emit('edit', request.id)"
        class="offers-btn btn btn-primary rounded-circle p-2 d-flex align-items-center justify-content-center shadow-sm"
        aria-label="Edit request"
      >
        <img src="@/assets/edit-offer-request.png" alt="edit request" class="small-image" />
      </button>

      <button
        @click="$emit('delete', request.id)"
        class="offers-btn btn btn-danger rounded-circle p-2 d-flex align-items-center justify-content-center shadow-sm"
        aria-label="Delete request"
      >
        <img src="@/assets/delete-offer-request.png" alt="delete request" class="small-image" />
      </button>
    </div>
  </div>
</template>
