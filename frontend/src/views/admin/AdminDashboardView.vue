<script setup>
import { computed } from 'vue';
import { useUsers } from '@/services/usersStore';
import { useChats } from '@/services/chatsStore';
import { useLanguages } from '@/services/languagesStore';
import { useOffers } from '@/services/offersStore';
import { useRequests } from '@/services/requestsStore';

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from 'chart.js';
import { Bar } from 'vue-chartjs';
import { useProfiles } from '@/services/profilesStore';

// Register Chart.js components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const { users, getUserById } = useUsers();
const { getProfileByUserId } = useProfiles();
const { chats } = useChats();
const { languages } = useLanguages();
const { offers } = useOffers();
const { requests } = useRequests();

const roleColors = {
  student: 'rgba(54, 162, 235, 0.7)', // Blue
  lecturer: 'rgba(75, 192, 192, 0.7)', // Green
};

// Chart 1
const chartIData = computed(() => {
  const chatCounts = {};

  chats.value.forEach((chat) => {
    chatCounts[chat.lecturerId] = (chatCounts[chat.lecturerId] || 0) + 1;
    chatCounts[chat.studentId] = (chatCounts[chat.studentId] || 0) + 1;
  });

  const sortedUsers = Object.entries(chatCounts)
    .map(([id, count]) => {
      const user = getUserById(Number(id));
      return {
        username: user ? getProfileByUserId(user.id).username : `User ${id}`,
        role: user ? user.role : 'student',
        count,
      };
    })
    .sort((a, b) => b.count - a.count)
    .slice(0, 10);

  return {
    labels: sortedUsers.map((u) => u.username),
    datasets: Object.keys(roleColors).map((role) => ({
      label: role.charAt(0).toUpperCase() + role.slice(1),
      data: sortedUsers.map((u) => (u.role === role ? u.count : 0)),
      backgroundColor: roleColors[role],
    })),
  };
});

// Chart 2
const chartIIData = computed(() => {
  const usersWithLangs = users.value
    .map((u) => {
      const fullUser = getUserById(u.id);
      const langCount = fullUser.knownLanguages.length || 0;
      return { username: getProfileByUserId(u.id).username, role: u.role, count: langCount };
    })
    .sort((a, b) => b.count - a.count)
    .slice(0, 10);

  return {
    labels: usersWithLangs.map((u) => u.username),
    datasets: Object.keys(roleColors).map((role) => ({
      label: role.charAt(0).toUpperCase() + role.slice(1),
      data: usersWithLangs.map((u) => (u.role === role ? u.count : 0)),
      backgroundColor: roleColors[role],
    })),
  };
});

// Chart 3
const chartIIIData = computed(() => {
  const langCounts = {};

  chats.value.forEach((chat) => {
    if (chat.learning) {
      langCounts[chat.learning] = (langCounts[chat.learning] || 0) + 1;
    }
  });

  const sortedLangs = Object.entries(langCounts)
    .map(([lang, count]) => ({ lang, count }))
    .sort((a, b) => b.count - a.count);

  return {
    labels: sortedLangs.map((l) => l.lang),
    datasets: [
      {
        label: 'Chats (Learning)',
        data: sortedLangs.map((l) => l.count),
        backgroundColor: 'rgba(153, 102, 255, 0.7)',
      },
    ],
  };
});

// Chart 4
const chartIVData = computed(() => {
  const labels = languages.value;

  const offersData = labels.map((lang) => {
    return offers.value.filter((o) => o.teaching === lang).length;
  });

  const requestsData = labels.map((lang) => {
    return requests.value.filter((r) => r.learning === lang).length;
  });

  return {
    labels,
    datasets: [
      {
        label: 'Offers (Teaching)',
        data: offersData,
        backgroundColor: 'rgba(75, 192, 192, 0.7)',
      },
      {
        label: 'Requests (Learning)',
        data: requestsData,
        backgroundColor: 'rgba(255, 159, 64, 0.7)',
      },
    ],
  };
});

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: { legend: { display: true } },
};

const stackedChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: { legend: { display: true } },
  scales: {
    x: { stacked: true },
    y: { stacked: true },
  },
};
</script>

<template>
  <div class="container-fluid py-2">
    <h2 class="mb-4 fw-bold">Dashboard Overview</h2>

    <div class="row g-4">
      <!-- Chart 1 -->
      <div class="col-12 col-xl-6">
        <div class="card shadow-sm border-0 h-100">
          <div class="card-header bg-white border-0 pt-4 pb-0">
            <h6 class="fw-bold text-secondary mb-0">Most Active Users</h6>
          </div>
          <div class="card-body" style="height: 300px">
            <Bar :data="chartIData" :options="stackedChartOptions" />
          </div>
        </div>
      </div>

      <!-- Chart 2 -->
      <div class="col-12 col-xl-6">
        <div class="card shadow-sm border-0 h-100">
          <div class="card-header bg-white border-0 pt-4 pb-0">
            <h6 class="fw-bold text-secondary mb-0">Most Known Languages by User</h6>
          </div>
          <div class="card-body" style="height: 300px">
            <Bar :data="chartIIData" :options="stackedChartOptions" />
          </div>
        </div>
      </div>

      <!-- Chart 3 -->
      <div class="col-12 col-xl-6">
        <div class="card shadow-sm border-0 h-100">
          <div class="card-header bg-white border-0 pt-4 pb-0">
            <h6 class="fw-bold text-secondary mb-0">Most Popular Languages</h6>
          </div>
          <div class="card-body" style="height: 300px">
            <Bar :data="chartIIIData" :options="chartOptions" />
          </div>
        </div>
      </div>

      <!-- Chart 4 -->
      <div class="col-12 col-xl-6">
        <div class="card shadow-sm border-0 h-100">
          <div class="card-header bg-white border-0 pt-4 pb-0">
            <h6 class="fw-bold text-secondary mb-0">Offers vs Requests per Language</h6>
          </div>
          <div class="card-body" style="height: 300px">
            <Bar :data="chartIVData" :options="chartOptions" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
