<template>
  <div id="app">
    <h3>조심! 방금 나와 뜨끈한 책이에요!🔥</h3>
    <vue-glide
        ref="slider"
        :options="options"
        v-model="books">
      <vue-glide-slide
          v-for="book in this.$store.state.newest" :key="book.isbn">
        <v-card
            class="ma-4"
        >
          <v-img
              :src="book.image"
              style="height: fit-content;"
          ></v-img>
          <v-card-actions style="height: fit-content;">
            <div style="word-break: break-all;">
              {{book.title}}
            </div>
          </v-card-actions>
        </v-card>
      </vue-glide-slide>
    </vue-glide>
  </div>
</template>

<script>
import { Glide, GlideSlide } from "vue-glide-js";
import "vue-glide-js/dist/vue-glide.css";

export default {
  name: 'App',
  data: () => ({
    books: [],
    options: {
      type: "carousel",
      autoplay: 5000,
      gap: 5,
      hoverpause: false,
      keyboard: false,
      bound: false
    }
  }),
  methods: {
  },
  components: {
    [Glide.name]: Glide,
    [GlideSlide.name]: GlideSlide
  }
}
</script>

<style scoped>

</style>