import { computed, ref } from 'vue';

const API_BASE_URL = '/api/topics'; // Temp

export function useTopics() {
  const topics = ref([]);
  const categories = computed(() => {
    const allCategories = topics.value.map((topic) => topic.category);
    return [...new Set(allCategories)];
  });

  const getAllTopics = async () => {
    try {
      const response = await fetch(API_BASE_URL);
      if (response.ok) {
        topics.value = await response.json();
        return topics.value;
      }
    } catch (error) {
      console.error('Error fetching all topics:', error);
    }
    return [];
  };

  const getTopicsByCategory = async (categoryLabel) => {
    try {
      const response = await fetch(`${API_BASE_URL}/category/${encodeURIComponent(categoryLabel)}`);
      if (response.ok) {
        topics.value = await response.json();
        return topics.value;
      }
    } catch (error) {
      console.error('Error fetching topics by category:', error);
    }
    return [];
  };

  const addTopic = async (newTopic) => {
    try {
      const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTopic),
      });
      if (response.ok) {
        const createdTopic = await response.json();
        topics.value.push(createdTopic);
        return createdTopic;
      }
    } catch (error) {
      console.error('Error adding topic:', error);
    }
  };

  const updateTopic = async (id, updatedData) => {
    try {
      const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedData),
      });
      if (response.ok) {
        const updatedTopic = await response.json();
        const index = topics.value.findIndex((t) => t.id === Number(id));
        if (index !== -1) {
          topics.value[index] = updatedTopic;
        }
      }
    } catch (error) {
      console.error('Error updating topic:', error);
    }
  };

  const deleteTopic = async (id) => {
    try {
      const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        topics.value = topics.value.filter((t) => t.id !== Number(id));
      }
    } catch (error) {
      console.error('Error deleting topic:', error);
    }
  };

  return {
    topics,
    categories,
    getAllTopics,
    addTopic,
    updateTopic,
    deleteTopic,
    getUniqueCategories,
    getTopicsByCategory,
  };
}
