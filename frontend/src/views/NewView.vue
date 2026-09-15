<script setup>
const route = useRoute().path;
</script>

<template>
  <div class="container">
    <h1>Totally new</h1>
    <p>
      Lorem ipsum dolor, sit amet consectetur adipisicing elit. Molestiae aspernatur harum beatae
      mollitia architecto id totam voluptatibus error, nesciunt fugit asperiores nam veniam numquam
      nostrum delectus, sunt aut, dolores odit.
    </p>
    <RouterLink v-bind:to="{ route }">Return to Home!</RouterLink>
  </div>
</template>
