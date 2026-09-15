import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/main.scss';

const app = createApp(App);

app.use(router);

// Configure the global error handler
app.config.errorHandler = (err, instance, info) => {
  console.group('Vue Global Error Caught.');

  // 1. The actual JavaScript error object (includes the stack trace!)
  console.error('Error:', err);

  // 2. The Vue component instance where the error happened
  console.log('Component Instance:', instance);

  // 3. Vue-specific info about where it happened (e.g., "native event handler", "setup function")
  console.warn('Vue Info:', info);

  console.groupEnd();

  // Optional: Send this error to a service like Sentry or LogRocket here
};

app.mount('#app');
