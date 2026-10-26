import { ref } from 'vue';
import { useLanguages } from './languagesStore';

const { getLanguageById } = useLanguages();

const initialRequests = ref([
  {
    id: 1,
    studentId: 2,
    learning: getLanguageById(2),
    native: getLanguageById(0),
    description:
      'Hi! I am looking for a dedicated tutor to help me with conversational English for my upcoming job interviews in London.',
  },
  {
    id: 2,
    studentId: 3,
    learning: getLanguageById(3),
    native: getLanguageById(0),
    description:
      'I need help preparing for my IELTS exam. I am currently at a B2 level and want to reach C1.',
  },
  {
    id: 3,
    studentId: 2,
    learning: getLanguageById(1),
    native: getLanguageById(2),
    description:
      'Beginner here. Want to learn basic phrases for traveling and making friends online.',
  },
  {
    id: 4,
    studentId: 2,
    learning: getLanguageById(4),
    native: getLanguageById(5),
    description:
      'Looking for someone to practice business English with, focusing heavily on writing professional emails.',
  },
]);

const storedRequests = localStorage.getItem('app_requests');
const requests = ref(storedRequests ? JSON.parse(storedRequests) : initialRequests);

const syncStorage = () => {
  localStorage.setItem('app_requests', JSON.stringify(requests.value));
};

export function useRequests() {
  const addRequest = (newRequest) => {
    requests.value.push({ id: requests.value.length + 1, ...newRequest });
    syncStorage();
  };

  const updateRequest = (id, updatedData) => {
    const index = requests.value.findIndex((o) => o.id === Number(id));
    if (index !== -1) {
      requests.value[index] = { id: Number(id), ...updatedData };
      syncStorage();
    }
  };

  const deleteRequest = (id) => {
    requests.value = requests.value.filter((o) => o.id !== Number(id));
    syncStorage();
  };

  const getRequestById = (id) => {
    return requests.value.find((o) => o.id === Number(id));
  };

  return {
    requests,
    addRequest,
    updateRequest,
    deleteRequest,
    getRequestById,
  };
}
