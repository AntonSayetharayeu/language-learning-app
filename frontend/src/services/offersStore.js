import { ref } from 'vue';
import { useLanguages } from './languagesStore';

const { getLanguageById } = useLanguages();

const initialOffers = [
  {
    id: 1,
    lecturerId: 1,
    teaching: getLanguageById(0),
    native: getLanguageById(1),
    description:
      'The really really really really really really really really really really really really long',
  },
  {
    id: 2,
    lecturerId: 1,
    teaching: getLanguageById(1),
    native: getLanguageById(2),
    description:
      'The really really really really really really really really really really really really long',
  },
  {
    id: 3,
    lecturerId: 1,
    teaching: getLanguageById(2),
    native: getLanguageById(3),
    description:
      'The really really really longhe really really really longhe really really really longhe really really really long',
  },
  {
    id: 4,
    lecturerId: 4,
    teaching: getLanguageById(3),
    native: getLanguageById(4),
    description:
      'The really really really really really really really really really really really really long',
  },
  {
    id: 5,
    lecturerId: 4,
    teaching: getLanguageById(4),
    native: getLanguageById(5),
    description:
      'The really really really really really really really really really really really really long',
  },
];

const storedOffers = localStorage.getItem('app_offers');
const offers = ref(storedOffers ? JSON.parse(storedOffers) : initialOffers);

const syncStorage = () => {
  localStorage.setItem('app_offers', JSON.stringify(offers.value));
};

export function useOffers() {
  const addOffer = (newOffer) => {
    offers.value.push({ id: offers.value.length + 1, ...newOffer });
    syncStorage();
  };

  const updateOffer = (id, updatedData) => {
    const index = offers.value.findIndex((o) => o.id === Number(id));
    if (index !== -1) {
      offers.value[index] = { id: Number(id), ...updatedData };
      syncStorage();
    }
  };

  const deleteOffer = (id) => {
    offers.value = offers.value.filter((o) => o.id !== Number(id));
    syncStorage();
  };

  const getOfferById = (id) => {
    return offers.value.find((o) => o.id === Number(id));
  };

  const getOfferByLecturerId = (id) => {
    return offers.value.filter((o) => o.lecturerId === Number(id));
  };

  return {
    offers,
    addOffer,
    updateOffer,
    deleteOffer,
    getOfferById,
    getOfferByLecturerId,
  };
}
