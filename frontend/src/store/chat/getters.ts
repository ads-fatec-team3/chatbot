import { GetterTree} from "vuex";
import { ChatState, RootState } from "../../@types/vuex";

const getters: GetterTree<ChatState, RootState> = {
    chatList: ({ chats }: ChatState ) => chats
};

export default getters;