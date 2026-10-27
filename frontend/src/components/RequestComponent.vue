<script setup>
import { usePings } from '@/services/pingsStore';
import { useUsers } from '@/services/usersStore';
import { computed, ref } from 'vue';

const props = defineProps({
  request: {
    type: Object,
    required: true,
  },
});

const { currentUserId, getUserById } = useUsers();
const { isAlreadyPinged, addPing } = usePings();

const user = computed(() => getUserById(props.request.studentId));

const hasAlreadyPinged = computed(() =>
  isAlreadyPinged(currentUserId.value, props.request.id, 'request'),
);

const sendPing = () => {
  if (hasAlreadyPinged.value) return;

  addPing({
    senderId: currentUserId.value,
    receiverId: props.request.studentId,
    basedOnId: props.request.id,
    basedOnType: 'request',
  });
};

const fullStars = computed(() => Math.floor(user.value.rating));
const hasHalfStar = computed(() => user.value.rating % 1 !== 0);
const emptyStars = computed(() => 5 - Math.ceil(user.value.rating));

const MAX_LENGTH = 100;
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
  <div class="request-card card h-100 border-0 bg-success-subtle shadow-sm p-4 rounded-5">
    <div class="d-flex h-100">
      <div class="request-card-column d-flex flex-column align-items-center me-4 flex-shrink-0">
        <img
          src="@/assets/user.png"
          alt="profile"
          class="giant-image rounded-circle shadow-sm mb-2"
        />
        <div class="d-flex justify-content-center w-100 gap-1 mt-1">
          <img
            v-for="n in fullStars"
            :key="'full-' + n"
            src="@/assets/star.png"
            alt="star"
            class="small-image"
          />
          <img
            v-if="hasHalfStar"
            src="@/assets/half-star.png"
            alt="half star"
            class="small-image"
          />
          <img
            v-for="n in emptyStars"
            :key="'empty-' + n"
            src="@/assets/empty-star.png"
            alt="empty star"
            class="small-image"
          />
        </div>
        <span class="text-muted small mt-1 fw-bold">{{ Number(user.rating).toFixed(1) }}</span>
      </div>

      <div class="d-flex flex-column flex-grow-1">
        <h5 class="mb-1 fw-bold text-dark">{{ user.username }}</h5>
        <div class="d-flex flex-wrap gap-2 mb-2">
          <span class="badge bg-success bg-opacity-10 text-success border border-success-subtle">
            Learning: {{ request.learning }}
          </span>
          <span class="badge bg-primary bg-opacity-10 text-primary border border-primary-subtle">
            Native: {{ request.native }}
          </span>
        </div>
        <p class="text-secondary small mb-3 description-text">
          {{ truncatedDescription }}
        </p>

        <button
          :disabled="hasAlreadyPinged"
          @click="sendPing"
          class="btn btn-success rounded-pill fw-semibold shadow-sm w-100 mt-auto"
        >
          {{ hasAlreadyPinged ? 'Ping Sent' : 'Accept Student' }}
        </button>
      </div>
    </div>
  </div>
</template>
