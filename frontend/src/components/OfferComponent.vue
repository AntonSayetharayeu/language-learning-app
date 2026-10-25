<script setup>
import { computed } from 'vue';

const props = defineProps({
  offer: {
    type: Object,
    required: true,
  },
});

//Passing to the parent
defineEmits(['edit', 'delete']);

const MAX_LENGTH = 210;
const TRUNCATED_SIGN = '...';

const truncatedDescription = computed(() => {
  if (!props.offer.description) return '';
  if (props.offer.description.length <= MAX_LENGTH) {
    return props.offer.description;
  }
  return props.offer.description.slice(0, MAX_LENGTH).trim() + TRUNCATED_SIGN;
});
</script>

<template>
  <!-- Offer -->
  <div
    class="offer-card card bg-success-subtle border-0 rounded-4 p-3 shadow-sm d-flex flex-column justify-content-between h-100"
  >
    <div>
      <div class="d-flex mb-1">
        <span class="fw-bold me-2" style="width: 80px">Teaching</span>
        <span class="text-dark">{{ offer.teaching }}</span>
      </div>
      <div class="d-flex mb-3">
        <span class="fw-bold me-2" style="width: 80px">Native</span>
        <span class="text-dark">{{ offer.native }}</span>
      </div>

      <p class="text-secondary small mb-4 text-break">
        {{ truncatedDescription }}
      </p>
    </div>

    <!-- Buttons -->
    <div class="d-flex justify-content-end align-items-center gap-2 mt-auto">
      <button
        @click="$emit('edit', offer.id)"
        class="offers-btn btn btn-primary rounded-circle p-2 d-flex align-items-center justify-content-center shadow-sm"
        aria-label="Edit offer"
      >
        <img src="@/assets/edit-offer-request.png" alt="edit offer" class="small-image" />
      </button>

      <button
        @click="$emit('delete', offer.id)"
        class="offers-btn btn btn-danger rounded-circle p-2 d-flex align-items-center justify-content-center shadow-sm"
        aria-label="Delete offer"
      >
        <img src="@/assets/delete-offer-request.png" alt="delete offer" class="small-image" />
      </button>
    </div>
  </div>
</template>
