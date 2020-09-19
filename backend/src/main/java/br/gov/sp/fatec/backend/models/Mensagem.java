package br.gov.sp.fatec.backend.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@Table(name = "mensagem")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_mensagem")
    private long id_mensagem;

    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "id_conversa")
    private Conversa id_conversa;
    
    @Column(name = "id_usuario")
    private long id_usuario;
    
    @Column(name = "data_horario")
    private Date date_horario;

	public long getId_mensagem() {
		return id_mensagem;
	}

	public void setId_mensagem(long id_mensagem) {
		this.id_mensagem = id_mensagem;
	}

	public Conversa getId_conversa() {
		return id_conversa;
	}

	public void setId_conversa(Conversa id_conversa) {
		this.id_conversa = id_conversa;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Date getDate_horario() {
		return date_horario;
	}

	public void setDate_horario(Date date_horario) {
		this.date_horario = date_horario;
	}
    
    
    

}