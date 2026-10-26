<script setup>
import { ref } from 'vue';
import RequestComponent from '@/components/RequestComponent.vue';
import { useRequests } from '@/services/requestsStore';

//TODO: for Student requests should be only from him
const { requests } = useRequests();
</script>

<template>
  <div class="container-fluid flex-grow-1 d-flex flex-column h-100 overflow-hidden">
    <div
      class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 align-content-start overflow-y-auto pb-4 px-2 m-0 flex-grow-1"
    >
      <div class="col" v-for="req in requests" :key="req.id">
        <RequestComponent :request="req" />
      </div>
    </div>
  </div>
</template>
