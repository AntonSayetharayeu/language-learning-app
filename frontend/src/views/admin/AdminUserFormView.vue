<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import LanguageSelectorComponent from '@/components/LanguageSelectorComponent.vue';

const props = defineProps({
  id: {
    type: [String, Number],
    required: false,
  },
});

const router = useRouter();
const { users, getUserById, addUser, updateUser, getCurrentUser } = useUsers();
const { getProfileByUserId, updateProfile, addProfile, DEFAULT_IMAGE } = useProfiles();
const isSuperAdmin = computed(() => getCurrentUser()?.role === 'superAdmin');

const isEditMode = ref(false);
const fileInput = ref(null);

const formData = ref({
  login: '',
  password: '',
  role: 'student',
  username: '',
  imgUrl: DEFAULT_IMAGE,
  knownLanguages: [],
  languagesToLearn: [],
});

onMounted(() => {
  if (props.id) {
    isEditMode.value = true;
    const numericId = Number(props.id);
    const user = getUserById(numericId);
    const profile = getProfileByUserId(numericId) || {};

    if (user) {
      formData.value = {
        login: user.login || '',
        password: user.password || '',
        role: user.role || 'student',
        username: profile.username || '',
        imgUrl: profile.imgUrl || DEFAULT_IMAGE,
        knownLanguages: [...(profile.knownLanguages || [])],
        languagesToLearn: [...(profile.languagesToLearn || [])],
      };
    }
  }
});

console.log(users.value);

const triggerFileInput = () => fileInput.value?.click();

const handleFileChange = (event) => {
  const file = event.target.files?.[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = (e) => {
    formData.value.imgUrl = e.target.result;
  };
  reader.readAsDataURL(file);
  event.target.value = '';
};

const removeImage = () => {
  formData.value.imgUrl = DEFAULT_IMAGE;
};

const saveUser = () => {
  const numericId = props.id ? Number(props.id) : users.value.length;

  const userData = {
    login: formData.value.login,
    password: formData.value.password,
    role: formData.value.role,
  };

  const profileData = {
    userId: numericId,
    username: formData.value.username,
    imgUrl: formData.value.imgUrl,
    knownLanguages: [...formData.value.knownLanguages],
    languagesToLearn: [...formData.value.languagesToLearn],
  };

  if (isEditMode.value) {
    updateUser(numericId, userData);
    updateProfile(numericId, profileData);
  } else {
    addUser({ id: numericId, ...userData });
    addProfile(profileData);
  }

  router.push({ name: 'admin-users' });
};
</script>

<template>
  <div class="container py-4 max-w-700 mx-auto">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2 class="mb-0 fw-bold">{{ isEditMode ? 'Edit User' : 'Create New User' }}</h2>
    </div>

    <div class="card shadow-sm p-4 border-0">
      <div class="d-flex flex-column align-items-center mb-4 gap-3">
        <div class="max-image position-relative">
          <img
            :src="formData.imgUrl"
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
          <button type="button" class="btn btn-outline-primary btn-sm" @click="triggerFileInput">
            Select File
          </button>
          <button
            v-if="formData.imgUrl !== DEFAULT_IMAGE"
            type="button"
            class="btn btn-outline-danger btn-sm"
            @click="removeImage"
          >
            Remove
          </button>
        </div>
      </div>

      <form @submit.prevent="saveUser">
        <h5 class="fw-bold mb-3 border-bottom pb-2">Account Details</h5>
        <div class="row g-3 mb-4">
          <div class="col-md-6">
            <label class="form-label fw-semibold">Login</label>
            <input type="text" class="form-control" v-model="formData.login" required />
          </div>
          <div class="col-md-6">
            <label class="form-label fw-semibold">Password</label>
            <input type="text" class="form-control" v-model="formData.password" required />
          </div>
          <div class="col-md-12">
            <label class="form-label fw-semibold">Role</label>
            <select class="form-select" v-model="formData.role">
              <!-- TODO: Move to a specific place -->
              <option value="student">Student</option>
              <option value="lecturer">Lecturer</option>
              <option v-if="isSuperAdmin" value="admin">Admin</option>
            </select>
          </div>
        </div>

        <!-- Profile -->
        <h5 class="fw-bold mb-3 border-bottom pb-2">Profile Details</h5>
        <div class="mb-4">
          <label class="form-label fw-semibold">Public Username</label>
          <input type="text" class="form-control" v-model="formData.username" required />
        </div>

        <LanguageSelectorComponent
          v-model="formData.knownLanguages"
          title="Known Languages"
          badge-class="bg-primary"
          class="mb-3"
        />

        <LanguageSelectorComponent
          v-if="formData.role === 'student'"
          v-model="formData.languagesToLearn"
          title="Languages to Learn"
          badge-class="bg-success"
        />

        <div class="d-flex justify-content-end mt-5 gap-2">
          <button class="btn btn-outline-secondary" @click="router.push({ name: 'admin-users' })">
            Cancel
          </button>
          <button type="submit" class="btn btn-success px-4 py-2 fw-semibold shadow-sm">
            {{ isEditMode ? 'Save Changes' : 'Create User' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
