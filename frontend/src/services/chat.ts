import axios from "axios"
import { IChat } from "../@types/chat";

const SERVER_URL = "https://8080-adaec710-37f0-408a-9f51-5f0c7d7a1403.ws-us02.gitpod.io/"

const ChatService = {
    async createChat(chat: IChat): Promise<IChat>{
        const chatCreated = await axios({
            method:"POST",
            data:{
                title: chat.title
            },
            url:`${SERVER_URL}/api/conversations`
        })
        return {...chat, id:chatCreated.data.id}
    },

    async addMemberToChat(chatRoom: IChat): Promise<IChat>{
        await chatRoom.members.forEach(member => {
            axios({
                method: "POST",
                url: `${SERVER_URL}/api/conversations/${chatRoom.id}/members/add/${member.id}`
            })
        });
        return chatRoom;
    }
}