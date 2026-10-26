import { computed, ref } from 'vue';

const initialPings = [
  {
    id: 1,
    senderId: 1,
    receiverId: 2,
    basedOnId: 1,
    basedOnType: 'request',
    status: 'pending',
    createdAt: Date.now() - 3600000,
  },
  {
    id: 2,
    senderId: 4,
    receiverId: 2,
    basedOnId: 4,
    basedOnType: 'offer',
    status: 'pending',
    createdAt: Date.now() - 86400000,
  },
  {
    id: 3,
    senderId: 3,
    receiverId: 1,
    basedOnId: 4,
    basedOnType: 'offer',
    status: 'pending',
    createdAt: Date.now() - 86400000,
  },
];

const storedPings = localStorage.getItem('app_pings');
const pings = ref(storedPings ? JSON.parse(storedPings) : initialPings);

const syncStorage = () => {
  localStorage.setItem('app_pings', JSON.stringify(pings.value));
};

export function usePings() {
  const getSentPings = (userId) => {
    return pings.value.filter((p) => p.senderId === userId);
  };

  const getReceivedPings = (userId) => {
    return pings.value.filter((p) => p.receiverId === userId);
  };

  const isAlreadyPinged = (senderId, basedOnId, basedOnType) => {
    return pings.value.some(
      (p) => p.senderId === senderId && p.basedOnId === basedOnId && p.basedOnType === basedOnType,
    );
  };

  const addPing = (pingData) => {
    const newId = pings.value.length ? pings.value.length + 1 : 1;
    pings.value.push({
      id: newId,
      status: 'pending',
      createdAt: Date.now(),
      ...pingData,
    });
    syncStorage();
  };

  const updatePingStatus = (id, newStatus) => {
    const index = pings.value.findIndex((p) => p.id === id);
    if (index !== -1) {
      pings.value[index].status = newStatus;
      syncStorage();
    }
  };

  const declineCompetingPings = (acceptedPingId, basedOnId, basedOnType) => {
    let changed = false;
    pings.value.forEach((p) => {
      if (
        p.id !== acceptedPingId &&
        p.basedOnId === basedOnId &&
        p.basedOnType === basedOnType &&
        p.status === 'pending'
      ) {
        p.status = 'declined';
        changed = true;
      }
    });
    if (changed) syncStorage();
  };

  return {
    pings,
    getSentPings,
    getReceivedPings,
    isAlreadyPinged,
    addPing,
    updatePingStatus,
    declineCompetingPings,
  };
}
