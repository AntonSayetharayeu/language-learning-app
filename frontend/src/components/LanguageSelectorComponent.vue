<script setup>
import { computed, unref } from 'vue';
import { useLanguages } from '@/services/languagesStore';

const props = defineProps({
  modelValue: {
    type: Array,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  badgeClass: {
    type: String,
    default: 'bg-primary',
  },
});

const emit = defineEmits(['update:modelValue']);
const { languages } = useLanguages();

const allLanguages = computed(() => {
  const raw = unref(languages) || [];
  return raw.map((name, index) => ({ id: index, name }));
});

const normalizedSelectedIds = computed(() => {
  const raw = unref(languages) || [];
  return props.modelValue
    .map((item) => {
      if (typeof item === 'string' && isNaN(Number(item))) {
        return raw.indexOf(item);
      }
      return Number(item?.id ?? item);
    })
    .filter((id) => id !== -1 && !isNaN(id));
});

const selectedLangs = computed(() =>
  allLanguages.value.filter((lang) => normalizedSelectedIds.value.includes(lang.id)),
);

const availableLangs = computed(() =>
  allLanguages.value.filter((lang) => !normalizedSelectedIds.value.includes(lang.id)),
);

const toggleLanguage = (langId) => {
  const newSelection = [...normalizedSelectedIds.value];
  const index = newSelection.indexOf(langId);

  if (index === -1) {
    newSelection.push(langId);
  } else {
    newSelection.splice(index, 1);
  }

  emit('update:modelValue', newSelection);
};
</script>

<template>
  <div class="mb-4">
    <label class="form-label fw-bold">{{ title }}</label>

    <div class="border rounded p-3 mb-2 bg-light">
      <span class="text-muted small d-block mb-2">Selected (Click to remove)</span>
      <div class="d-flex flex-wrap gap-2">
        <span v-if="!selectedLangs.length" class="text-muted small">None selected</span>
        <span
          v-for="lang in selectedLangs"
          :key="lang.id"
          class="badge rounded-pill cursor-pointer"
          :class="badgeClass"
          @click="toggleLanguage(lang.id)"
        >
          {{ lang.name }} &times;
        </span>
      </div>
    </div>

    <div class="border rounded p-3">
      <span class="text-muted small d-block mb-2">Available (Click to add)</span>
      <div class="d-flex flex-wrap gap-2">
        <span v-if="!availableLangs.length" class="text-muted small">All selected</span>
        <span
          v-for="lang in availableLangs"
          :key="lang.id"
          class="badge rounded-pill bg-secondary text-light cursor-pointer"
          @click="toggleLanguage(lang.id)"
        >
          {{ lang.name }} +
        </span>
      </div>
    </div>
  </div>
</template>
