<script setup>
import { computed } from 'vue';
import { useUsers } from '@/services/usersStore';
import { useOffers } from '@/services/offersStore';
import { useRequests } from '@/services/requestsStore';

const props = defineProps({
  ping: { type: Object, required: true },
  type: { type: String, required: true },
});

const emit = defineEmits(['accept', 'decline']);

const { getUserById } = useUsers();
const { getOfferById } = useOffers();
const { getRequestById } = useRequests();

const targetUser = computed(() => {
  const targetId = props.type === 'sent' ? props.ping.receiverId : props.ping.senderId;
  return getUserById(targetId);
});

const rating = computed(() => targetUser.value.rating || 0);
const fullStars = computed(() => Math.floor(rating.value));
const hasHalfStar = computed(() => rating.value % 1 !== 0);
const emptyStars = computed(() => 5 - Math.ceil(rating.value));

const basedOnItem = computed(() => {
  return props.ping.basedOnType === 'offer'
    ? getOfferById(props.ping.basedOnId)
    : getRequestById(props.ping.basedOnId);
});

const primaryLangLabel = computed(() =>
  props.ping.basedOnType === 'offer' ? 'Teaching' : 'Learning',
);
const primaryLangValue = computed(() =>
  props.ping.basedOnType === 'offer' ? basedOnItem.value.teaching : basedOnItem.value.learning,
);

const MAX_LENGTH = 150;
const TRUNCATED_SIGN = '...';

const truncatedDescription = computed(() => {
  if (!basedOnItem.value.description) return '';
  if (basedOnItem.value.description.length <= MAX_LENGTH) {
    return basedOnItem.value.description;
  }
  return basedOnItem.value.description.slice(0, MAX_LENGTH).trim() + TRUNCATED_SIGN;
});
</script>

<template>
  <div class="card border-0 bg-light shadow-sm p-3 mb-3 rounded-4">
    <div class="d-flex align-items-start mb-2">
      <!-- Sent -->
      <div class="ping-card-label d-flex flex-column align-items-center me-3 flex-shrink-0">
        <img src="@/assets/user.png" alt="profile" class="image rounded-circle mb-1" />
        <div class="d-flex justify-content-center w-100 gap-1 mt-1">
          <img
            v-for="n in fullStars"
            :key="'full-' + n"
            src="@/assets/star.png"
            alt="star"
            class="tiny-image"
          />
          <img v-if="hasHalfStar" src="@/assets/half-star.png" alt="half star" class="tiny-image" />
          <img
            v-for="n in emptyStars"
            :key="'empty-' + n"
            src="@/assets/empty-star.png"
            alt="empty star"
            class="tiny-image"
          />
        </div>
      </div>

      <!-- Recieved -->
      <div class="flex-grow-1 d-flex justify-content-between align-items-start">
        <div>
          <h6 class="mb-0 fw-bold">{{ targetUser.username }}</h6>
          <span class="text-muted small">
            {{ type === 'sent' ? 'Sent to this user' : 'Received from this user' }}
          </span>
        </div>
        <span
          class="badge text-capitalize mt-1"
          :class="{
            'bg-warning text-dark': ping.status === 'pending',
            'bg-success': ping.status === 'accepted',
            'bg-danger': ping.status === 'declined',
          }"
        >
          {{ ping.status }}
        </span>
      </div>
    </div>

    <div v-if="basedOnItem" class="bg-white p-2 rounded border mb-2">
      <span class="text-muted small fw-bold d-block mb-2 text-capitalize"
        >Based on {{ ping.basedOnType }}:</span
      >

      <div class="d-flex gap-2 mb-2">
        <span
          class="badge bg-success bg-opacity-10 text-success border border-success-subtle px-2 py-1"
        >
          {{ primaryLangLabel }}: {{ primaryLangValue }}
        </span>
        <span
          class="badge bg-primary bg-opacity-10 text-primary border border-primary-subtle px-2 py-1"
        >
          Native: {{ basedOnItem.native }}
        </span>
      </div>

      <p class="text-secondary small mb-0">
        {{ truncatedDescription }}
      </p>
    </div>

    <div v-if="type === 'received' && ping.status === 'pending'" class="d-flex gap-2 mt-2">
      <button
        @click="emit('accept', ping.id)"
        class="btn btn-sm btn-success flex-grow-1 rounded-pill fw-semibold shadow-sm"
      >
        Accept
      </button>
      <button
        @click="emit('decline', ping.id)"
        class="btn btn-sm btn-outline-danger flex-grow-1 rounded-pill fw-semibold bg-white shadow-sm"
      >
        Decline
      </button>
    </div>
  </div>
</template>
