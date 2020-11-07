<template>
  <div>
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
              <strong>{{ item.begins_at|formatDate }}</strong>
            </v-list-item-title>
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
  name: 'AgendaTab',
  props: {
    agenda: Array
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
