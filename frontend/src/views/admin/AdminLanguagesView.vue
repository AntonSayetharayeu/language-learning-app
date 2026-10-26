<script setup>
import { ref, computed } from 'vue';
import { useLanguages } from '@/services/languagesStore';
import { useUsers } from '@/services/usersStore';
import AdminLanguageComponent from '@/components/admin/AdminLanguageComponent.vue';

const { languages, addLanguage, updateLanguage, deleteLanguage } = useLanguages();
const { getCurrentUser } = useUsers();

const isSuperAdmin = computed(() => getCurrentUser()?.role === 'superAdmin');

const isEditing = ref(false);
const editingIndex = ref(null);
const languageName = ref('');

const handleSave = () => {
  if (!languageName.value.trim() || !isSuperAdmin.value) return;

  if (isEditing.value && editingIndex.value !== null) {
    updateLanguage(editingIndex.value, languageName.value.trim());
  } else {
    addLanguage(languageName.value.trim());
  }
  resetForm();
};

const editLanguage = (index, name) => {
  if (!isSuperAdmin.value) return;
  isEditing.value = true;
  editingIndex.value = index;
  languageName.value = name;
};

const removeLanguage = (index) => {
  if (!isSuperAdmin.value) return;
  if (confirm('Are you sure you want to delete this language?')) {
    deleteLanguage(index);
    if (editingIndex.value === index) resetForm();
  }
};

const resetForm = () => {
  isEditing.value = false;
  editingIndex.value = null;
  languageName.value = '';
};
</script>

<template>
  <div class="container py-4">
    <div class="d-flex align-items-center gap-3 mb-4">
      <h3 class="fw-bold mb-0">Languages List</h3>
    </div>

    <div class="row g-4">
      <div v-if="isSuperAdmin" class="col-md-5 col-lg-4">
        <div class="card shadow-sm border-0 position-sticky" style="top: 20px">
          <div class="card-header bg-white border-0 pt-4 pb-0">
            <h5 class="fw-bold">{{ isEditing ? 'Edit Language' : 'Add New Language' }}</h5>
          </div>
          <div class="card-body">
            <form @submit.prevent="handleSave">
              <div class="mb-4">
                <label class="form-label fw-semibold">Language Name</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="languageName"
                  placeholder="e.g. Japanese"
                  required
                />
              </div>

              <div class="d-flex gap-2">
                <button type="submit" class="btn btn-success flex-grow-1 shadow-sm">
                  {{ isEditing ? 'Save Changes' : 'Add Language' }}
                </button>
                <button
                  v-if="isEditing"
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="resetForm"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div :class="isSuperAdmin ? 'col-md-7 col-lg-8' : 'col-12 max-w-800 mx-auto'">
        <div
          v-if="!isSuperAdmin"
          class="alert alert-secondary border-0 shadow-sm mb-4 d-flex align-items-center gap-2"
        >
          <i class="bi bi-info-circle-fill text-secondary"></i>
          <strong>Read-Only Mode:</strong> Only Super Admins can add, edit, or remove languages.
        </div>

        <div class="d-flex flex-column">
          <AdminLanguageComponent
            v-for="(lang, index) in languages"
            :key="index"
            :language="lang"
            :index="index"
            :is-super-admin="isSuperAdmin"
            @edit="editLanguage"
            @delete="removeLanguage"
          />
        </div>

        <div v-if="!languages.length" class="text-center py-5 text-muted">No languages found</div>
      </div>
    </div>
  </div>
</template>
