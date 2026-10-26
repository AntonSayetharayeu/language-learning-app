import { computed, ref } from 'vue';
import { useLanguages } from './languagesStore';

const { getLanguageById } = useLanguages();

const initialChats = [
  {
    id: 1,
    lecturerId: 1,
    studentId: 2,
    learning: getLanguageById(0),
    native: getLanguageById(2),
    isActive: true,
  },
  {
    id: 2,
    lecturerId: 1,
    studentId: 3,
    learning: getLanguageById(1),
    native: getLanguageById(3),
    isActive: true,
  },
  {
    id: 3,
    lecturerId: 1,
    studentId: 3,
    learning: getLanguageById(2),
    native: getLanguageById(5),
    isActive: false,
  },
  {
    id: 7,
    lecturerId: 8,
    studentId: 3,
    learning: getLanguageById(1),
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
    const index = chats.value.findIndex((c) => c.id === Number(id));
    if (index !== -1) {
      chats.value[index] = { ...chats.value[index], id: Number(id), isActive: false };
      syncStorage();
    }
  };

  const getChatById = (id) => {
    return chats.value.find((c) => c.id === Number(id));
  };

  const getActiveChatsByUserId = (userId) => {
    return chats.value.filter(
      (c) => (c.lecturerId === Number(userId) || c.studentId === Number(userId)) && c.isActive,
    );
  };

  const getArchivedChatsByUserId = (userId) => {
    return chats.value.filter(
      (c) => (c.lecturerId === Number(userId) || c.studentId === Number(userId)) && !c.isActive,
    );
  };

  const getChatPartnerId = (chatId, userId) => {
    const chat = getChatById(chatId);
    if (!chat) return null;

    const numericUserId = Number(userId);
    return chat.lecturerId === numericUserId ? chat.studentId : chat.lecturerId;
  };

  return {
    chats,
    activeChats,
    archivedChats,
    createChat,
    archiveChat,
    getChatById,
    getActiveChatsByUserId,
    getArchivedChatsByUserId,
    getChatPartnerId,
  };
}
