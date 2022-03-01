<template>
  <div id="newest">
    <h3>앗뜨! 방금 나와 뜨끈한 책이에요!🔥</h3>
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
import axios from "axios";

export default {
  name: 'Newest',
  data: () => ({
    books: [],
  }),
  methods: {
  },
  components: {
  },
  created() {
    axios.get("/home/newest")
        .then(res => {
          res.data.forEach(item => this.books.push(item));
        });
    console.log(this.books);
  }
}
</script>

<style scoped>

</style>