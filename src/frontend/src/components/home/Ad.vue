<template>
  <v-app>
    <v-carousel>
      <v-carousel-item
          v-for="ad in images"
          :key="ad"
      >
        <v-sheet
            height="100%"
            tile
            :style="{'background-image': 'url(' + ad + ')'}"
        >
          <v-row
              class="fill-height"
              align="center"
              justify="center"
          >
            <img :src="ad" alt="">
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    images: [
      require("../../assets/images/ad1.png"),
      require("../../assets/images/ad2.png"),
    ]
  }),
}
</script>

<style scoped>

</style>