package io.github.salmasouza.agenda.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name="tbl_contato")
public class Contato implements Serializable {

    /**
     *
     */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contato_nome", length = 150, nullable = false)
    private String nome;

    @Column(name = "contato_email",length = 150, nullable = false)
    private String email;

    @Column(name = "contato_favorito")
    private Boolean favorito;

    @Column(name = "contato_foto")
    @Lob
    private byte[] foto;

    public Contato() {
    }

    public Contato(Long id, String nome, String email, Boolean favorito, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.favorito = favorito;
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", favorito=" + favorito +
                ", foto=" + Arrays.toString(foto) +
                '}';
    }
}
