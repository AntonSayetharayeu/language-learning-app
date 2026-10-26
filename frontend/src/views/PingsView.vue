<script setup>
import { computed } from 'vue';
import { usePings } from '@/services/pingsStore';
import { useUsers } from '@/services/usersStore';
import PingComponent from '@/components/PingComponent.vue';
import { useRequests } from '@/services/requestsStore';
import { useOffers } from '@/services/offersStore';
import { useChats } from '@/services/chatsStore';
import { useRouter } from 'vue-router';

const router = useRouter();
const { currentUserId } = useUsers();
const { pings, getSentPings, getReceivedPings, updatePingStatus, declineCompetingPings } =
  usePings();
const { getRequestById, deleteRequest } = useRequests();
const { getOfferById, deleteOffer } = useOffers();
const { createChat } = useChats();

const sentPings = computed(() => getSentPings(currentUserId.value));
const receivedPings = computed(() => getReceivedPings(currentUserId.value));

const handleAccept = (pingId) => {
  const ping = pings.value.find((p) => p.id === pingId);
  if (!ping) return;

  updatePingStatus(pingId, 'accepted');
  declineCompetingPings(pingId, ping.basedOnId, ping.basedOnType);

  let lecturerId, studentId, learningLang, nativeLang;

  if (ping.basedOnType === 'request') {
    const request = getRequestById(ping.basedOnId);

    lecturerId = ping.senderId;
    studentId = ping.receiverId;
    learningLang = request.learning;
    nativeLang = request.native;

    deleteRequest(ping.basedOnId);
  } else if (ping.basedOnType === 'offer') {
    const offer = getOfferById(ping.basedOnId);

    lecturerId = ping.receiverId;
    studentId = ping.senderId;
    learningLang = offer.teaching;
    nativeLang = offer.native;

    deleteOffer(ping.basedOnId);
  }

  createChat({
    lecturerId,
    studentId,
    learning: learningLang,
    native: nativeLang,
    isActive: true,
  });

  router.push({ name: 'chats' });
};

const handleDecline = (pingId) => {
  updatePingStatus(pingId, 'declined');
};
</script>

<template>
  <div class="container-fluid py-4 flex-grow-1 d-flex flex-column h-100 overflow-hidden">
    <div class="row h-100 m-0">
      <!-- Sent -->
      <div class="col-md-6 d-flex flex-column h-100 border-end pe-3">
        <h4 class="fw-bold mb-3 flex-shrink-0">Sent Pings</h4>
        <div class="flex-grow-1 overflow-y-auto pe-2">
          <p v-if="!sentPings.length" class="text-muted text-center mt-4">No sent pings.</p>
          <PingComponent v-for="ping in sentPings" :key="ping.id" :ping="ping" type="sent" />
        </div>
      </div>

      <!-- Received -->
      <div class="col-md-6 d-flex flex-column h-100 ps-3">
        <h4 class="fw-bold mb-3 flex-shrink-0">Received Pings</h4>
        <div class="flex-grow-1 overflow-y-auto pe-2">
          <p v-if="!receivedPings.length" class="text-muted text-center mt-4">No received pings.</p>
          <PingComponent
            v-for="ping in receivedPings"
            :key="ping.id"
            :ping="ping"
            type="received"
            @accept="handleAccept"
            @decline="handleDecline"
          />
        </div>
      </div>
    </div>
  </div>
</template>
