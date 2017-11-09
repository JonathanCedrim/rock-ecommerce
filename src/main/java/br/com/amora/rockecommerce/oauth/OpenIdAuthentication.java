package br.com.amora.rockecommerce.oauth;

import br.com.amora.rockecommerce.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OpenIdAuthentication implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Embedded
    @Getter
    private AuthorizationId id;

    @Getter
    @Column(name = "authn_provider")
    private String provider;

    @Getter
    @Setter
    private Date validate;

    @Getter
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private User user;

    @Deprecated
    OpenIdAuthentication() {

    }


    public OpenIdAuthentication(AuthorizationId id, String provider, Date validate, User user) {
        this.id = id;
        this.provider = provider;
        this.validate = validate;
        this.user = user;

        user.authenticate(this);
    }


    public boolean expired() {

        OffsetDateTime TokenValidateDate = OffsetDateTime.ofInstant(
                validate.toInstant(), ZoneId.systemDefault());

        OffsetDateTime now = OffsetDateTime.now(ZoneId.systemDefault());

        return now.isAfter(TokenValidateDate);
    }
}