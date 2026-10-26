<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import { useLanguages } from '@/services/languagesStore';
import AdminUserComponent from '@/components/admin/AdminUserComponent.vue';

const router = useRouter();
const { users } = useUsers();
const { getProfileByUserId, DEFAULT_IMAGE } = useProfiles();
const { getLanguageById } = useLanguages();

const usersList = computed(() => {
  return users.value.map((user) => {
    const profile = getProfileByUserId(user.id) || {};

    const knownLangs = (profile.knownLanguages || []).map((id) => getLanguageById(id));
    const learnLangs = (profile.languagesToLearn || []).map((id) => getLanguageById(id));

    return {
      ...user,
      username: profile.username || user.login,
      imgUrl: profile.imgUrl || DEFAULT_IMAGE,
      knownString: knownLangs.length ? knownLangs.join(', ') : 'None',
      learnString: learnLangs.length ? learnLangs.join(', ') : 'None',
    };
  });
});

const goToAddUser = () => {
  router.push({ name: 'admin-user-create' });
};

const goToEditUser = (id) => {
  router.push({ name: 'admin-user-edit', params: { id } });
};
</script>

<template>
  <div class="container py-4">
    <div class="mb-5">
      <button
        @click="goToAddUser"
        class="add-offer-btn btn btn-success bg-success-subtle text-dark border border-success-subtle w-100 py-3 rounded-pill fw-semibold shadow-sm"
      >
        Add New User
      </button>
    </div>

    <div class="d-flex flex-column">
      <AdminUserComponent
        v-for="user in usersList"
        :key="user.id"
        :user="user"
        @edit="goToEditUser"
      />
    </div>
  </div>
</template>
