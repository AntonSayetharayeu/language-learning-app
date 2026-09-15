<script setup>
import { ref, onMounted } from 'vue';
import Message from '@/components/Message.vue';

const items = ref([]);
const loading = ref(true);
const error = ref(null);
const msg = ref(null);

const fetchData = async () => {
  try {
    const response = await fetch('http://localhost:8081/api/user');
    if (!response.ok) throw new Error('Network response was not ok');

    const data = await response.json();
    items.value = data;
  } catch (err) {
    error.value = 'Failed to load data: ' + err.message;
  } finally {
    loading.value = false;
    msg.value = 'Something retrieved!';
  }
};

// Run this when the component loads
onMounted(() => {
  fetchData();
});
</script>

<template>
  <div>
    <h2>Users</h2>

    <Message :message="msg"></Message>

    <p v-if="loading">Loading items...</p>
    <p v-else-if="error" style="color: red">{{ error }}</p>

    <ul v-else>
      <li v-for="item in items" :key="item.id">
        <strong>{{ item.userName }}</strong> - {{ item.userRole == null ? 'Role' : item.userRole }}
      </li>
    </ul>
  </div>
  <div>
    <RouterLink to="/">Return Back!</RouterLink>
  </div>
</template>

<style></style>
