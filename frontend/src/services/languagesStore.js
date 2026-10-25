import { ref } from 'vue';

const languages = ref(['German', 'Polish', 'English', 'Spanish', 'French', 'Italian']);

export function useLanguages() {
  const getLanguageById = (id) => {
    return languages.value[id];
  };
  return {
    languages,
    getLanguageById,
  };
}
