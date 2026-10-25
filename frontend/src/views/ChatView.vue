<script setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue';
import { useRoute } from 'vue-router';
import ChatComponent from '@/components/ChatComponent.vue';
import MessageComponent from '@/components/MessageComponent.vue';
import { useChats } from '@/services/chatsStore';
import { useMessages } from '@/services/messagesStore';

const { activeChats } = useChats();

const route = useRoute();

const activeChatId = computed(() => Number(route.params.id));

const activeChat = computed(() => {
  return activeChats.find((c) => c.id === activeChatId.value) || activeChats.value[0];
});

const { getMessagesByChatId, addMessage } = useMessages();
const messages = computed(() => getMessagesByChatId(activeChatId.value));

const newMessage = ref('');

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
</script>

<template>
  <div class="container-fluid py-3 chat-screen-wrapper h-100">
    <div class="row g-3 h-100">
      <!-- Left Sidebar -->
      <div class="col-md-3 border-end pe-3 overflow-y-auto h-100">
        <ChatComponent
          v-for="chat in activeChats"
          :key="chat.id"
          :chat="chat"
          size="small"
          :isActive="chat.id === activeChatId"
        />
      </div>

      <!-- Chat Window -->
      <div class="col-md-9 d-flex flex-column h-100 overflow-hidden">
        <div
          class="d-flex align-items-center justify-content-between border-bottom pb-2 mb-3 px-2 flex-shrink-0"
        >
          <div>
            <h4 class="m-0 fw-semibold">{{ activeChat.username }}</h4>
            <div class="d-flex gap-2">
              <span
                class="badge bg-success bg-opacity-10 text-success border border-success-subtle mt-1"
              >
                Learning: {{ activeChat.learning }}
              </span>
              <span
                class="badge bg-primary bg-opacity-10 text-primary border border-primary-subtle mt-1"
              >
                Native: {{ activeChat.native }}
              </span>
            </div>
          </div>
          <div class="d-flex align-items-center gap-3">
            <a href="#" class="text-danger fw-semibold">Archive</a>
            <a href="#" class="text-dark text-decoration-none small fw-semibold">Topics</a>
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
        <div class="d-flex align-items-center gap-2 flex-shrink-0 pb-2">
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
      </div>
    </div>
  </div>
</template>
