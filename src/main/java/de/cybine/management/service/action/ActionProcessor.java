package de.cybine.management.service.action;

import de.cybine.management.data.action.process.*;

public interface ActionProcessor<T>
{
    ActionProcessorMetadata getMetadata( );

    boolean shouldExecute(ActionService service, ActionProcessMetadata nextState);

    ActionProcessorResult<T> process(ActionService service, ActionProcess previousState);
}
