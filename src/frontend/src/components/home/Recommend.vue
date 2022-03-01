<template>
  <div id="recommend">
    <h3>사서 선생님이 추천한대요~🤓</h3>
    <vue-glide ref="slider" type="carousel" v-if="books.length">
      <vue-glide-slide v-for="(book, index) in books" :key="index" style="width: fit-content;">
        <router-link :to="'/detail/info?isbn='+book.isbn">
          <v-card class="ma-4">
            <img :src="book.image" style="width: 200px;" />
            <v-card-actions>
              <div style="word-break: break-all;">{{book.title}}</div>
            </v-card-actions>
          </v-card>
        </router-link>
      </vue-glide-slide>
    </vue-glide>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: 'Recommend',
  data: () => ({
    books: [],
  }),
  methods: {
  },
  created() {
    axios.get("/home/recommend")
        .then(res => {
          res.data.forEach(item => this.books.push(item));
        });
  }
}
</script>

<style scoped>

</style>