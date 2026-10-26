import { computed, ref } from 'vue';
import { useProfiles } from './profilesStore';
import { useRatings } from './ratingsStore';

const { getAverageRating } = useRatings();

const initialUsers = computed(() => [
  { id: 1, login: 'smith_teach', password: '123', role: 'lecturer', rating: getAverageRating(1) },
  { id: 2, login: 'alex_j', password: '123', role: 'student', rating: getAverageRating(2) },
  { id: 3, login: 'maria_g', password: '123', role: 'student', rating: getAverageRating(3) },
  {
    id: 4,
    login: 'davis_t',
    password: '123',
    role: 'lecturer',
    rating: getAverageRating(4),
  },
  { id: 5, login: 'emma_w', password: '123', role: 'student', rating: getAverageRating(5) },
]);

const storedUsers = localStorage.getItem('app_users');
const users = ref(storedUsers ? JSON.parse(storedUsers) : initialUsers);

const syncStorage = () => {
  localStorage.setItem('app_users', JSON.stringify(users.value));
};

// Active user
const currentUserId = ref(2);

export function useUsers() {
  const { getProfileByUserId } = useProfiles();

  const getUserById = (id) => {
    const user = users.value.find((u) => u.id === id);
    if (!user) return null;

    const profile = getProfileByUserId(id);
    return { ...user, ...profile };
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
