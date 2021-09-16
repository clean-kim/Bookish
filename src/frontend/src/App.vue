<template>
  <div id="App">
    <Tabs></Tabs>
    <div style="margin: 10px;">
      <Ad></Ad>
    </div>
    <!--
<button type="button" class="v-btn v-btn--icon v-btn--round theme--light v-size--default"><span class="v-btn__content"><i aria-hidden="true" class="v-icon notranslate mdi mdi-bookmark theme--light"></i></span></button>
-->
    <div style="margin: 10px;">
      <Recommend></Recommend>
    </div>
  </div>
</template>


<script>
import Ad from './components/Ad'
import Tabs from './components/Tabs'
import Recommend from './components/Recommend'

export default {
  name: 'App',

  components: {
    Ad
    , Tabs
    , Recommend
  },

};
</script>
