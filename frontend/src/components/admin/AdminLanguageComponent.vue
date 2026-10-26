<script setup>
defineProps({
  language: {
    type: String,
    required: true,
  },
  index: {
    type: Number,
    required: true,
  },
  isSuperAdmin: {
    type: Boolean,
    default: false,
  },
});

defineEmits(['edit', 'delete']);
</script>

<template>
  <div
    class="d-flex align-items-center justify-content-between p-3 bg-white rounded shadow-sm border border-light mb-2"
  >
    <div class="d-flex align-items-center gap-3">
      <span class="badge bg-secondary rounded-pill px-3 py-2 text-white shadow-sm"
        >ID: {{ index }}</span
      >
      <span class="fw-semibold text-dark fs-5">{{ language }}</span>
    </div>

    <div v-if="isSuperAdmin" class="d-flex gap-2 flex-shrink-0">
      <button @click="$emit('edit', index, language)" class="btn btn-sm btn-outline-secondary px-3">
        Edit
      </button>
      <button @click="$emit('delete', index)" class="btn btn-sm btn-outline-danger px-3">
        Delete
      </button>
    </div>
  </div>
</template>
