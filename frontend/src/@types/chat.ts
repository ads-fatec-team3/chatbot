import { IMember } from "./member"
import { IMember } from "./member"
import { IMessage } from "./message"



export interface IChat {
    id?: string;
    message?: IMessage;
    members: IMember[];
    title: string
}