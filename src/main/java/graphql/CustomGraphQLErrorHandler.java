package graphql;

import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public boolean errorsPresent(List<GraphQLError> errors) {
        return errors != null && !errors.isEmpty();
    }

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors
            .stream()
            .map(e -> new GenericGraphQLError(e.getMessage()))
            .collect(Collectors.toList());
    }

}
