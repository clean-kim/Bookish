<template>
  <div class="wrap p20">
    <div class="nav-tab d-flex">
      <a class="tab-link" data-tab="main">Main</a>
      <a class="tab-link" data-tab="recommend">Recommend</a>
      <a class="tab-link" data-tab="myPage">My Page</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "Nav.vue"
}
</script>

<style scoped>
.wrap {
  width: 100%;
  border-top: 1.5px solid #6c757d8c;
}

.nav-tab .tab-link {
  width: 33%;
}

.tab-link {
  float: left;
  padding: 10px 15px;
  display: inline-block;
  cursor: pointer;
  text-align: center;
  line-height: 1;
}

</style>