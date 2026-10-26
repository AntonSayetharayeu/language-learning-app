import { ref } from 'vue';

const initialRatings = ref([
  {
    id: 1,
    userId: 2,
    reporterId: 1,
    value: 4.5,
    description: 'Great student, very attentive and catches on to vocabulary quickly.',
  },
  {
    id: 2,
    userId: 2,
    reporterId: 1,
    value: 5.0,
    description: 'Excellent pronunciation and always does the homework.',
  },
  {
    id: 3,
    userId: 3,
    reporterId: 4,
    value: 4.0,
    description: 'Fun language exchange partner, very patient.',
  },
]);

const storedRatings = localStorage.getItem('app_ratings');
const ratings = ref(storedRatings ? JSON.parse(storedRatings) : initialRatings);

const syncStorage = () => {
  localStorage.setItem('app_ratings', JSON.stringify(ratings.value));
};

export function useRatings() {
  const getRatingById = (id) => {
    return ratings.value.find((r) => r.id === id);
  };

  const getRatingsForUser = (userId) => {
    return ratings.value.filter((r) => r.userId === userId);
  };

  const getRatingsByReporter = (reporterId) => {
    return ratings.value.filter((r) => r.reporterId === reporterId);
  };

  const getRatingsByReporterAndPartnerId = (reporterId, userId) => {
    return ratings.value.find((r) => r.reporterId === reporterId && r.userId === userId);
  };

  const getAverageRating = (userId) => {
    const userRatings = getRatingsForUser(userId);
    if (userRatings.length === 0) return 0;

    const sum = userRatings.reduce((acc, curr) => acc + curr.value, 0);
    const exactAverage = sum / userRatings.length;

    return exactAverage;
  };

  const addRating = (ratingData) => {
    const newId = ratings.value.length ? ratings.value.length + 1 : 1;
    ratings.value.push({
      id: newId,
      ...ratingData,
    });
    syncStorage();
  };

  const updateRating = (id, updatedData) => {
    const index = ratings.value.findIndex((r) => r.id === id);
    if (index !== -1) {
      ratings.value[index] = { id: Number(id), ...updatedData };
      syncStorage();
    }
  };

  return {
    ratings,
    getRatingById,
    getRatingsForUser,
    getRatingsByReporter,
    getRatingsByReporterAndPartnerId,
    getAverageRating,
    addRating,
    updateRating,
  };
}
