<template>
  <div>
  <v-row justify="center">
    <v-dialog v-model="activeDialogConversas" persistent max-width="370">
      <v-card>
        <v-card-title class="headline"> Criar nova conversa </v-card-title>
        <v-card-text>
          <v-text-field label="Título" v-model="title"></v-text-field>
          <v-select
            v-model="selectedMembers"
            multiple
            :items="members"
            label="Participantes"
          ></v-select>
        </v-card-text>
        <v-card-actions>
          <v-btn color="red darken-1" text @click="dialogChange">
            Cancelar
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="createConversa">
            Adicionar
          </v-btn>
        </v-card-actions>
      </v-card>
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
export default {
  name: 'ConversasTab',
  props: {
    members: Array,
    conversas: Array,
    handleChangeTab: Function,
    handleActiveDialog: Function,
    activeDialogConversas: Boolean
  },
  data () {
    return {
      searchMember: null,
      title: null,
      selectedMembers: []
    }
  },
  methods: {
    changeTab: function (itemId) {
      this.$emit('handleChangeTab', itemId)
    },
    dialogChange: function () {
      this.$emit('handleActiveDialog')
    },
    createConversa: function () {
      const dataConversa = {
        title: this.title,
        selectedMembers: this.selectedMembers
      }
      this.$emit('handleCreateConversa', dataConversa)
    }
  }
}
</script>
