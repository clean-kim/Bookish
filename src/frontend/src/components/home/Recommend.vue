<template>
  <div id="recommend">
    <h3>사서 선생님이 추천한대요~🤓</h3>
    <vue-glide
        ref="slider"
        v-bind="carouselOptions">
      <vue-glide-slide
          v-for="book in this.$store.state.books" :key="book.isbn" style="width: fit-content;">
        <router-link :to="'/detail/info?isbn='+book.isbn">
          <v-card
              class="ma-4"
          >
            <v-img
                :src="book.image"
                style="width: fit-content;"
            ></v-img>
            <v-card-actions>
              <div style="word-break: break-all;">
                {{book.title}}
              </div>
            </v-card-actions>
          </v-card>
        </router-link>
      </vue-glide-slide>
    </vue-glide>
  </div>
</template>

<script>
import { Glide, GlideSlide } from "vue-glide-js";
import "vue-glide-js/dist/vue-glide.css";

export default {
  name: 'Recommend',
  data: () => ({
    books: []
  }),
  methods: {
  },
  components: {
    [Glide.name]: Glide,
    [GlideSlide.name]: GlideSlide
  },
  computed: {
    carouselOptions() {
      return {
        type: "carousel",
        autoplay: 5000,
        gap: 5,
        hoverpause: true,
        keyboard: false,
        bound: false
      }
    }
  }

}
</script>

<style scoped>

</style>