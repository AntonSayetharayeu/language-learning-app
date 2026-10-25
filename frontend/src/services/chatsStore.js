import { computed, ref } from 'vue';
import { useLanguages } from './languagesStore';

const { getLanguageById } = useLanguages();

const initialChats = [
  {
    id: 1,
    username: 'Username1',
    learning: getLanguageById(0),
    native: getLanguageById(2),
    isActive: true,
  },
  {
    id: 2,
    username: 'Username2',
    learning: getLanguageById(1),
    native: getLanguageById(3),
    isActive: true,
  },
  {
    id: 3,
    username: 'Username3',
    learning: getLanguageById(2),
    native: getLanguageById(5),
    isActive: true,
  },
  {
    id: 4,
    username: 'Username4',
    learning: getLanguageById(3),
    native: getLanguageById(5),
    isActive: false,
  },
  {
    id: 5,
    username: 'Username5',
    learning: getLanguageById(4),
    native: getLanguageById(0),
    isActive: true,
  },
  {
    id: 6,
    username: 'Username6',
    learning: getLanguageById(5),
    native: getLanguageById(1),
    isActive: false,
  },
  {
    id: 7,
    username: 'Username7',
    learning: getLanguageById(0),
    native: getLanguageById(2),
    isActive: true,
  },
  {
    id: 8,
    username: 'Username8',
    learning: getLanguageById(1),
    native: getLanguageById(2),
    isActive: false,
  },
  {
    id: 9,
    username: 'Username9',
    learning: getLanguageById(2),
    native: getLanguageById(3),
    isActive: true,
  },
];

const storedChats = localStorage.getItem('app_chats');
const chats = ref(storedChats ? JSON.parse(storedChats) : initialChats);

const syncStorage = () => {
  localStorage.setItem('app_chats', JSON.stringify(chats.value));
};

export function useChats() {
  const activeChats = computed(() => chats.value.filter((chat) => chat.isActive));
  const archivedChats = computed(() => chats.value.filter((chat) => !chat.isActive));

  const createChat = (newChat) => {
    chats.value.push({ id: chats.value.length + 1, ...newChat });
    syncStorage();
  };

  const archiveChat = (id) => {
    const index = chats.value.findIndex((o) => o.id === Number(id));
    if (index !== -1) {
      chats.value[index] = { id: Number(id), isActive: false };
      syncStorage();
    }
  };

  const getChatById = (id) => {
    return chats.value.find((o) => o.id === Number(id));
  };

  return {
    chats,
    activeChats,
    archivedChats,
    createChat,
    archiveChat,
    getChatById,
  };
}
