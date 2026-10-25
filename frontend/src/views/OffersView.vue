<script setup>
import { useRouter } from 'vue-router';
import OfferComponent from '@/components/OfferComponent.vue';
import { useOffers } from '@/services/offersStore';

const router = useRouter();
const { offers, deleteOffer } = useOffers();

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
</script>

<template>
  <div class="container py-4">
    <!-- Add Offer -->
    <div class="mb-4">
      <button
        @click="goToCreateOffer"
        class="add-offer-btn btn btn-success bg-success-subtle text-dark border border-success-subtle w-100 py-3 rounded-pill fw-semibold shadow-sm"
      >
        Add New Offer
      </button>
    </div>

    <!-- Offers -->
    <div class="row g-4">
      <div v-for="offer in offers" :key="offer.id" class="col-12 col-sm-6 col-lg-3">
        <OfferComponent :offer="offer" @edit="handleEdit" @delete="handleDelete" />
      </div>
    </div>
  </div>
</template>
