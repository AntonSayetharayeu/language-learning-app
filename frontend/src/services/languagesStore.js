import { ref } from 'vue';

const initialLanguages = ['German', 'Polish', 'English', 'Spanish', 'French', 'Italian'];

const storedLanguages = localStorage.getItem('app_languages');
const languages = ref(storedLanguages ? JSON.parse(storedLanguages) : initialLanguages);

export function useLanguages() {
  const getLanguageById = (id) => {
    return languages.value[id];
  };
  return {
    languages,
    getLanguageById,
  };
}
