<script setup>
import { useRouter } from 'vue-router';

const props = defineProps({
  chat: {
    type: Object,
    required: true,
  },
  size: {
    type: String,
    default: 'large',
  },
  isSelected: {
    type: Boolean,
    default: false,
  },
  isArchived: {
    type: Boolean,
    default: false,
  },
});

const router = useRouter();
const openChat = () => {
  router.push({ name: 'chat', params: { id: props.chat.id } });
};
</script>

<template>
  <div
    v-if="size === 'large'"
    @click="openChat"
    class="chat-card-large d-flex align-items-center p-3 mb-3 rounded-pill shadow-sm"
    :class="isArchived ? 'bg-archived' : 'bg-success-subtle'"
  >
    <img src="@/assets/user.png" alt="profile" class="big-image rounded-circle me-4 ms-2" />
    <div class="info w-25">
      <h5 class="mb-1 fw-semibold text-dark">{{ chat.username }}</h5>
      <p class="mb-0 text-secondary">Learning: {{ chat.learning }}</p>
    </div>
    <div v-if="isArchived" class="flex-grow-1 d-flex justify-content-center pe-5">
      <span class="fw-bold text-dark">= Archived =</span>
    </div>
  </div>

  <div
    v-else
    @click="openChat"
    class="chat-item-small d-flex align-items-center p-2 mb-2 rounded-pill"
    :class="
      isSelected ? 'bg-secondary bg-opacity-25 border border-secondary-subtle' : 'bg-success-subtle'
    "
  >
    <img src="@/assets/user.png" alt="profile" class="image rounded-circle me-2 ms-1" />
    <div class="flex-grow-1 d-flex justify-content-between align-items-center pe-2">
      <span class="fw-semibold text-dark small">{{ chat.username }}</span>
      <span class="text-muted small">{{ chat.learning }}</span>
    </div>
  </div>
</template>
