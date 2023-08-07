package de.cybine.management.data.mail.domain;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum MailDomainAction
{
    NONE("none"), MANAGE("manage"), SEND_ONLY("send_only");

    private final String action;
}
