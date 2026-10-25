<script setup>
import { useLanguages } from '@/services/languagesStore';
import { useOffers } from '@/services/offersStore';
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const { addOffer, updateOffer, getOfferById } = useOffers();
const { languages } = useLanguages();

const EDIT_LABEL = 'Edit Offer';
const CREATION_LABEL = 'Add New Offer';

const offerId = computed(() => route.params.id);
const editMode = computed(() => !!offerId.value);
const pageTitle = computed(() => (editMode.value ? EDIT_LABEL : CREATION_LABEL));

const form = ref({
  teaching: 'German',
  native: 'Polish',
  description: '',
});

// TODO:Implement via request
onMounted(() => {
  if (editMode.value) {
    const existingOffer = getOfferById(offerId.value);
    if (existingOffer) {
      form.value = { ...existingOffer };
    }
  }
});

const saveOffer = () => {
  if (!form.value.teaching || !form.value.native) {
    alert('Please enter a teaching and native language.');
    return;
  }

  if (editMode.value) {
    updateOffer(offerId.value, form.value);
  } else {
    addOffer(form.value);
  }

  router.push({ name: 'offers' });
};

const goBack = () => {
  router.push({ name: 'offers' });
};
</script>

<template>
  <div class="container py-5">
    <div class="d-flex align-items-center justify-content-center mb-4 px-2">
      <h3 class="m-0 fw-bold text-dark">{{ pageTitle }}</h3>
    </div>

    <!-- Card -->
    <div class="row justify-content-center">
      <div class="col-12 col-md-10 col-lg-8">
        <div class="card bg-success-subtle border-0 rounded-4 p-4 p-md-5 shadow-sm">
          <form @submit.prevent="saveOffer">
            <div class="row g-3 mb-4">
              <div class="col-md-6">
                <label for="teachingLang" class="form-label fw-semibold text-dark"
                  >Teaching Language</label
                >
                <select
                  id="teachingLang"
                  v-model="form.teaching"
                  class="form-select border-0 shadow-sm py-2"
                >
                  <option v-for="lang in languages" :key="lang" :value="lang">
                    {{ lang }}
                  </option>
                </select>
              </div>

              <div class="col-md-6">
                <label for="nativeLang" class="form-label fw-semibold text-dark"
                  >Native Language</label
                >
                <select
                  id="nativeLang"
                  v-model="form.native"
                  class="form-select border-0 shadow-sm py-2"
                >
                  <option v-for="lang in languages" :key="lang" :value="lang">
                    {{ lang }}
                  </option>
                </select>
              </div>
            </div>

            <div class="mb-4">
              <label for="descriptionText" class="form-label fw-semibold text-dark"
                >Description</label
              >
              <textarea
                id="descriptionText"
                v-model="form.description"
                rows="6"
                placeholder="Write a detailed description of your offer..."
                class="form-control border-0 shadow-sm p-3"
              ></textarea>
              <div class="form-text text-muted text-end mt-1">
                {{ form.description.length }} characters
              </div>
            </div>

            <!-- Buttons -->
            <div
              class="d-flex align-items-center justify-content-end gap-3 pt-3 border-top border-success-subtle"
            >
              <button
                type="button"
                @click="goBack"
                class="btn btn-outline-secondary rounded-pill px-4"
              >
                Cancel
              </button>
              <button type="submit" class="btn btn-primary rounded-pill px-5 shadow-sm">
                Save Offer
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-select,
.form-control {
  background-color: #ffffff;
}
.form-select:focus,
.form-control:focus {
  background-color: #ffffff;
  box-shadow: 0 0 0 0.25rem rgba(25, 135, 84, 0.25) !important;
}
</style>
