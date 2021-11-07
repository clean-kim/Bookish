<template>
  <div>
    <Tabs></Tabs>
    <div style="margin: 10px;">
      <Ad></Ad>
    </div>
    <Recommend></Recommend>
    <Newest></Newest>
  </div>
</template>

<script>
import Ad from './Ad'
import Tabs from './Tabs'
import Recommend from './Recommend'
import Newest from './Newest'

export default {
  name: "Home",
  components: {
    Ad
    , Tabs
    , Recommend
    , Newest
  }
}
</script>

<style scoped>

</style>