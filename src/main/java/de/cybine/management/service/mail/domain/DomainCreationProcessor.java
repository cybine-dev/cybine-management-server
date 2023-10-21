package de.cybine.management.service.mail.domain;

import de.cybine.management.data.action.process.*;
import de.cybine.management.data.mail.domain.*;
import de.cybine.management.service.action.*;
import de.cybine.management.service.mail.*;
import de.cybine.management.util.converter.*;
import io.quarkus.arc.*;
import jakarta.enterprise.inject.*;
import lombok.*;

public class DomainCreationProcessor implements ActionProcessor<MailDomain>
{
    private final Instance<MailDomainRepository> repository = Arc.container().select(MailDomainRepository.class);

    private final Instance<ConverterRegistry> converterRegistry = Arc.container().select(ConverterRegistry.class);

    @Getter
    private final ActionProcessorMetadata metadata = DomainService.CREATION.toProcessorMetadata("prepare",
            BaseActionProcessStatus.TERMINATED.getName());

    public boolean shouldExecute(ActionService service, ActionProcessMetadata nextState)
    {
        return true;
    }

    @Override
    public ActionProcessorResult<MailDomain> process(ActionService service, ActionProcess previousState)
    {
        MailDomain domain = previousState.<MailDomain>getData().orElseThrow();
        MailDomainEntity entity = this.converterRegistry.get()
                                                        .getProcessor(MailDomain.class, MailDomainEntity.class)
                                                        .toItem(domain)
                                                        .result();

        this.repository.get().persist(entity);
        service.updateItemId(previousState.getContextId(), entity.getId().toString());

        MailDomain result = this.converterRegistry.get()
                                                  .getProcessor(MailDomainEntity.class, MailDomain.class)
                                                  .toItem(entity)
                                                  .result();

        return ActionProcessorResult.of(result);
    }
}
