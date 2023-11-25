package de.cybine.management.util.cloudevent;

import de.cybine.management.config.*;
import de.cybine.management.data.action.context.*;
import de.cybine.management.data.action.metadata.*;
import de.cybine.management.data.action.process.*;
import de.cybine.management.util.converter.*;
import jakarta.ws.rs.core.*;
import lombok.*;

@RequiredArgsConstructor
public class CloudEventConverter implements Converter<ActionProcess, CloudEvent>
{
    private final ApplicationConfig config;

    @Override
    public Class<ActionProcess> getInputType( )
    {
        return ActionProcess.class;
    }

    @Override
    public Class<CloudEvent> getOutputType( )
    {
        return CloudEvent.class;
    }

    @Override
    public CloudEvent convert(ActionProcess input, ConversionHelper helper)
    {
        ActionContext context = input.getContext().orElseThrow();
        ActionMetadata metadata = context.getMetadata().orElseThrow();

        CloudEvent.Generator builder = CloudEvent.builder()
                                                 .id(input.getEventId())
                                                 .type(String.format("%s:%s:%s:%s", metadata.getNamespace(),
                                                         metadata.getCategory(), metadata.getName(), input.getStatus()))
                                                 .subject(context.getItemId().orElse(null))
                                                 .source(this.config.appId())
                                                 .time(input.getCreatedAt())
                                                 .correlationId(context.getCorrelationId())
                                                 .priority(input.getPriority().orElse(null));

        input.getData().ifPresent(data -> builder.contentType(MediaType.APPLICATION_JSON).data(data));

        return builder.build();
    }
}
