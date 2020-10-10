import axios from "axios"
import { IChat } from "../@types/chat";

const SERVER_URL = "http://localhost:8080"

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
    },

    async getAllChats():Promise<IChat[]>{
        const response = await axios({
            method:"GET",
            url: `${SERVER_URL}/api/conversations`
        })
        return response.data;
    }
}

export default ChatService;
