import { ActionTree } from "vuex";
import { ChatState, RootState } from "../../@types/vuex";
import ChatService from "../../services/chat"
import { ChatMutation } from "./mutations";

const actions: ActionTree<ChatState, RootState> = {
    async getChats({ commit }): Promise<Boolean>{
        const chats = await ChatService.getAllChats();
        commit(ChatMutation.SET_CHATS,chats);
        return true;
    }
}

export default actions;