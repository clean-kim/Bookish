<template>
  <div class="container">
    <div class="row align-items-start">
      <div class="col">
        <img :src="bookDetail.imgUrl" alt="표지" style="width: fit-content;">
      </div>
      <div class="col">
        <div>{{booDetail.title}}</div>
        <div>{{booDetail.author}}</div>
        <div>{{booDetail.publisher}}</div>
        <div>{{booDetail.pubDate}}</div>
        <div>{{booDetail.isbn}}</div>
      </div>
    </div>
    <div class="detail-middle">
      {{bookDetail.description}}
    </div>
    <div class="detail-bottom"></div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BookDetail",
  props: ['isbn'],
  data() {
    return {
      bookDetail: null
    }
  },
  created() {
    axios.get("/detail", {params: {isbn: this.$route.params.isbn}})
        .then(res => this.bookDetail = res.data)
  }

}
</script>

<style scoped>

</style>