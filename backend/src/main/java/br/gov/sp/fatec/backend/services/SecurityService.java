package br.gov.sp.fatec.backend.services;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;

public interface SecurityService extends UserDetailsService {
    
    public Message insertMessage(String text, Date timestamp, long conversation, long sender);

    public Member addToConversation(long id, long conversation);

}