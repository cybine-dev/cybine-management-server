package de.cybine.management.service.action;

import de.cybine.management.data.action.process.*;
import lombok.*;

import java.util.function.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericActionProcessor<T> implements ActionProcessor<T>
{
    private static final DefaultAction       DEFAULT_ACTION       = new DefaultAction();
    private static final DefaultPrecondition DEFAULT_PRECONDITION = new DefaultPrecondition();

    private final ActionProcessorMetadata metadata;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final BiPredicate<ActionService, ActionProcessMetadata> precondition;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final BiFunction<ActionService, ActionProcess, ActionProcessorResult<T>> action;

    @Override
    public boolean shouldExecute(ActionService service, ActionProcessMetadata nextState)
    {
        return this.precondition.test(service, nextState);
    }

    @Override
    public ActionProcessorResult<T> process(ActionService service, ActionProcess previousState)
    {
        return this.action.apply(service, previousState);
    }

    public static GenericActionProcessor<Void> of(ActionProcessorMetadata metadata)
    {
        return new GenericActionProcessor<>(metadata, DEFAULT_PRECONDITION, DEFAULT_ACTION);
    }

    public static <T> GenericActionProcessor<T> of(ActionProcessorMetadata metadata,
            BiFunction<ActionService, ActionProcess, ActionProcessorResult<T>> action)
    {
        return new GenericActionProcessor<>(metadata, DEFAULT_PRECONDITION, action);
    }

    public static <T> GenericActionProcessor<T> of(ActionProcessorMetadata metadata,
            BiFunction<ActionService, ActionProcess, ActionProcessorResult<T>> action,
            BiPredicate<ActionService, ActionProcessMetadata> precondition)
    {
        return new GenericActionProcessor<>(metadata, precondition, action);
    }

    private static class DefaultPrecondition implements BiPredicate<ActionService, ActionProcessMetadata>
    {
        @Override
        public boolean test(ActionService service, ActionProcessMetadata previousState)
        {
            return true;
        }
    }

    private static class DefaultAction implements BiFunction<ActionService, ActionProcess, ActionProcessorResult<Void>>
    {
        @Override
        public ActionProcessorResult<Void> apply(ActionService service, ActionProcess previousState)
        {
            return ActionProcessorResult.empty();
        }
    }
}
