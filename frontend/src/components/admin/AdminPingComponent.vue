<script setup>
import { useOffers } from '@/services/offersStore';
import { useRequests } from '@/services/requestsStore';
import { computed } from 'vue';

const props = defineProps({
  ping: {
    type: Object,
    required: true,
  },
});

const { getOfferById } = useOffers();
const { getRequestById } = useRequests();

const basedOnItem = computed(() => {
  return props.ping.basedOnType === 'offer'
    ? getOfferById(props.ping.basedOnId)
    : getRequestById(props.ping.basedOnId);
});
</script>

<template>
  <div
    class="d-flex align-items-center p-3 mb-3 rounded-pill shadow-sm bg-white border border-light"
  >
    <img :src="ping.sender.imgUrl" alt="sender" class="image rounded-circle ms-2 border" />
    <div class="mx-2 text-muted"><i class="bi bi-arrow-right"></i></div>
    <img :src="ping.receiver.imgUrl" alt="receiver" class="image rounded-circle me-4 border" />

    <div class="flex-grow-1 d-flex flex-column justify-content-center">
      <div class="d-flex align-items-center gap-2 mb-1">
        <span class="fw-semibold text-dark">{{ ping.sender.username }}</span>
        <span class="text-muted small">pinged</span>
        <span class="fw-semibold text-dark">{{ ping.receiver.username }}</span>

        <!-- TODO: Move to a specific place -->
        <span
          class="badge rounded-pill ms-2 text-capitalize"
          :class="{
            'text-bg-warning': ping.status === 'pending',
            'text-bg-success': ping.status === 'accepted',
            'text-bg-danger': ping.status === 'declined',
          }"
        >
          {{ ping.status }}
        </span>
      </div>

      <div class="text-secondary small">
        Based on <strong class="text-capitalize">{{ ping.basedOnType }}</strong> #{{
          ping.basedOnId
        }}
        <span class="text-muted ms-2">({{ new Date(ping.createdAt).toLocaleString() }})</span>
      </div>
      <div class="text-muted small text-truncate" style="max-width: 500px">
        "{{ basedOnItem?.description || 'No description provided' }}"
      </div>
    </div>
  </div>
</template>
