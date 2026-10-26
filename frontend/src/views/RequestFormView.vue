<script setup>
import { useLanguages } from '@/services/languagesStore';
import { useRequests } from '@/services/requestsStore';
import { useUsers } from '@/services/usersStore';
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const { addRequest, updateRequest, getRequestById } = useRequests();
const { languages } = useLanguages();
const { currentUserId } = useUsers();

const EDIT_LABEL = 'Edit Request';
const CREATION_LABEL = 'Add New Request';

const requestId = computed(() => route.params.id);
const editMode = computed(() => !!requestId.value);
const pageTitle = computed(() => (editMode.value ? EDIT_LABEL : CREATION_LABEL));

const form = ref({
  studentId: currentUserId.value,
  learning: '',
  native: '',
  description: '',
});

onMounted(() => {
  if (editMode.value) {
    const existingRequest = getRequestById(requestId.value);
    if (existingRequest) {
      form.value = { ...existingRequest };
    }
  }
});

const saveRequest = () => {
  if (!form.value.learning || !form.value.native) {
    alert('Please enter a learning and native language.');
    return;
  }

  if (editMode.value) {
    updateRequest(requestId.value, form.value);
  } else {
    addRequest(form.value);
  }

  router.push({ name: 'requests' });
};

const goBack = () => {
  router.push({ name: 'requests' });
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
          <form @submit.prevent="saveRequest">
            <div class="row g-3 mb-4">
              <div class="col-md-6">
                <label for="learningLang" class="form-label fw-semibold text-dark"
                  >Learning Language</label
                >
                <select
                  id="learningLang"
                  v-model="form.learning"
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
                placeholder="Write a detailed description of your request..."
                class="resize-none form-control border-0 shadow-sm p-3"
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
                Save Request
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
