<template>
  <div>
    <!-- <div class="d-flex flex-row ma-2">
      <v-textarea
        v-model="searchAgenda"
        @input="searchChange"
        outlined
        rows="1"
        no-resize
        class="ml-2"
        placeholder="Filtro de tarefas"
      ></v-textarea>
      <v-btn
        fab
        color="primary"
        class="ml-2"
        @click="dialogChange"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </div> -->

    <template>
      <div class="scroll-box" style="height: 90vh;">

        <v-sheet height="64">
          <v-toolbar
            flat
          >
            <v-toolbar-title class="ml-2" v-if="$refs.calendar">
              {{ $refs.calendar.title }}
            </v-toolbar-title>
            <v-btn
              fab
              text
              small
              color="grey darken-2"
              @click="prev"
            >
              <v-icon small>
                mdi-chevron-left
              </v-icon>
            </v-btn>
            <v-btn
              fab
              text
              small
              color="grey darken-2"
              @click="next"
            >
              <v-icon small>
                mdi-chevron-right
              </v-icon>
            </v-btn>
            <v-btn
              class="info"
              color="grey darken-2"
              @click="setToday"
            >
              Hoje
            </v-btn>
            <v-btn
              class="ml-3"
              color="primary"
              @click="dialogChange()"
            >
              Nova
            </v-btn>
          </v-toolbar>
        </v-sheet>
        <v-sheet height="500">
          <v-calendar
            ref="calendar"
            v-model="focus"
            color="info "
            :events="events"
            :event-color="getEventColor"
            type="week"
            @click:event="showEvent"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            offset-x
          >
            <v-card
              color="grey lighten-4"
              min-width="350px"
              flat
            >
              <v-toolbar
                :color="selectedEvent.color"
                dark
              >
                <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                <v-spacer></v-spacer>
                <v-tooltip v-if="selectedEvent.status !== 'concluido'" bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      color="info"
                      dark
                      v-bind="attrs"
                      v-on="on"
                      @click="updateStatusAgenda(selectedEvent)"
                    >
                      <v-icon>mdi-check</v-icon>
                    </v-btn>
                  </template>
                  <span>Concluir Atividade</span>
                </v-tooltip>
              </v-toolbar>
              <v-card-text class="headline justify-start">
                <div class="text-subtitle-1" v-if="selectedEvent.owner"><b>Criada por:</b> {{ selectedEvent.owner.name }}</div>
                <div class="text-subtitle-1"><b>Inicio:</b> {{ selectedEvent.start|formatDate }}</div>
                <div class="text-subtitle-1"><b>Fim:</b> {{ selectedEvent.end|formatDate }}</div>
                <div class="text-subtitle-1"><b>Descrição:</b> {{ selectedEvent.description }}</div>
                <div class="text-subtitle-1"><b>Participantes:</b> {{ selectedEvent.members|arrayToString }}</div>
                <div class="text-subtitle-1"><b>Status:</b> {{ selectedEvent.status || 'Indisponível' }}</div>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  text
                  color="secondary"
                  @click="selectedOpen = false"
                >
                  Fechar
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-menu>
        </v-sheet>
      </div>
    </template>

    <v-row justify="center">
      <v-dialog
        v-model="activeDialogAgenda"
        persistent
        max-width="370"
      >
        <v-form v-model="valid" ref="form">
        <v-card>
          <v-card-title class="headline">
            Nova Atividade
          </v-card-title>
          <v-card-text>
            <v-text-field label="Título" v-model="title" :rules="[v => !!v || 'Título é obrigatório']" required></v-text-field>
            <v-textarea label="Descrição" rows="2" v-model="description" :rules="[v => !!v || 'Descrição é obrigatória']" required></v-textarea>
            <v-row class="mx-1">
              <v-text-field label="Data Início" type="date" class="mr-4" v-model="dateBegin" :rules="[v => !!v || 'Data de início é obrigatória']" required></v-text-field>
              <v-text-field label="Hora Início" type="time" v-model="hourBegin" :rules="[v => !!v || 'Hora de início é obrigatória']" required></v-text-field>
            </v-row>
            <v-row class="mx-1">
              <v-text-field label="Data Final" type="date" class="mr-4" v-model="dateEnd" :rules="[v => !!v || 'Data final é obrigatória']" required></v-text-field>
              <v-text-field label="Hora Final" type="time" v-model="hourEnd" :rules="[v => !!v || 'Hora final é obrigatória']" required></v-text-field>
            </v-row>
            <v-select v-model="selectedMembers" multiple :items="members" label="Participantes" :rules="[required]" required></v-select>

            <v-radio-group label="Prioridade" v-model="color" :rules="[v => !!v || 'Prioridade é obrigatória']" required>
              <v-row align="center" justify="center">
                <v-radio label="Baixa" class="mr-2" color="green" value="green"></v-radio>
                <v-radio label="Média" class="mr-1" color="yellow darken-3" value="yellow darken-3"></v-radio>
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
                Criar Atividade
              </v-btn>
            </v-card-actions>
        </v-card>
        </v-form>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
