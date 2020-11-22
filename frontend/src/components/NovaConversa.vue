<template>
      <v-card>
        <v-card-title class="headline"> Novo grupo </v-card-title>
        <v-card-text>
          <v-text-field label="Título" v-model="title"></v-text-field>
          <v-select
            v-model="selectedMembers"
            @change="sendMembers(selectedMembers)"
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
            Criar grupo
          </v-btn>
        </v-card-actions>
      </v-card>
</template>
<script>
import serviceConversation from '@/services/conversation.js'
export default {
  name: 'NovaConversa',
  props: {
    handleActiveDialog: Function,
    activeDialogAgenda: Boolean,
    members: Array
  },
  data () {
    return {
      title: null,
      selectedMembers: []
    }
  },
  methods: {
    dialogChange: function () {
      this.$emit('handleActiveDialog')
    },
    sendMembers: function (members) {
      this.$emit('handleLoadMembers')
    },
    createConversa: async function () {
      const resp = await serviceConversation.newConversation(this.title)
      for (const memberId of this.selectedMembers) {
        await serviceConversation.insertConversationMember(memberId, resp.data.id)
      }
      this.dialogChange()
    }

  }
}
</script>
