import { ref } from 'vue';

const DEFAULT_IMAGE = '/src/assets/user.png';

const initialProfiles = [
  {
    id: 1,
    userId: 1,
    username: 'Dr. Smith',
    imgUrl: DEFAULT_IMAGE,
    knownLanguages: [1, 2, 3],
    languagesToLearn: [],
  },
  {
    id: 2,
    userId: 2,
    username: 'Alex Johnson',
    imgUrl: DEFAULT_IMAGE,
    knownLanguages: [2, 3],
    languagesToLearn: [1, 5],
  },
  {
    id: 3,
    userId: 3,
    username: 'Maria Garcia',
    imgUrl: DEFAULT_IMAGE,
    knownLanguages: [1],
    languagesToLearn: [3, 1],
  },
  {
    id: 4,
    userId: 4,
    username: 'Prof. Davis',
    imgUrl: DEFAULT_IMAGE,
    knownLanguages: [4, 5],
    languagesToLearn: [],
  },
  {
    id: 5,
    userId: 5,
    username: 'Emma Watson',
    imgUrl: DEFAULT_IMAGE,
    knownLanguages: [1],
    languagesToLearn: [2, 4],
  },
];

const storedProfiles = localStorage.getItem('app_profiles');
const profiles = ref(storedProfiles ? JSON.parse(storedProfiles) : initialProfiles);

const syncStorage = () => {
  localStorage.setItem('app_profiles', JSON.stringify(profiles.value));
};

export function useProfiles() {
  const addProfile = (newProfile) => {
    profiles.value.push({ id: profiles.value.length + 1, ...newProfile });
    syncStorage();
  };

  const deleteProfile = (id) => {
    profiles.value = profiles.value.filter((p) => p.id !== Number(id));
    syncStorage();
  };

  const getProfileByUserId = (userId) => {
    console.log(profiles.value);
    console.log(userId);
    return profiles.value.find((p) => p.userId === userId);
  };

  const updateProfile = (userId, updatedData) => {
    const index = profiles.value.findIndex((p) => p.userId === Number(userId));
    if (index !== -1) {
      profiles.value[index] = { ...profiles.value[index], ...updatedData };
      syncStorage();
    }
  };

  return {
    profiles,
    DEFAULT_IMAGE,
    addProfile,
    deleteProfile,
    getProfileByUserId,
    updateProfile,
  };
}
