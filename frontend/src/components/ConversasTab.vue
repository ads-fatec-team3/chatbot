<template>
  <div>
  <v-form v-model="valid" ref="form">
    <v-row justify="center">
      <v-dialog v-model="activeDialogConversas" persistent max-width="370">
        <v-card>
          <v-card-title class="headline"> Novo grupo </v-card-title>
          <v-card-text>
            <v-text-field class="text-icon" prepend-icon="mdi-asterisk" label="Título" v-model="title" :rules="[v => !!v || 'Título é obrigatório']" required></v-text-field>
            <v-select
              class="text-icon" prepend-icon="mdi-asterisk"
              v-model="selectedMembers"
              multiple
              :items="members"
              label="Participantes"
              :rules="[required]"
              required
            ></v-select>
          </v-card-text>
          <v-card-actions>
            <v-btn color="red darken-1" text @click="dialogChange">
              Cancelar
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="createConversa">
              Criar grupo
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </v-form>
    <div class="d-flex flex-row ma-2">
      <v-textarea
        v-model="searchConversa"
        @input="searchChange"
        outlined
        rows="1"
        no-resize
        class="ml-2"
        placeholder="Quem você está procurando?"
      ></v-textarea>
      <v-btn
        fab
        @click="searchEvent"
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
              <div class="d-flex flex-row justify-space-between align-end mb-1">
                <div class="text-h5">{{ item.title }}</div>
                <i v-if="item.lastMessage !== null">{{ item.lastMessage.timestamp|formatDate }}</i>
              </div>
            </v-list-item-title>
            <div v-if="item.lastMessage !== null" class="d-flex flex-row justify-start">
              <i>Mensagem: {{ item.lastMessage.text }}</i>
            </div>
          </v-list-item-content>

        </v-list-item>
        <v-divider></v-divider>

      </template>
    </v-virtual-scroll>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'ConversasTab',
  props: {
    members: Array,
    conversas: Array,
    handleChangeTab: Function,
    handleActiveDialog: Function,
    activeDialogConversas: Boolean,
    SearchConversa: Function
  },
  data () {
    return {
      searchConversa: null,
      title: null,
      selectedMembers: [],
      valid: false
    }
  },
  methods: {
    searchEvent: function () {
      this.$emit('SearchConversa', this.searchMember)
    },
    changeTab: function (itemId) {
      this.$emit('handleChangeTab', itemId)
    },
    dialogChange: function () {
      this.$emit('handleActiveDialog')
      this.$refs.form.reset()
    },
    searchChange: function () {
      this.$emit('handleSearchConversa', this.searchConversa)
    },
    createConversa: function () {
      const dataConversa = {
        title: this.title,
        selectedMembers: this.selectedMembers,
        members: this.selectedMembers
      }
      this.$refs.form.validate()
      if (this.valid === true) {
        this.$emit('handleCreateConversa', dataConversa)
        this.$refs.form.reset()
      }
    },
    required (value) {
      if (value instanceof Array && value.length === 0) {
        return 'Participantes são obrigatórios'
      }
      return !!value || 'Participantes são obrigatórios'
    }
  },
  filters: {
    formatDate: function (date) {
      return moment(String(date)).format('DD/MM/YYYY hh:mm')
    }
  }
}
</script>

<style>
.text-icon .v-icon {
    color: green;
    color: (this.valid === false ? 'red !important' : 'green !important') ;
    font-size: 12px;
}
</style>
