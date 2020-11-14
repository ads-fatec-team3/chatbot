package br.gov.sp.fatec.backend.websocket;

import java.util.Date;

import br.gov.sp.fatec.backend.models.MessageType;

public class ChatMessage {
  private String text;
  private long chatId;
  private long sender;
  private long recipient;
  private Date timestamp;
  private MessageType type;

  public ChatMessage() {}

  public ChatMessage(long sender, MessageType type) {
    this.sender = sender;
    this.type = type;
    this.timestamp = new Date(System.currentTimeMillis());
  }

  public String getText() {
    return text;
  }

  public long getChatId() {
    return chatId;
  }

  public long getSender() {
    return sender;
  }

  public long getRecipient() {
    return recipient;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public MessageType getType() {
    return type;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

  public void setSender(long sender) {
    this.sender = sender;
  }

  public void setRecipient(long recipient) {
    this.recipient = recipient;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public void setMessageType(MessageType type) {
    this.type = type;
  }
}