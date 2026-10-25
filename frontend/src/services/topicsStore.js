import { ref } from 'vue';

const basicTopics = ref([
  {
    id: 1,
    text: 'What does a typical day look like for you?',
    category: 'Daily Life',
  },
  {
    id: 2,
    text: 'How do you usually spend your weekends?',
    category: 'Daily Life',
  },
  {
    id: 3,
    text: 'What is your morning routine?',
    category: 'Daily Life',
  },
  {
    id: 4,
    text: 'What kind of music are you into lately?',
    category: 'Hobbies & Interests',
  },
  {
    id: 5,
    text: 'Do you play any sports or exercise regularly?',
    category: 'Hobbies & Interests',
  },
  {
    id: 6,
    text: 'What was the last good movie or book you experienced?',
    category: 'Hobbies & Interests',
  },
  {
    id: 7,
    text: "What is the most beautiful place you've ever visited?",
    category: 'Travel & Culture',
  },
  {
    id: 8,
    text: 'If you could travel anywhere right now, where would it be?',
    category: 'Travel & Culture',
  },
]);

const storedTopics = localStorage.getItem('app_topics');
const topics = ref(storedTopics ? JSON.parse(storedTopics) : basicTopics);

const syncStorage = () => {
  localStorage.setItem('app_topics', JSON.stringify(topics.value));
};

export function useTopics() {
  const addTopic = (topic) => {
    basicTopics.value.push({ id: basicTopics.value.length, ...topic });
    syncStorage;
  };

  const getUniqueCategories = () => {
    return [...new Set(topics.value.map((topic) => topic.category))];
  };

  const getTopicsByCategory = (category) => {
    return topics.value.filter((topic) => topic.category === category);
  };

  return {
    topics,
    addTopic,
    getUniqueCategories,
    getTopicsByCategory,
  };
}
