<template>
  <div>
  <v-row justify="center">
    <v-dialog v-model="activeDialogAgenda" persistent max-width="370">
      <NovaConversa
        :activeDialogAgenda="activeDialogAgenda"
        :members="members"
        @handleActiveDialog="activeDialogAgenda = !activeDialogAgenda"
        @handleCreateAgenda="createAgenda"/>
    </v-dialog>
  </v-row>
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
      <v-btn
        fab
        color="primary"
        class="ml-2"
        @click="dialogChange"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </div>
    <v-virtual-scroll
      :items="conversas"
      item-height="64"
      class="scroll-box"
    >
      <template v-slot="{ item, index }">

        <v-list-item :key="index" @click="changeTab(item.id)">
          <v-list-item-action>
            <v-btn
              fab
              small
              depressed
              color="blue darken-4"
            >
              <strong class="white--text">{{ item.title.charAt(0) }}</strong>
            </v-btn>
          </v-list-item-action>

          <v-list-item-content>
            <v-list-item-title>
              <strong>{{ item.title }}</strong>
            </v-list-item-title>
          </v-list-item-content>

        </v-list-item>
        <v-divider></v-divider>

      </template>
    </v-virtual-scroll>
  </div>
</template>

<script>
import NovaConversa from '@/components/NovaConversa'
export default {
  components: {
    NovaConversa
  },
  name: 'ConversasTab',
  props: {
    members: Array,
    conversas: Array,
    handleChangeTab: Function,
    handleActiveDialog: Function,
    activeDialogAgenda: Boolean
  },
  data () {
    return {
      searchMember: null,
      activeDialogConversa: false
    }
  },
  methods: {
    changeTab: function (user) {
      console.log('Abacate')
      this.$emit('handleChangeTab', user)
    },
    dialogChange: function () {
      this.$emit('handleActiveDialog')
    }
  }
}
</script>
