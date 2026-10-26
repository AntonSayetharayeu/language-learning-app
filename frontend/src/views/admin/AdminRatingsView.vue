<script setup>
import { computed } from 'vue';
import { useRatings } from '@/services/ratingsStore';
import { useUsers } from '@/services/usersStore';
import { useProfiles } from '@/services/profilesStore';
import AdminRatingComponent from '@/components/admin/AdminRatingComponent.vue';

const { ratings } = useRatings();
const { getUserById } = useUsers();
const { DEFAULT_IMAGE } = useProfiles();

const enrichedRatings = computed(() => {
  return ratings.value
    .map((rating) => {
      const reporter = getUserById(rating.reporterId) || {};
      const user = getUserById(rating.userId) || {};

      return {
        ...rating,
        reporter: {
          username: reporter.username || reporter.login || `User #${rating.reporterId}`,
          imgUrl: reporter.imgUrl || DEFAULT_IMAGE,
        },
        user: {
          username: user.username || user.login || `User #${rating.userId}`,
          imgUrl: user.imgUrl || DEFAULT_IMAGE,
        },
      };
    })
    .reverse();
});
</script>

<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">Ratings List</h3>

    <div class="d-flex flex-column">
      <AdminRatingComponent v-for="rating in enrichedRatings" :key="rating.id" :rating="rating" />

      <div v-if="!enrichedRatings.length" class="text-center py-5 text-muted">
        No ratings found.
      </div>
    </div>
  </div>
</template>
