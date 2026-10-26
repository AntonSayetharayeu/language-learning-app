<script setup>
defineProps({
  topic: {
    type: Object,
    required: true,
  },
});

defineEmits(['edit', 'delete']);
</script>

<template>
  <div
    class="d-flex align-items-center justify-content-between p-3 bg-white rounded shadow-sm border border-light"
  >
    <span class="text-dark">{{ topic.text }}</span>

    <div class="d-flex gap-2 flex-shrink-0 ms-3">
      <button @click="$emit('edit', topic)" class="btn btn-sm btn-outline-secondary">Edit</button>
      <button @click="$emit('delete', topic.id)" class="btn btn-sm btn-outline-danger">
        Delete
      </button>
    </div>
  </div>
</template>
