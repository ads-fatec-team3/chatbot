package br.gov.sp.fatec.backend.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
@Table(name = "conversa")
public class Conversa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_conversa")
    private long id_conversa;

    @Column(name = "titulo")
    private String titulo;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Membro> membros;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Mensagem> mensagens;

    public long getId_conversa() {
        return id_conversa;
    }

    public void setId_conversa(long id_conversa) {
        this.id_conversa = id_conversa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;

    }

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
    
}