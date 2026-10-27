<script setup>
import { useTopics } from '@/services/topicsStore';

const props = defineProps({
  showTopics: {
    type: Boolean,
    default: false,
  },
});

const { categories, getTopicsByCategory } = useTopics();
</script>
<template>
  <div
    v-if="showTopics"
    class="topics-popup position-absolute bg-white border rounded shadow p-3 top-100 end-0 mt-2 z-3"
  >
    <div class="d-flex justify-content-between align-items-center mb-2 border-bottom pb-1">
      <h6 class="m-0 fw-bold">Conversation Topics</h6>
    </div>
    <div v-for="category in categories" :key="category" class="mb-3">
      <span class="text-secondary small fw-bold d-block mb-1">{{ category }}</span>
      <ul class="list-unstyled mb-0 ms-2">
        <li
          v-for="topic in getTopicsByCategory(category)"
          :key="topic.text"
          class="small text-dark mb-1"
        >
          - {{ topic.text }}
        </li>
      </ul>
    </div>
  </div>
</template>
