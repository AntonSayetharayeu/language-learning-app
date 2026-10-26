<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import BrowseOfferComponent from '@/components/BrowseOfferComponent.vue';
import OfferComponent from '@/components/OfferComponent.vue';
import { useOffers } from '@/services/offersStore';
import { useUsers } from '@/services/usersStore';

const router = useRouter();
const { offers, getOfferByLecturerId, deleteOffer } = useOffers();
const { currentUserId, getCurrentUser } = useUsers();

const isLecturer = computed(() => getCurrentUser()?.role === 'lecturer');

const goToCreateOffer = () => {
  router.push({ name: 'offer-creation' });
};

const handleEdit = (id) => {
  router.push({ name: 'offer-edition', params: { id } });
};

const handleDelete = (id) => {
  if (confirm('Are you sure you want to delete this offer?')) {
    deleteOffer(id);
  }
};

console.log(getOfferByLecturerId(currentUserId).length);
</script>

<template>
  <div class="container py-4">
    <!-- Lecturer -->
    <template v-if="isLecturer">
      <div class="mb-4">
        <button
          @click="goToCreateOffer"
          class="add-offer-btn btn btn-success bg-success-subtle text-dark border border-success-subtle w-100 py-3 rounded-pill fw-semibold shadow-sm"
        >
          Add New Offer
        </button>
      </div>

      <div class="row g-4">
        <div
          v-for="offer in getOfferByLecturerId(currentUserId)"
          :key="offer.id"
          class="col-12 col-sm-6 col-lg-3"
        >
          <OfferComponent :offer="offer" @edit="handleEdit" @delete="handleDelete" />
        </div>
        <div v-if="!getOfferByLecturerId(currentUserId).length" class="text-center py-5 text-muted">
          No offers found
        </div>
      </div>
    </template>

    <!-- Student -->
    <div v-else class="row g-4">
      <div v-for="offer in offers" :key="offer.id" class="col-12 col-sm-6 col-lg-4">
        <BrowseOfferComponent :offer="offer" />
      </div>
      <div v-if="!offers.length" class="text-center py-5 text-muted">No offers found</div>
    </div>
  </div>
</template>
