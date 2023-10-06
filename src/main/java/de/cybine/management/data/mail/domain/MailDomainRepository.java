package de.cybine.management.data.mail.domain;

import io.quarkus.hibernate.orm.panache.*;
import jakarta.enterprise.context.*;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class MailDomainRepository implements PanacheRepository<MailDomainEntity>
{ }