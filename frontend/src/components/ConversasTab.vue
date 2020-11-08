<template>
  <div>
    <div class="d-flex flex-row ma-2">
      <v-textarea
        v-model="searchMember"
        outlined
        rows="1"
        no-resize
        class="ml-2"
        placeholder="Quem você está procurando?"
      ></v-textarea>
      <v-btn
        fab
        color="primary"
        class="ml-2"
      >
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    </div>
    <v-virtual-scroll
      :items="conversas"
      item-height="64"
      class="scroll-box"
    >
      <template v-slot="{ item, index }">

        <v-list-item :key="index" @click="changeTab(item.name)">
          <v-list-item-action>
            <v-btn
              fab
              small
              depressed
              color="blue darken-4"
            >
              <strong class="white--text">{{ index }}</strong>
            </v-btn>
          </v-list-item-action>

          <v-list-item-content>
            <v-list-item-title>
              <strong>{{ item.name }}</strong>
            </v-list-item-title>
          </v-list-item-content>

        </v-list-item>
        <v-divider></v-divider>

      </template>
    </v-virtual-scroll>
  </div>
</template>

<script>
export default {
  name: 'ConversasTab',
  props: {
    conversas: Array,
    handleChangeTab: Function
  },
  data () {
    return {
      searchMember: null
    }
  },
  methods: {
    changeTab: function (user) {
      console.log('Abacate')
      this.$emit('handleChangeTab', user)
    }
  }
}
</script>
