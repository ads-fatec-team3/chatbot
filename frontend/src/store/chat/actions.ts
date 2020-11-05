import { ActionTree } from "vuex";
import { ChatState, RootState } from "../../@types/vuex";
import IMember from "../../@types/member";
import { IChat } from "../../@types/chat";
import ChatService from "../../services/chat"
import { ChatMutation } from "./mutations";

const actions: ActionTree<ChatState, RootState> = {
    async getChats({ commit }): Promise<Boolean>{
        const chats = await ChatService.getAllChats();
        commit(ChatMutation.SET_CHATS,chats);
        return true;
    },
    async setChat({ dispatch },{ title,members }):Promise<IChat>{
        const chatRoom = await ChatService.createChat({title, members});
        await dispatch('getChats');
        return await ChatService.addMemberToChat(chatRoom);
    }
}

export default actions;