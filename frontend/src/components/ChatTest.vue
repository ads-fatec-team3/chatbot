<template>
  <v-app>
    <v-card width="500">
      <v-tabs
        v-model="tab"
        background-color="primary"
        centered
        icons-and-text
      >
        <v-tab href="#tab-1">
          Chat
          <v-icon>mdi-chat</v-icon>
        </v-tab>

        <v-tab href="#tab-2">
          Conversas
          <v-icon>mdi-view-list</v-icon>
        </v-tab>

        <v-tab href="#tab-3">
          Agenda
          <v-icon>mdi-alarm</v-icon>
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item
          value="tab-1"
        >
          <!-- Card chat -->
          <v-card height="600" flat class="d-flex align-content-end flex-column">
            <v-card-text>
              <v-virtual-scroll
                height="480"
                item-height="64"
                :items="messages"
              >
                <template v-slot="{ message }">
                  <v-list-item :key="message">
                    <v-list-item-content>
                      {{ message }}
                    </v-list-item-content>
                  </v-list-item>
                </template>
              </v-virtual-scroll>
            </v-card-text>
            <v-card-actions>
              <v-textarea
                class="message-box"
                outlined
                no-resize
                rows="2"
                row-height="10"
              ></v-textarea>
              <v-btn
                class="mb-8 ml-4"
                large
                fab
                color="primary"
              >
                <v-icon color="white">mdi-send</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-tab-item>

        <v-tab-item
          value="tab-2"
        >
          <!-- Card conversas -->
          <v-card height="600" flat>
            <v-card-text>{{ text }}</v-card-text>
          </v-card>
        </v-tab-item>

        <v-tab-item
          value="tab-3"
        >
          <!-- Card agenda -->
          <v-card height="600" flat>
            <v-card-text>{{ text }}</v-card-text>
          </v-card>
        </v-tab-item>

      </v-tabs-items>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: 'Chat',
  data () {
    return {
      tab: null,
      text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      message: 1,
      messages: [
        { content: 'Olá!' },
        { content: 'Salve' },
        { content: 'Chique' }
      ]
    }
  }
}
</script>
