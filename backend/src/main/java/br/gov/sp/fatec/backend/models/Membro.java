package br.gov.sp.fatec.backend.models;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@Table(name = "membro")
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_membro")
    private long id_membro;
    
    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "id_conversa")
    private Conversa id_conversa;
    
    @Column(name = "id_usuario")
    private long id_usuario;


    public long getId_membro() {
        return id_membro;
    }

    public void setId_membro(long id_membro) {
        this.id_membro = id_membro;
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
    
    

}