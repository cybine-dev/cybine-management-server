package de.cybine.management.config;

import de.cybine.management.exception.*;
import io.quarkus.liquibase.*;
import io.quarkus.runtime.*;
import jakarta.enterprise.context.*;
import jakarta.enterprise.event.*;
import liquibase.*;
import liquibase.changelog.*;
import liquibase.exception.*;
import lombok.*;
import lombok.extern.java.*;

@Log
@ApplicationScoped
@RequiredArgsConstructor
public class LiquibaseConfig
{
    private final LiquibaseFactory factory;

    public void setup(@Observes StartupEvent event)
    {
        try
        {
            this.checkMigrations();
        }
        catch (LiquibaseException exception)
        {
            throw new DatabaseMigrationException(exception);
        }
    }

    private void checkMigrations( ) throws LiquibaseException
    {
        try (Liquibase liquibase = this.factory.createLiquibase())
        {
            Contexts contexts = this.factory.createContexts();
            LabelExpression labels = this.factory.createLabels();

            liquibase.validate();
            liquibase.update(contexts, labels);

            liquibase.getChangeSetStatuses(contexts, labels).forEach(this::logMigration);
        }
    }

    private void logMigration(ChangeSetStatus status)
    {
    }
}
