<template>
  <div>
    <div class="d-flex flex-row ma-4">
      <v-btn
        block
        color="info"
        class="mx-2"
        @click="dialogChange"
      >
        Adicionar
      </v-btn>
    </div>
    <v-virtual-scroll
      :items="agenda"
      item-height="60"
      class="scroll-box"
    >
      <template v-slot="{ item, index }">

        <v-list-item :key="index">

          <v-list-item-content>
            <v-list-item-title class="d-flex flex-column">
              <strong class="mb-1">{{ item.title|upperCase }}</strong>
              <strong>{{ item.dateBegin|formatDate }}</strong>
            </v-list-item-title>
          </v-list-item-content>

          <v-list-item-action>
            <v-btn fab small depressed :color="item.color" />
          </v-list-item-action>

        </v-list-item>
        <v-divider></v-divider>

      </template>
    </v-virtual-scroll>
    <v-row justify="center">
      <v-dialog
        v-model="activeDialogAgenda"
        persistent
        max-width="370"
      >
        <v-card>
          <v-card-title class="headline">
            Adicionar Atividade
          </v-card-title>
          <v-card-text>
            <v-text-field label="Título" v-model="title"></v-text-field>
            <v-textarea label="Descrição" rows="2" v-model="description"></v-textarea>
            <v-row class="mx-1">
              <v-text-field label="Data Início" type="date" class="mr-4" v-model="dateBegin"></v-text-field>
              <v-text-field label="Hora Início" type="time" v-model="hourBegin"></v-text-field>
            </v-row>
            <v-row class="mx-1">
              <v-text-field label="Data Final" type="date" class="mr-4" v-model="dateEnd"></v-text-field>
              <v-text-field label="Hora Final" type="time" v-model="hourEnd"></v-text-field>
            </v-row>
            <v-select v-model="selectedMembers" @change="sendMembers(selectedMembers)" multiple :items="members" label="Participantes" ></v-select>

            <v-radio-group label="Prioridade" v-model="color">
              <v-row align="center" justify="center">
                <v-radio label="Baixa" class="mr-2" color="green" value="green"></v-radio>
                <v-radio label="Média" class="mr-2" color="orange" value="orange"></v-radio>
                <v-radio label="Alta" class="mb-2" color="red" value="red"></v-radio>
              </v-row>
            </v-radio-group>
          </v-card-text>
            <v-card-actions>
              <v-btn
                color="red darken-1"
                text
                @click="dialogChange"
              >
                Cancelar
              </v-btn>
              <v-spacer></v-spacer>
              <v-btn
                color="green darken-1"
                text
                @click="createAgenda"
              >
                Adicionar
              </v-btn>
            </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: 'AgendaTab',
  props: {
    agenda: Array,
    members: Array,
    handleActiveDialog: Function,
    activeDialogAgenda: Boolean
  },
  data () {
    return {
      title: null,
      description: null,
      dateBegin: null,
      hourBegin: null,
      dateEnd: null,
      hourEnd: null,
      selectedMembers: [],
      color: 'orange'
    }
  },
  methods: {
    dialogChange: function () {
      this.$emit('handleActiveDialog')
    },
    sendMembers: function (members) {
      this.$emit('handleLoadMembers')
    },
    createAgenda: function () {
      const dateBegin = new Date(`${this.dateBegin} ${this.hourBegin}`)
      const dateEnd = new Date(`${this.dateEnd} ${this.hourEnd}`)
      const dataAgenda = {
        title: this.title,
        description: this.description,
        date_begin: dateBegin,
        date_end: dateEnd,
        color: this.color
      }
      this.$emit('handleCreateAgenda', dataAgenda)
    }
  },
  filters: {
    formatDate: function (value) {
      if (value) {
        return moment(String(value)).format('DD/MM/YYYY hh:mm')
      }
    },
    upperCase: function (value) {
      if (value) {
        return value.toUpperCase()
      }
    }
  }
}
</script>
