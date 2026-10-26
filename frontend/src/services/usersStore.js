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
  { id: 6, login: 'admin', password: 'admin', role: 'admin', rating: 0 },
  { id: 7, login: 'sa', password: 'super', role: 'superAdmin', rating: 0 },
]);

const storedUsers = localStorage.getItem('app_users');
const users = ref(storedUsers ? JSON.parse(storedUsers) : initialUsers);

const syncStorage = () => {
  localStorage.setItem('app_users', JSON.stringify(users.value));
};

// Active user
const storedSession = localStorage.getItem('app_session');
const currentUserId = ref(storedSession ? Number(storedSession) : null);

export function useUsers() {
  const { addProfile, getProfileByUserId, DEFAULT_IMAGE } = useProfiles();

  const addUser = (newUser) => {
    users.value.push({
      id: users.value.length + 1,
      ...newUser,
      rating: getAverageRating(users.value.length + 1),
    });
    syncStorage();
  };

  const updateUser = (id, updatedData) => {
    const index = users.value.findIndex((u) => u.id === Number(id));
    if (index !== -1) {
      users.value[index] = { id: Number(id), ...updatedData };
      syncStorage();
    }
  };

  const deleteUser = (id) => {
    users.value = users.value.filter((u) => u.id !== Number(id));
    syncStorage();
  };

  const getUserById = (id) => {
    const numericId = Number(id);
    const user = users.value.find((u) => Number(u.id) === numericId);

    if (!user) return null;

    const profile = getProfileByUserId(numericId) || {};

    // Spread profile FIRST, then user SECOND, so the user's id is never overwritten.
    return { ...profile, ...user };
  };

  const getCurrentUser = () => {
    return getUserById(currentUserId.value);
  };

  const getUsersByRole = (role) => {
    return users.value.filter((u) => u.role === role);
  };

  const register = (loginStr, password, role, username) => {
    const userExists = users.value.some((u) => u.login === loginStr);
    if (userExists) {
      return { success: false, message: 'This login is already taken.' };
    }

    const newUserId = users.value.length ? users.value.length + 1 : 1;

    const newUser = {
      id: newUserId,
      login: loginStr,
      password: password,
      role: role,
      rating: 0,
    };
    users.value.push(newUser);
    syncStorage();

    addProfile({
      userId: newUserId,
      username: username,
      imgUrl: DEFAULT_IMAGE,
      knownLanguages: [],
      languagesToLearn: [],
    });

    currentUserId.value = newUserId;
    localStorage.setItem('app_session', newUserId);

    return { success: true, role: newUser.role };
  };

  const login = (loginStr, password) => {
    const user = users.value.find((u) => u.login === loginStr && u.password === password);

    if (user) {
      currentUserId.value = user.id;
      localStorage.setItem('app_session', user.id);
      return { success: true, role: user.role };
    }
    return { success: false, message: 'Invalid username or password' };
  };

  const logout = () => {
    currentUserId.value = null;
    localStorage.removeItem('app_session');
  };

  return {
    users,
    currentUserId,
    addUser,
    updateUser,
    deleteUser,
    getUserById,
    getCurrentUser,
    getUsersByRole,
    register,
    login,
    logout,
  };
}
