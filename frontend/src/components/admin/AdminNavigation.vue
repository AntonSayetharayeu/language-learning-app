<script setup>
const props = defineProps({
  navType: {
    type: String,
    required: true,
  },
});

console.log(props.navType);

const navLinks = [
  {
    navType: 'admin-chats',
    label: 'Chats',
    to: '/admin/chats',
  },
  {
    navType: 'admin-users',
    label: 'Users',
    to: '/admin/users',
  },
  {
    navType: 'admin-offers',
    label: 'Offers',
    to: '/admin/offers',
  },
  {
    navType: 'admin-requests',
    label: 'Requests',
    to: '/admin/requests',
  },
  {
    navType: 'admin-pings',
    label: 'Pings',
    to: '/admin/pings',
  },
  {
    navType: 'admin-ratings',
    label: 'Ratings',
    to: '/admin/ratings',
  },
  {
    navType: 'admin-topics',
    label: 'Topics',
    to: '/admin/topics',
  },
  {
    navType: 'admin-languages',
    label: 'Languages',
    to: '/admin/languages',
  },
];
</script>

<template>
  <aside
    class="admin-nav-sidebar d-flex flex-column flex-shrink-0 p-3 bg-light border-end vh-100 shadow-sm"
  >
    <div class="d-flex align-items-center mb-4">
      <a class="navbar-brand fs-5 fw-bold text-success" href="/admin">LLM Admin</a>
    </div>
    <ul class="nav flex-column mb-auto">
      <li v-for="item in navLinks" :key="item.navType" class="nav-item">
        <RouterLink
          :to="item.to"
          :class="{ 'nav-list-item-active': navType === item.navType }"
          class="nav-list-item nav-link text-secondary"
        >
          {{ item.label }}
        </RouterLink>
      </li>
    </ul>
    <hr />
    <div class="px-2">
      <RouterLink to="/" class="d-flex align-items-center link-dark text-decoration-none gap-2">
        <img src="@/assets/user.png" alt="admin profile" class="image rounded-circle shadow-sm" />
        <span class="fw-medium">Exit Admin</span>
      </RouterLink>
    </div>
  </aside>
</template>
