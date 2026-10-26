<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUsers } from '@/services/usersStore';

const router = useRouter();
const { login } = useUsers();

const loginInput = ref('');
const passwordInput = ref('');
const errorMessage = ref('');

const handleLogin = () => {
  errorMessage.value = '';

  // Simulate network delay

  const result = login(loginInput.value, passwordInput.value);

  if (result.success) {
    // Route based on role
    if (result.role === 'admin' || result.role === 'superAdmin') {
      router.push({ name: 'admin-dashboard' });
    } else {
      router.push({ name: 'chats' });
    }
  } else {
    errorMessage.value = result.message;
  }
};
</script>

<template>
  <div
    class="d-flex align-items-center justify-content-center vh-100 bg-light w-100 position-absolute top-0 start-0 z-3"
  >
    <div class="card shadow-sm border-0" style="width: 100%; max-width: 400px">
      <div class="card-body p-5">
        <div class="text-center mb-4">
          <h2 class="fw-bold text-success">LLM App</h2>
          <p class="text-muted">Sign in to continue</p>
        </div>

        <div v-if="errorMessage" class="alert alert-danger py-2 small mb-3">
          {{ errorMessage }}
        </div>

        <form @submit.prevent="handleLogin">
          <div class="mb-3">
            <label class="form-label fw-semibold small">Login</label>
            <input
              type="text"
              class="form-control bg-light"
              v-model="loginInput"
              required
              placeholder="Enter login"
            />
          </div>

          <div class="mb-4">
            <label class="form-label fw-semibold small">Password</label>
            <input
              type="password"
              class="form-control bg-light"
              v-model="passwordInput"
              required
              placeholder="Enter password"
            />
          </div>

          <button type="submit" class="btn btn-success w-100 py-2 fw-bold shadow-sm">
            Sign In
          </button>
        </form>

        <div class="text-center mt-4 pt-3 border-top">
          <span class="text-muted small">Don't have an account? </span>
          <!-- We will create this route next -->
          <RouterLink to="/register" class="text-success text-decoration-none fw-semibold small"
            >Register here</RouterLink
          >
        </div>
      </div>
    </div>
  </div>
</template>
