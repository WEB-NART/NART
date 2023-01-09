<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
<div id="page" class="w-full h-full fixed top-0 left-0 m-0 overflow-auto">
  <RouterView />
</div>
</template>

<style>
.text {
  font-size: 1.5em;
  font-weight: 300;
  color: black;
}
.img {
  width: 150px;
  min-width: 100px;
  height: 150px;
  min-height: 100px;
  margin-left: 5px;
}
.infinite-list {
  list-style: none;
}
.title {
  font-weight: 500;
}
</style>
