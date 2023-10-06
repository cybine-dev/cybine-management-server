package de.cybine.management.util.api;

import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
public class ApiFieldResolver
{
    public static final String DEFAULT_CONTEXT = "default";

    private final Map<String, ApiFieldResolverContext> contexts = new HashMap<>();

    public ApiFieldResolverContext getDefaultContext()
    {
        return this.getContext(ApiFieldResolver.DEFAULT_CONTEXT);
    }

    public ApiFieldResolverContext getContext(String name)
    {
        return this.contexts.computeIfAbsent(name, ApiFieldResolverContext::new);
    }

    public Optional<ApiFieldResolverContext> findContext(String context)
    {
        return Optional.ofNullable(this.contexts.get(context));
    }
}
