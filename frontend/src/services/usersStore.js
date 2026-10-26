import { computed, ref } from 'vue';
import { useLanguages } from './languagesStore';
import { useRatings } from './ratingsStore';

const { getLanguageById } = useLanguages();
const { getAverageRating } = useRatings();

const initialUsers = computed(() => [
  {
    id: 1,
    username: 'Dr. Smith',
    login: 'smith_teach',
    password: '123',
    imgUrl: '@/assets/user.png',
    role: 'lecturer',
    rating: getAverageRating(1),
    knownLanguages: [getLanguageById(0), getLanguageById(1), getLanguageById(2)],
    languagesToLearn: [],
  },
  {
    id: 2,
    username: 'Alex Johnson',
    login: 'alex_j',
    password: '123',
    imgUrl: '@/assets/user.png',
    role: 'student',
    rating: getAverageRating(2),
    knownLanguages: [getLanguageById(1), getLanguageById(2)],
    languagesToLearn: [getLanguageById(0), getLanguageById(5)],
  },
  {
    id: 3,
    username: 'Maria Garcia',
    login: 'maria_g',
    password: '123',
    imgUrl: '@/assets/user.png',
    role: 'student',
    rating: getAverageRating(3),
    knownLanguages: [getLanguageById(0)],
    languagesToLearn: [getLanguageById(2), getLanguageById(0)],
  },
]);

const storedUsers = localStorage.getItem('app_users');
const users = ref(storedUsers ? JSON.parse(storedUsers) : initialUsers);

const syncStorage = () => {
  localStorage.setItem('app_users', JSON.stringify(users.value));
};

// Active user
const currentUserId = ref(1);

export function useUsers() {
  const getUserById = (id) => {
    return users.value.find((u) => u.id === id);
  };

  const getCurrentUser = () => {
    return getUserById(currentUserId.value);
  };

  const getUsersByRole = (role) => {
    return users.value.filter((u) => u.role === role);
  };

  return {
    users,
    currentUserId,
    getUserById,
    getCurrentUser,
    getUsersByRole,
  };
}
