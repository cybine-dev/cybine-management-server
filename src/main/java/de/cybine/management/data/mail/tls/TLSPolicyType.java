package de.cybine.management.data.mail.tls;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum TLSPolicyType
{
    NONE("none"),
    MAY("may"),
    ENCRYPT("encrypt"),
    DANE("dane"),
    DANE_ONLY("dane-only"),
    FINGERPRINT("fingerprint"),
    VERIFY("verify"),
    SECURE("secure");

    private final String type;
}
