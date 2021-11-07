<template>
  <div>
    <div class="detail-top">
      {{bookDetail.description}}
    </div>
    <div class="detail-middle">

    </div>
    <div class="detail-bottom"></div>
  </div>
</template>

<script>
export default {
  name: "BookDetail",
  data: () => ({
     bookDetail : this.$store.state.bookDetail
  })
}
</script>

<style scoped>

</style>