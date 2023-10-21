package de.cybine.management.service.action;

import de.cybine.management.exception.action.*;
import jakarta.enterprise.context.*;
import lombok.*;
import lombok.extern.slf4j.*;

import java.util.*;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ActionProcessorRegistry
{
    private final Map<ActionProcessorMetadata, ActionProcessor<?>> processors = new HashMap<>();

    public void registerProcessors(List<ActionProcessor<?>> processors)
    {
        for (ActionProcessor<?> processor : processors)
            this.registerProcessor(processor);
    }

    public void registerProcessor(ActionProcessor<?> processor)
    {
        ActionProcessorMetadata metadata = processor.getMetadata();
        if (this.processors.containsKey(metadata))
            throw new DuplicateProcessorDefinitionException(metadata.asString());

        log.debug("Registering action-processor: {}", metadata.asString());

        this.processors.put(processor.getMetadata(), processor);
    }

    public void removeProcessor(ActionProcessorMetadata metadata)
    {
        this.processors.remove(metadata);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<ActionProcessor<T>> findProcessor(ActionProcessorMetadata metadata)
    {
        log.debug("Searching action-processor: {}", metadata.asString());

        return Optional.ofNullable((ActionProcessor<T>) this.processors.get(metadata));
    }
}
