package de.cybine.management.data.action.context;

import de.cybine.management.data.action.metadata.*;
import de.cybine.management.data.action.process.*;
import de.cybine.management.util.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

import java.util.*;

@UtilityClass
@StaticMetamodel(ActionContextEntity.class)
public class ActionContextEntity_
{
    public static final String TABLE  = "action_context";
    public static final String ENTITY = "ActionContext";

    public static final String ID_COLUMN             = "id";
    public static final String METADATA_ID_COLUMN    = "metadata_id";
    public static final String CORRELATION_ID_COLUMN = "correlation_id";
    public static final String ITEM_ID_COLUMN        = "item_id";

    // @formatter:off
    public static final DatasourceField ID        =
            DatasourceField.property(ActionContextEntity.class, "id", Long.class);
    public static final DatasourceField METADATA_ID =
            DatasourceField.property(ActionContextEntity.class, "metadataId", Long.class);
    public static final DatasourceField METADATA   =
            DatasourceField.property(ActionContextEntity.class, "metadata", ActionMetadataEntity.class);
    public static final DatasourceField CORRELATION_ID =
            DatasourceField.property(ActionContextEntity.class, "correlationId", String.class);
    public static final DatasourceField ITEM_ID   =
            DatasourceField.property(ActionContextEntity.class, "itemId", String.class);
    public static final DatasourceField PROCESSES =
            DatasourceField.property(ActionContextEntity.class, "processes", ActionProcessEntity.class);
    // @formatter:on

    public static final String METADATA_RELATION = "metadata";

    public static volatile SingularAttribute<ActionContextEntity, Long>                 id;
    public static volatile SingularAttribute<ActionContextEntity, Long>                 metadataId;
    public static volatile SingularAttribute<ActionContextEntity, ActionMetadataEntity> metadata;
    public static volatile SingularAttribute<ActionContextEntity, UUID>                 correlationId;
    public static volatile SingularAttribute<ActionContextEntity, String>               itemId;
    public static volatile SetAttribute<ActionContextEntity, ActionProcessEntity>       processes;
}