import moment from 'moment'
import serviceAgenda from '@/services/agenda.js'

export default {
  name: 'AgendaTab',
  props: {
    agendas: Array,
    members: Array,
    handleActiveDialog: Function,
    activeDialogAgenda: Boolean,
    handleSearchAgenda: Function,
    handleUpdateAgenda: Function
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
      color: 'orange',
      status: null,
      valid: false,
      dialogCalendario: false,
      focus: '',
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      events: [],
      searchAgenda: null
    }
  },
  mounted () {
    this.$refs.calendar.checkChange()
    this.loadEvents()
  },
  methods: {
    updateStatusAgenda: async function (data) {
      const newAgenda = {
        id: data.id,
        title: data.name,
        description: data.description,
        color: 'blue',
        dateBegin: data.start,
        dateEnd: data.end,
        status: 'concluido'
      }
      const resp = await serviceAgenda.updateAgenda(newAgenda)
      console.log(resp.status)
      this.$emit('handleUpdateAgenda')
      this.selectedOpen = false
      this.selectedEvent = {}
    },
    dialogChange: function () {
      this.$emit('handleActiveDialog')
      this.$refs.form.reset()
    },
    createAgenda: function () {
      const dateBegin = new Date(`${this.dateBegin} ${this.hourBegin}`)
      const dateEnd = new Date(`${this.dateEnd} ${this.hourEnd}`)
      const dataAgenda = {
        title: this.title,
        description: this.description,
        dateBegin: dateBegin,
        dateEnd: dateEnd,
        color: this.color,
        status: 'aberta'
      }
      this.$refs.form.validate()

      if (this.valid === true) {
        this.$emit('handleCreateAgenda', dataAgenda, this.selectedMembers)
        this.loadEvents()
        this.$refs.form.reset()
      }
    },
    isAllDay (start, end) {
      if (start.getMonth() !== end.getMonth() || start.getDate() !== end.getDate()) {
        return false
      }
      return true
    },
    loadEvents: function () {
      const events = []
      this.agendas.forEach((element, index, originalArray) => {
        const start = new Date(element.dateBegin)
        const end = new Date(element.dateEnd)
        events.push({
          id: element.id,
          name: element.title,
          start: start,
          end: end,
          color: element.color,
          timed: this.isAllDay(start, end),
          description: element.description,
          status: element.status
        })
      })
      this.events = events
    },
    required (value) {
      if (value instanceof Array && value.length === 0) {
        return 'Participantes são obrigatórios'
      }
      return !!value || 'Participantes são obrigatórios'
    },
    getEventColor (event) {
      return event.color
    },
    setToday () {
      this.focus = ''
    },
    prev () {
      this.$refs.calendar.prev()
    },
    next () {
      this.$refs.calendar.next()
    },
    showEvent ({ nativeEvent, event }) {
      const open = async () => {
        const resp = await serviceAgenda.getAgendaById(event.id)
        const start = new Date(resp.data.dateBegin)
        const end = new Date(resp.data.dateEnd)
        const selectedEvent = {
          id: resp.data.id,
          name: resp.data.title,
          start: start,
          end: end,
          color: resp.data.color,
          timed: this.isAllDay(start, end),
          description: resp.data.description,
          members: resp.data.members,
          owner: resp.data.owner,
          status: resp.data.status
        }
        this.selectedEvent = selectedEvent
        this.selectedElement = nativeEvent.target
        setTimeout(() => {
          this.selectedOpen = true
        }, 10)
      }

      if (this.selectedOpen) {
        this.selectedOpen = false
        setTimeout(open, 10)
      } else {
        open()
      }

      nativeEvent.stopPropagation()
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
    },
    arrayToString: function (value) {
      if (value) {
        return value.map(val => {
          return val.name
        }).join(', ') || '---'
      }
    }
  },
  watch: {
    agendas: function (val) {
      this.loadEvents()
    }
  }
}
</script>
