package com.optima.test_task.exceptions;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GraphQlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof ResourceInActiveException || ex instanceof ResourceBookedException
                || ex instanceof ResourceTimeException || ex instanceof PaymentException) {
            return buildError(ex, env, ErrorType.BAD_REQUEST);
        }

        if (ex instanceof UsernameNotFoundException || ex instanceof ResourceNotFoundException ||
            ex instanceof BookingNotFoundException) {
            return buildError(ex, env, ErrorType.NOT_FOUND);
        }

        if(ex instanceof UserUnAuthorizedException) {
            return buildError(ex, env, ErrorType.UNAUTHORIZED);
        }

        if (ex instanceof AccessDeniedException) {
            return buildError(ex, env, ErrorType.FORBIDDEN);
        }

        return null;
    }

    private GraphQLError buildError(Throwable ex, DataFetchingEnvironment env, ErrorType errorType) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(errorType)
                .path(env.getExecutionStepInfo().getPath())
                .build();
    }
}
