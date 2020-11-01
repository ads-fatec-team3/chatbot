import Vue from "vue"

import { MutationTree } from "vuex";
import { ChatState } from "../../@types/vuex";
import { IChat } from "../../@types/chat";

export enum ChatMutation{
    SET_CHATS = "SET_CHATS"
}

const mutations: MutationTree<ChatState> = {
    SET_CHATS(state: ChatState, chatList: IChat[] ){
        Vue.set(state,"chats", chatList);
    }
}

export default mutations;