<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useProfiles } from '@/services/profilesStore';
import LanguageSelectorComponent from '../components/LanguageSelectorComponent.vue';
import { useUsers } from '@/services/usersStore.js';

const router = useRouter();

const { getProfileByUserId, updateProfile, DEFAULT_IMAGE } = useProfiles();
const { getCurrentUser } = useUsers();
const currentUser = computed(() => getCurrentUser());

const profile = ref(null);
const editableUsername = ref('');
const fileInput = ref(null);

const selectedKnownIds = ref([]);
const selectedLearnIds = ref([]);

onMounted(() => {
  const fetchedProfile = getProfileByUserId(currentUser.value.id);

  if (fetchedProfile) {
    profile.value = { ...fetchedProfile };
    editableUsername.value = fetchedProfile.username;

    selectedKnownIds.value = [...(fetchedProfile.knownLanguages || [])];
    selectedLearnIds.value = [...(fetchedProfile.languagesToLearn || [])];
  }
});

const triggerFileInput = () => fileInput.value?.click();

const handleFileChange = (event) => {
  const file = event.target.files?.[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = (e) => {
    profile.value.imgUrl = e.target.result;
  };
  reader.readAsDataURL(file);
  event.target.value = '';
};

const removeImage = () => {
  profile.value.imgUrl = DEFAULT_IMAGE;
};

const saveProfile = () => {
  updateProfile(profile.value.userId, {
    username: editableUsername.value,
    imgUrl: profile.value.imgUrl,
    knownLanguages: [...selectedKnownIds.value],
    languagesToLearn: [...selectedLearnIds.value],
  });
  router.push({ name: 'home' });
};
</script>

<template>
  <div class="profile-card container py-5">
    <div v-if="profile" class="card shadow-sm p-4">
      <div class="d-flex flex-column align-items-center mb-4 gap-3">
        <div class="max-image position-relative">
          <img
            :src="profile.imgUrl || DEFAULT_IMAGE"
            alt="profile image"
            class="rounded-circle w-100 h-100 object-fit-cover border shadow-sm"
          />
        </div>
        <input
          type="file"
          ref="fileInput"
          class="d-none"
          accept="image/*"
          @change="handleFileChange"
        />
        <div class="d-flex gap-2">
          <button class="btn btn-outline-primary btn-sm" @click="triggerFileInput">
            Select File
          </button>
          <button
            v-if="profile.imgUrl !== DEFAULT_IMAGE"
            class="btn btn-outline-danger btn-sm"
            @click="removeImage"
          >
            Remove
          </button>
        </div>
      </div>

      <form @submit.prevent="saveProfile">
        <div class="mb-4">
          <label class="form-label fw-bold">Username</label>
          <input type="text" class="form-control" v-model="editableUsername" required />
        </div>

        <LanguageSelectorComponent
          v-model="selectedKnownIds"
          title="known languages"
          badge-class="bg-primary"
        />

        <LanguageSelectorComponent
          v-if="currentUser.role === 'student'"
          v-model="selectedLearnIds"
          title="languages to learn"
          badge-class="bg-success"
        />

        <div class="d-flex justify-content-end mt-4">
          <button type="submit" class="btn btn-primary">Save Profile</button>
        </div>
      </form>
    </div>
  </div>
</template>
