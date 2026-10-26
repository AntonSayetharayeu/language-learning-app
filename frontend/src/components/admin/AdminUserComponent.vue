<script setup>
import { useProfiles } from '@/services/profilesStore';
import { computed } from 'vue';

const props = defineProps({
  user: {
    type: Object,
    required: true,
  },
});

const { getProfileByUserId } = useProfiles();

defineEmits(['edit']);
const profile = computed(() => getProfileByUserId(props.user.id));
</script>

<template>
  <div
    @click="$emit('edit', user.id)"
    class="chat-card-large d-flex align-items-center p-3 mb-3 rounded-pill shadow-sm bg-white border border-light cursor-pointer"
  >
    <img :src="profile.imgUrl" alt="profile" class="big-image rounded-circle me-4 ms-2 border" />

    <div class="flex-grow-1 d-flex flex-column justify-content-center">
      <div class="d-flex align-items-center gap-2 mb-1">
        <h5 class="mb-0 fw-semibold text-dark">{{ user.username }}</h5>
        <span
          class="badge rounded-pill text-capitalize"
          :class="
            user.role === 'lecturer'
              ? 'text-bg-primary'
              : user.role === 'admin'
                ? 'text-bg-danger'
                : 'text-bg-secondary'
          "
        >
          {{ user.role }}
        </span>
        <span>Rating:</span>
        <span class="ms-2 text-warning fw-bold">{{ user.rating?.toFixed(1) || '0.0' }}</span>
      </div>

      <div class="text-secondary small">
        <span v-if="user.role === 'lecturer' || user.role === 'admin'">
          <strong>Teaching/Known:</strong> {{ user.knownString }}
        </span>
        <span v-if="user.role === 'student'" class="ms-1">
          <strong>Learning:</strong> {{ user.learnString }}
        </span>
      </div>
    </div>

    <div class="pe-4 text-muted">
      <i class="bi bi-chevron-right fs-4"></i>
    </div>
  </div>
</template>
