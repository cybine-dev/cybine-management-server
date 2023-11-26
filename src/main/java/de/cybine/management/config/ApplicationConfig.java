package de.cybine.management.config;

import io.quarkus.runtime.annotations.*;
import io.smallrye.config.*;
import jakarta.validation.constraints.*;

@StaticInitSafe
@SuppressWarnings("unused")
@ConfigMapping(prefix = "application")
public interface ApplicationConfig
{
    @NotNull @NotBlank
    @WithName("base-url")
    String baseUrl( );

    @NotNull @NotBlank
    @WithName("app-id")
    String appId( );

    @WithName("converter")
    Converter converter( );

    interface Converter
    {
        @WithDefault("false")
        @WithName("allow-multi-level-relations")
        boolean allowMultiLevelRelations( );
    }
}
