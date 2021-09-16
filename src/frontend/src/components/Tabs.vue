<template>
  <v-card>
    <v-toolbar flat class="text-center">

      <v-toolbar-title>BOOKISH</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <template v-slot:extension>
        <v-tabs
            v-model="tabs"
            fixed-tabs
        >
          <v-tabs-slider></v-tabs-slider>
          <v-tab
              href="#mobile-tabs-5-1"
              class="primary--text"
          >
            <v-icon>mdi-phone</v-icon>
          </v-tab>

          <v-tab
              href="#mobile-tabs-5-2"
              class="primary--text"
          >
            <v-icon>mdi-heart</v-icon>
          </v-tab>

          <v-tab
              href="#mobile-tabs-5-3"
              class="primary--text"
          >
            <v-icon>mdi-account-box</v-icon>
          </v-tab>
        </v-tabs>
      </template>
    </v-toolbar>

    <v-tabs-items v-model="tabs">
      <v-tab-item
          v-for="i in 3"
          :key="i"
          :value="'mobile-tabs-5-' + i"
      >
      </v-tab-item>
    </v-tabs-items>
  </v-card>
</template>

<script>
export default {
  name: "Tabs.vue",
  data: () => ({
    tabs: null
  }),
}
</script>

<style scoped>

</style>