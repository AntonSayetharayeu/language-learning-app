import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/main.scss';

const app = createApp(App);

app.use(router);

// Global error handler
app.config.errorHandler = (err, instance, info) => {
  console.group('Vue Global Error Caught.');
  console.error('Error:', err);
  console.log('Component Instance:', instance);
  console.warn('Vue Info:', info);
  console.groupEnd();
  // Optional: Send this error to a service
};

app.mount('#app');
