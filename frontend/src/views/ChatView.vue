<script setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue';
import { useRoute } from 'vue-router';
import ChatComponent from '@/components/ChatComponent.vue';
import MessageComponent from '@/components/MessageComponent.vue';
import { useChats } from '@/services/chatsStore';
import { useMessages } from '@/services/messagesStore';
import { useTopics } from '@/services/topicsStore';

const route = useRoute();

const { activeChats, getChatById } = useChats();
const activeChatId = computed(() => Number(route.params.id));
const actualChat = computed(() => getChatById(activeChatId.value));
const isActiveChat = computed(() => actualChat.value.isActive);

const { getMessagesByChatId, addMessage } = useMessages();
const messages = computed(() => getMessagesByChatId(activeChatId.value));

const newMessage = ref('');

const { topics, getUniqueCategories, getTopicsByCategory } = useTopics();
const showTopics = ref(false);
const categories = computed(() => getUniqueCategories());

const chatContainer = ref(null);
const scrollToBottom = () => {
  nextTick(() => {
    if (chatContainer.value) {
      chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
    }
  });
};

onMounted(() => {
  scrollToBottom();
});

watch(
  messages,
  () => {
    scrollToBottom();
  },
  { deep: true },
);

const sendMessage = () => {
  if (!newMessage.value.trim()) return;
  addMessage({
    chatId: activeChatId.value,
    text: newMessage.value,
    time: Date.now(),
    isOutgoing: true,
  });
  newMessage.value = '';
};

const archiveChat = () => {
  if (actualChat.value && confirm('Are you sure you want to archive this chat?')) {
    actualChat.value.isActive = false;
    router.push({ name: 'chats' });
  }
};

const toggleTopics = () => {
  showTopics.value = !showTopics.value;
};
</script>

<template>
  <div class="container-fluid py-3 chat-screen-wrapper h-100">
    <div class="row g-3 h-100">
      <!-- Left Sidebar -->
      <div v-if="isActiveChat" class="col-md-3 border-end pe-3 overflow-y-auto h-100">
        <ChatComponent
          v-for="chat in activeChats"
          :key="chat.id"
          :chat="chat"
          size="small"
          :isSelected="chat.id === activeChatId"
        />
      </div>

      <!-- Chat Window -->
      <div
        :class="isActiveChat ? 'col-md-9' : 'col-md-12'"
        class="d-flex flex-column h-100 overflow-hidden"
      >
        <div
          class="d-flex align-items-center justify-content-between border-bottom pb-2 mb-3 px-2 flex-shrink-0"
        >
          <div>
            <h4 class="m-0 fw-semibold">{{ actualChat.username }}</h4>
            <div class="d-flex gap-2">
              <span
                class="badge bg-success bg-opacity-10 text-success border border-success-subtle mt-1"
              >
                Learning: {{ actualChat.learning }}
              </span>
              <span
                class="badge bg-primary bg-opacity-10 text-primary border border-primary-subtle mt-1"
              >
                Native: {{ actualChat.native }}
              </span>
            </div>
          </div>
          <div class="d-flex align-items-center gap-3">
            <template v-if="isActiveChat">
              <a href="#" @click.prevent="archiveChat" class="text-danger fw-semibold">Archive</a>
              <div class="position-relative">
                <a
                  href="#"
                  @click.prevent="toggleTopics"
                  class="text-dark text-decoration-none small fw-semibold"
                  >Topics</a
                >

                <!-- Topics -->
                <div
                  v-if="showTopics"
                  class="topics-popup position-absolute bg-white border rounded shadow p-3 top-100 end-0 mt-2 z-3"
                >
                  <div
                    class="d-flex justify-content-between align-items-center mb-2 border-bottom pb-1"
                  >
                    <h6 class="m-0 fw-bold">Conversation Topics</h6>
                    <button
                      type="button"
                      class="btn-close"
                      @click="showTopics = false"
                      aria-label="close"
                    ></button>
                  </div>
                  <div v-for="category in categories" :key="category" class="mb-3">
                    <span class="text-secondary small fw-bold d-block mb-1">{{ category }}</span>
                    <ul class="list-unstyled mb-0 ms-2">
                      <li
                        v-for="topic in getTopicsByCategory(category)"
                        :key="topic.text"
                        class="small text-dark mb-1"
                      >
                        • {{ topic.text }}
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </template>
            <img src="@/assets/user.png" alt="profile icon" class="image rounded-circle" />
          </div>
        </div>

        <!-- Chat messages itself -->
        <div
          ref="chatContainer"
          class="chat flex-grow-1 p-3 mb-3 bg-white border rounded shadow-sm overflow-y-auto"
        >
          <MessageComponent v-for="msg in messages" :key="msg.id" :message="msg" />
        </div>

        <!-- Input Area -->
        <div class="flex-shrink-0 pb-2">
          <div v-if="isActiveChat" class="d-flex align-items-center gap-2">
            <div class="input-group bg-light rounded-pill px-3 py-1 border flex-grow-1">
              <textarea
                type="text"
                v-model="newMessage"
                @keyup.enter="sendMessage"
                placeholder="Type some message..."
                class="message-textarea form-control border-0 bg-transparent shadow-none"
                rows="1"
              ></textarea>
            </div>
            <button
              @click="sendMessage"
              class="send-btn btn btn-primary rounded-circle p-0 d-flex align-items-center justify-content-center shadow-sm"
              aria-label="Send message"
            >
              <img src="@/assets/send.png" alt="icon" class="small-image" />
            </button>
          </div>
          <div v-else class="text-center p-3 bg-light rounded border text-muted fw-semibold">
            This chat is archived and is read-only
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
