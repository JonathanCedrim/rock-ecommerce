package br.com.amora.rockecommerce.model;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String name;

    @Getter
    private String email;

    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private OpenIdAuthentication openIdAuthentication;

    @Getter
    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Shelf shelf = new Shelf();

    @Deprecated
    User() {
        this.name = "Anonym";
        this.email = email;
        this.shelf.setUser(this);
    }

    public void alterName(String name) {
        this.name = name;
    }

    public void authenticate(OpenIDAuthentication openIDAuthentication) {
        this.openIdAuthentication = openIdAuthentication;
    }
}
