import { ref } from 'vue';

const initialOffers = [
  {
    id: 1,
    teaching: 'German',
    native: 'Polish',
    description:
      'The really really really really really really really really really really really really long',
  },
  {
    id: 2,
    teaching: 'German',
    native: 'Polish',
    description:
      'The really really really really really really really really really really really really long',
  },
  {
    id: 3,
    teaching: 'German',
    native: 'Polish',
    description:
      'The really really really longhe really really really longhe really really really longhe really really really long',
  },
  {
    id: 4,
    teaching: 'German',
    native: 'Polish',
    description:
      'The really really really really really really really really really really really really long',
  },
  {
    id: 5,
    teaching: 'German',
    native: 'Polish',
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

  return {
    offers,
    addOffer,
    updateOffer,
    deleteOffer,
    getOfferById,
  };
}
