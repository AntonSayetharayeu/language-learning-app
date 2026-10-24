<script setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue';
import { useRoute } from 'vue-router';
import ChatComponent from '@/components/ChatComponent.vue';
import MessageComponent from '@/components/MessageComponent.vue';

const chats = ref([
  { id: 1, username: 'Username1', learning: 'German' },
  { id: 2, username: 'Username2', learning: 'Polish' },
  { id: 3, username: 'Username3', learning: 'English' },
]);

const route = useRoute();

const activeChatId = computed(() => Number(route.params.id));

const activeChat = computed(() => {
  return chats.value.find((c) => c.id === activeChatId.value) || chats.value[0];
});

const messages = ref([
  {
    id: 1,
    text: 'Hej-hej, chciałbym się spotkać na żywo, może pójdziemy do restauracji Na Rogu?',
    time: '14:23',
    isOutgoing: false,
  },
  { id: 2, text: 'Muszę sprawdzić, czekaj.', time: '14:30', isOutgoing: true },
  { id: 3, text: 'Proponuję godzinę 18:30 w najbliższy wtorek.', time: '14:35', isOutgoing: true },
  {
    id: 4,
    text: 'Fajnie, mi jak najbardziej odpowiada ten termin :)',
    time: '15:00',
    isOutgoing: false,
  },
  {
    id: 5,
    text: 'DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD',
    time: '16:20',
    isOutgoing: false,
  },
  {
    id: 6,
    text: 'DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD',
    time: '16:30',
    isOutgoing: true,
  },
  {
    id: 7,
    text: 'DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD',
    time: '18:30',
    isOutgoing: true,
  },
]);

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
  messages.value.push({
    id: Date.now(),
    text: newMessage.value,
    time: 'Just now',
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
          v-for="chat in chats"
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
            <span
              class="badge bg-success bg-opacity-10 text-success border border-success-subtle mt-1"
            >
              Learning: {{ activeChat.learning }}
            </span>
          </div>
          <div class="d-flex align-items-center gap-3">
            <a href="#" class="text-danger fw-semibold">Archive</a>
            <a href="#" class="text-dark text-decoration-none small fw-semibold">Topics</a>
            <img
              src="@/assets/user.png"
              alt="profile icon"
              class="profile-in-chat rounded-circle"
            />
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
            <img src="../assets/send.png" alt="icon" class="send-btn-image" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
