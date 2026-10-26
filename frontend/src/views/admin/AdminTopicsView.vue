<script setup>
import { ref, computed } from 'vue';
import { useTopics } from '@/services/topicsStore';
import AdminTopicComponent from '@/components/admin/AdminTopicComponent.vue';

const { topics, addTopic, updateTopic, deleteTopic, getUniqueCategories } = useTopics();

const isEditing = ref(false);
const editingId = ref(null);

const formData = ref({
  category: '',
  text: '',
});

const existingCategories = computed(() => getUniqueCategories());

const groupedTopics = computed(() => {
  return topics.value.reduce((acc, topic) => {
    if (!acc[topic.category]) acc[topic.category] = [];
    acc[topic.category].push(topic);
    return acc;
  }, {});
});

const handleSave = () => {
  if (!formData.value.text.trim() || !formData.value.category.trim()) return;

  if (isEditing.value) {
    updateTopic(editingId.value, {
      category: formData.value.category.trim(),
      text: formData.value.text.trim(),
    });
  } else {
    addTopic({
      category: formData.value.category.trim(),
      text: formData.value.text.trim(),
    });
  }
  resetForm();
};

const editTopic = (topic) => {
  isEditing.value = true;
  editingId.value = topic.id;
  formData.value.category = topic.category;
  formData.value.text = topic.text;
};

const removeTopic = (id) => {
  if (confirm('Are you sure you want to delete this topic?')) {
    deleteTopic(id);
    if (editingId.value === id) resetForm();
  }
};

const resetForm = () => {
  isEditing.value = false;
  editingId.value = null;
  formData.value.category = '';
  formData.value.text = '';
};
</script>

<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">Conversation Topics</h3>

    <div class="row g-4">
      <div class="col-md-5 col-lg-4">
        <div class="card shadow-sm border-0 position-sticky" style="top: 20px">
          <div class="card-header bg-white border-0 pt-4 pb-0">
            <h5 class="fw-bold">{{ isEditing ? 'Edit Topic' : 'Add New Topic' }}</h5>
          </div>
          <div class="card-body">
            <form @submit.prevent="handleSave">
              <div class="mb-3">
                <label class="form-label fw-semibold">Category</label>
                <input
                  type="text"
                  class="form-control mb-2"
                  v-model="formData.category"
                  placeholder="e.g. Travel & Culture"
                  required
                />
                <div class="d-flex flex-wrap gap-1">
                  <span
                    v-for="cat in existingCategories"
                    :key="cat"
                    @click="formData.category = cat"
                    class="badge bg-light text-dark border cursor-pointer hover-bg-secondary"
                  >
                    {{ cat }}
                  </span>
                </div>
              </div>

              <div class="mb-4">
                <label class="form-label fw-semibold">Topic</label>
                <textarea
                  class="form-control resize-none"
                  v-model="formData.text"
                  rows="3"
                  placeholder="What is your favorite..."
                  required
                ></textarea>
              </div>

              <div class="d-flex gap-2">
                <button type="submit" class="btn btn-success flex-grow-1 shadow-sm">
                  {{ isEditing ? 'Save Changes' : 'Add Topic' }}
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

      <div class="col-md-7 col-lg-8">
        <div
          v-for="(categoryTopics, categoryName) in groupedTopics"
          :key="categoryName"
          class="mb-4"
        >
          <h5 class="fw-semibold text-secondary mb-3 border-bottom pb-2">{{ categoryName }}</h5>

          <div class="d-flex flex-column gap-2">
            <AdminTopicComponent
              v-for="topic in categoryTopics"
              :key="topic.id"
              :topic="topic"
              @edit="editTopic"
              @delete="removeTopic"
            />
          </div>
        </div>

        <div v-if="!topics.length" class="text-center py-5 text-muted">No topics found</div>
      </div>
    </div>
  </div>
</template>
