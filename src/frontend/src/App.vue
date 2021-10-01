<template>
  <div id="app">
    <Tabs></Tabs>
    <div style="margin: 10px;">
      <Ad></Ad>
    </div>
    <Recommend></Recommend>
    <Newest></Newest>
  </div>
</template>


<script>
import Ad from './components/Ad'
import Tabs from './components/Tabs'
import Recommend from './components/Recommend'
import Newest from './components/Newest'

export default {
  name: 'app',

  components: {
    Ad
    , Tabs
    , Recommend
    , Newest
  },

};
</script>
