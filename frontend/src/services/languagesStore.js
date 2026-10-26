import { ref } from 'vue';

const initialLanguages = ['German', 'Polish', 'English', 'Spanish', 'French', 'Italian'];

const storedLanguages = localStorage.getItem('app_languages');
const languages = ref(storedLanguages ? JSON.parse(storedLanguages) : initialLanguages);

const syncStorage = () => {
  localStorage.setItem('app_languages', JSON.stringify(languages.value));
};

export function useLanguages() {
  const getLanguageById = (id) => {
    return languages.value[Number(id)];
  };

  const addLanguage = (name) => {
    if (!languages.value.includes(name)) {
      languages.value.push(name);
      syncStorage();
    }
  };

  const updateLanguage = (index, newName) => {
    if (languages.value[index] !== undefined) {
      languages.value[index] = newName;
      syncStorage();
    }
  };

  const deleteLanguage = (index) => {
    languages.value.splice(index, 1);
    syncStorage();
  };

  return {
    languages,
    getLanguageById,
    addLanguage,
    updateLanguage,
    deleteLanguage,
  };
}
