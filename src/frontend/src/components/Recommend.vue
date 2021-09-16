<template>
  <div>
    <h3>사서 선생님이 추천한대요~🤓</h3>
    <v-sheet
        class="mx-auto"
        elevation="8"
        max-width="100%"
        max-height="100%"
    >
      <v-slide-group
          class="pa-4"
          active-class="success"
          show-arrows
      >
        <v-slide-item
            v-for="book in this.$store.state.books" :key="book.isbn"
            style="display: inline-block; box-shadow: none; width: 200px; height: fit-content;"
            v-slot="{ active, toggle }"
        >
          <v-card
              class="ma-4"
              @click="toggle"
          >
            <v-row
                class="fill-height"
                align="center"
                justify="center"
                style="margin: 10px 5px;"
            >
              <v-img
                  :src="book.image"
                  style="height: fit-content;"
              ></v-img>
              <v-card-actions style="height: fit-content;">
                <div style="word-break: break-all;">
                  {{book.title}}
                </div>
              </v-card-actions>
            </v-row>
          </v-card>

        </v-slide-item>
      </v-slide-group>
    </v-sheet>

  </div>
</template>

<script>
export default {
  data: () => ({
    loading: false,
    selection: 1,
    books: [],
  }),
  methods: {
  },
}
</script>

<style scoped>

</style>