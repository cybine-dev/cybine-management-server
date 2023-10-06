package de.cybine.management.data.mail.mailbox;

import de.cybine.management.data.mail.address.*;
import de.cybine.management.data.mail.user.*;
import de.cybine.management.util.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = MailboxEntity_.TABLE)
@Entity(name = MailboxEntity_.ENTITY)
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailboxEntity extends PanacheEntityBase implements Serializable, WithId<Long>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MailboxEntity_.ID_COLUMN, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = MailboxEntity_.NAME_COLUMN, nullable = false)
    private String name;

    @Nullable
    @Column(name = MailboxEntity_.DESCRIPTION_COLUMN)
    private String description;

    @NotNull
    @Column(name = MailboxEntity_.ENABLED_COLUMN, nullable = false)
    private Boolean isEnabled;

    @NotNull
    @Column(name = MailboxEntity_.QUOTA_COLUMN, nullable = false)
    private long quota;

    @NotNull
    @ManyToMany
    @JoinTable(name = MailboxSource_.TABLE,
               joinColumns = @JoinColumn(name = MailboxSource_.MAILBOX_ID_COLUMN),
               inverseJoinColumns = @JoinColumn(name = MailboxSource_.ADDRESS_ID_COLUMN))
    private Set<MailAddressEntity> sourceAddresses;

    @NotNull
    @ManyToMany
    @JoinTable(name = MailboxPermission_.TABLE,
               joinColumns = @JoinColumn(name = MailboxPermission_.MAILBOX_ID_COLUMN),
               inverseJoinColumns = @JoinColumn(name = MailboxPermission_.USER_ID_COLUMN))
    private Set<MailUserEntity> users;

    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;

        if(this.getClass() != other.getClass())
            return false;

        WithId<?> that = ((WithId<?>) other);
        if (this.findId().isEmpty() || that.findId().isEmpty())
            return false;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode( )
    {
        return this.findId().map(Object::hashCode).orElse(0);
    }
}
