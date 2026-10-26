<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUsers } from '@/services/usersStore';

const router = useRouter();
const { register } = useUsers();

const formData = ref({
  login: '',
  password: '',
  username: '',
  role: 'student',
});

const errorMessage = ref('');

const handleRegister = () => {
  errorMessage.value = '';

  const result = register(
    formData.value.login,
    formData.value.password,
    formData.value.role,
    formData.value.username,
  );

  if (result.success) {
    router.push({ name: 'chats' });
  } else {
    errorMessage.value = result.message;
  }
};
</script>

<template>
  <div
    class="d-flex align-items-center justify-content-center min-vh-100 bg-light w-100 position-absolute top-0 start-0 z-3 py-5"
  >
    <div class="card shadow-sm border-0" style="width: 100%; max-width: 450px">
      <div class="card-body p-5">
        <div class="text-center mb-4">
          <h2 class="fw-bold text-success">LLM App</h2>
          <p class="text-muted">Create a new account</p>
        </div>

        <div v-if="errorMessage" class="alert alert-danger py-2 small mb-3">
          {{ errorMessage }}
        </div>

        <form @submit.prevent="handleRegister">
          <div class="mb-3">
            <label class="form-label fw-semibold small">Login</label>
            <input
              type="text"
              class="form-control bg-light"
              v-model="formData.login"
              required
              placeholder="Choose a unique login"
            />
          </div>

          <div class="mb-3">
            <label class="form-label fw-semibold small">Password</label>
            <input
              type="password"
              class="form-control bg-light"
              v-model="formData.password"
              required
              placeholder="Create a password"
              minlength="4"
            />
          </div>

          <hr class="my-4 text-muted" />

          <div class="mb-3">
            <label class="form-label fw-semibold small">Public Username</label>
            <input
              type="text"
              class="form-control bg-light"
              v-model="formData.username"
              required
              placeholder="How others will see you"
            />
          </div>

          <div class="mb-4">
            <label class="form-label fw-semibold small">I want to be a...</label>
            <select class="form-select bg-light" v-model="formData.role">
              <option value="student">Student (Learning)</option>
              <option value="lecturer">Lecturer (Teaching)</option>
            </select>
          </div>

          <button type="submit" class="btn btn-success w-100 py-2 fw-bold shadow-sm">
            Register
          </button>
        </form>

        <div class="text-center mt-4 pt-3 border-top">
          <span class="text-muted small">Already have an account? </span>
          <RouterLink to="/login" class="text-success text-decoration-none fw-semibold small"
            >Sign in here</RouterLink
          >
        </div>
      </div>
    </div>
  </div>
</template>
