<script setup>
import ChatComponent from '@/components/ChatComponent.vue';
import MessageComponent from '@/components/MessageComponent.vue';
import TopicsListComponent from '@/components/TopicsListComponent.vue';
import { useChats } from '@/services/chatsStore';
import { useMessages } from '@/services/messagesStore';
import { useRatings } from '@/services/ratingsStore';
import { useUsers } from '@/services/usersStore';
import { computed, nextTick, onMounted, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const { getActiveChatsByUserId, getChatById, getChatPartnerId } = useChats();
const { getMessagesByChatId, addMessage } = useMessages();
const { currentUserId } = useUsers();
const { addRating, updateRating, getRatingById, getRatingsByReporterAndPartnerId } = useRatings();

const userChats = computed(() => getActiveChatsByUserId(currentUserId.value));
const activeChatId = computed(() => Number(route.params.id));
const actualChat = computed(() => getChatById(activeChatId.value));
const isActiveChat = computed(() => actualChat.value.isActive);

const messages = computed(() => getMessagesByChatId(activeChatId.value));
const newMessage = ref('');

const showTopics = ref(false);

const partnerId = computed(() => getChatPartnerId(activeChatId.value, currentUserId.value));

const showRating = ref(false);
const ratingValue = ref(0);
const ratingText = ref('');
const existingRatingId = ref(null);

const toggleRating = () => {
  showTopics.value = false;
  showRating.value = !showRating.value;

  if (showRating.value) {
    const existing = getRatingsByReporterAndPartnerId(currentUserId.value, partnerId.value);

    if (existing) {
      existingRatingId.value = existing.id;
      ratingValue.value = existing.value;
      ratingText.value = existing.description;
    } else {
      existingRatingId.value = null;
      ratingValue.value = 0;
      ratingText.value = '';
    }
  }
};

const setRating = (val) => {
  ratingValue.value = val;
};

const submitRating = () => {
  if (ratingValue.value === 0) return;

  const existing = getRatingById(existingRatingId.value);
  if (existingRatingId.value) {
    updateRating(existingRatingId.value, {
      userId: existing.userId,
      reporterId: existing.reporterId,
      value: ratingValue.value,
      description: ratingText.value,
    });
  } else {
    addRating({
      userId: partnerId.value,
      reporterId: currentUserId.value,
      value: ratingValue.value,
      description: ratingText.value,
    });
  }
  showRating.value = false;
};

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
    senderId: currentUserId.value,
    text: newMessage.value,
    time: Date.now(),
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
  showRating.value = false;
  showTopics.value = !showTopics.value;
};

watch(activeChatId, () => {
  showTopics.value = false;
  showRating.value = false;
});
</script>

<template>
  <div class="container-fluid py-3 chat-screen-wrapper h-100">
    <div class="row g-3 h-100">
      <!-- Left Sidebar -->
      <div v-if="isActiveChat" class="col-md-3 border-end pe-3 overflow-y-auto h-100">
        <ChatComponent
          v-for="chat in userChats"
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

                <TopicsListComponent :showTopics="showTopics" />
              </div>
            </template>

            <!-- Rating -->
            <div class="position-relative">
              <img
                src="@/assets/user.png"
                alt="profile icon"
                class="image rounded-circle border cursor-pointer"
                @click="toggleRating"
              />
              <div
                v-if="showRating"
                class="rating-popup position-absolute bg-white border rounded shadow p-3 top-100 end-0 mt-2 z-3"
              >
                <div class="d-flex justify-content-center gap-2 mb-3">
                  <img
                    v-for="n in 5"
                    :key="n"
                    :src="n <= ratingValue ? '/src/assets/star.png' : '/src/assets/empty-star.png'"
                    alt="star"
                    class="small-image cursor-pointer"
                    @click="setRating(n)"
                  />
                </div>

                <textarea
                  v-model="ratingText"
                  class="resize-none form-control mb-3 text-secondary small bg-light border-0"
                  rows="4"
                  placeholder="Provide some thoughts about student..."
                ></textarea>

                <button class="btn btn-primary w-100 rounded-pill shadow-sm" @click="submitRating">
                  {{ existingRatingId ? 'Update Review' : 'Leave the Review' }}
                </button>
              </div>
            </div>
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
