package de.cybine.management.data.action.metadata;

import de.cybine.management.data.action.context.*;
import de.cybine.management.util.converter.*;

public class ActionMetadataMapper implements EntityMapper<ActionMetadataEntity, ActionMetadata>
{
    @Override
    public Class<ActionMetadataEntity> getEntityType( )
    {
        return ActionMetadataEntity.class;
    }

    @Override
    public Class<ActionMetadata> getDataType( )
    {
        return ActionMetadata.class;
    }

    @Override
    public ActionMetadataEntity toEntity(ActionMetadata data, ConversionHelper helper)
    {
        return ActionMetadataEntity.builder()
                                   .id(data.findId().map(ActionMetadataId::getValue).orElse(null))
                                   .namespace(data.getNamespace())
                                   .category(data.getCategory())
                                   .name(data.getName())
                                   .type(data.getType() != null ? data.getType().getName() : null)
                                   .contexts(helper.toSet(ActionContext.class, ActionContextEntity.class)
                                                   .map(data::getContexts))
                                   .build();
    }

    @Override
    public ActionMetadata toData(ActionMetadataEntity entity, ConversionHelper helper)
    {
        return ActionMetadata.builder()
                             .id(ActionMetadataId.of(entity.getId()))
                             .namespace(entity.getNamespace())
                             .category(entity.getCategory())
                             .name(entity.getName())
                             .type(entity.getType() != null ? ActionType.findByName(entity.getType()).orElseThrow() :
                                     null)
                             .contexts(helper.toSet(ActionContextEntity.class, ActionContext.class)
                                             .map(entity::getContexts))
                             .build();
    }
}
