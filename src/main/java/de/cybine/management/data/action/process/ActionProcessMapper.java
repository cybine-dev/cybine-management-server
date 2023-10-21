package de.cybine.management.data.action.process;

import de.cybine.management.data.action.context.*;
import de.cybine.management.data.util.primitive.*;
import de.cybine.management.service.action.*;
import de.cybine.management.util.converter.*;
import lombok.*;

public class ActionProcessMapper implements EntityMapper<ActionProcessEntity, ActionProcess>
{
    @Override
    public Class<ActionProcessEntity> getEntityType( )
    {
        return ActionProcessEntity.class;
    }

    @Override
    public Class<ActionProcess> getDataType( )
    {
        return ActionProcess.class;
    }

    @Override
    public ActionProcessEntity toEntity(ActionProcess data, ConversionHelper helper)
    {
        return ActionProcessEntity.builder()
                                  .id(data.findId().map(Id::getValue).orElse(null))
                                  .eventId(data.getEventId())
                                  .contextId(data.getContextId().getValue())
                                  .context(helper.toItem(ActionContext.class, ActionContextEntity.class)
                                                 .map(data::getContext))
                                  .status(data.getStatus())
                                  .priority(data.getPriority().orElse(100))
                                  .description(data.getDescription().orElse(null))
                                  .creatorId(data.getCreatorId().orElse(null))
                                  .createdAt(data.getCreatedAt())
                                  .dueAt(data.getDueAt().orElse(null))
                                  .data(data.getData().map(ActionData::of).orElse(null))
                                  .build();
    }

    @Override
    @SneakyThrows
    public ActionProcess toData(ActionProcessEntity entity, ConversionHelper helper)
    {
        return ActionProcess.builder()
                            .id(ActionProcessId.of(entity.getId()))
                            .eventId(entity.getEventId())
                            .contextId(ActionContextId.of(entity.getContextId()))
                            .context(helper.toItem(ActionContextEntity.class, ActionContext.class)
                                           .map(entity::getContext))
                            .status(entity.getStatus())
                            .priority(entity.getPriority())
                            .description(entity.getDescription().orElse(null))
                            .createdAt(entity.getCreatedAt())
                            .dueAt(entity.getDueAt().orElse(null))
                            .data(entity.getData().map(ActionData::data).orElse(null))
                            .build();
    }
}
