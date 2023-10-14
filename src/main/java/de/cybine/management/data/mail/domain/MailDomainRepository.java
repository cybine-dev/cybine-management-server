package de.cybine.management.data.mail.domain;

import io.quarkus.arc.*;
import io.quarkus.hibernate.orm.panache.*;
import jakarta.enterprise.context.*;
import lombok.*;

@Unremovable
@ApplicationScoped
@RequiredArgsConstructor
public class MailDomainRepository implements PanacheRepository<MailDomainEntity>
{ }