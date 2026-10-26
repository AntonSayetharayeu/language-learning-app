<script setup>
import { computed } from 'vue';
import { useOffers } from '@/services/offersStore';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import AdminOfferComponent from '@/components/admin/AdminOfferComponent.vue';

const { offers } = useOffers();
const { getUserById } = useUsers();
const { DEFAULT_IMAGE } = useProfiles();

const enrichedOffers = computed(() => {
  return offers.value.map((offer) => {
    const lecturer = getUserById(offer.lecturerId) || {};
    return {
      ...offer,
      lecturer: {
        username: lecturer.username || lecturer.login || `User #${offer.lecturerId}`,
        imgUrl: lecturer.imgUrl || DEFAULT_IMAGE,
      },
    };
  });
});
</script>

<template>
  <div class="container py-4">
    <div class="d-flex flex-column">
      <h3 class="fw-bold mb-4">Offers List</h3>
      <AdminOfferComponent v-for="offer in enrichedOffers" :key="offer.id" :offer="offer" />
      <div v-if="!enrichedOffers.length" class="text-center py-5 text-muted">No offers found.</div>
    </div>
  </div>
</template>
