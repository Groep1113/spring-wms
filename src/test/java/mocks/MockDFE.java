package mocks;

import entity.User;
import graphql.AuthContext;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionId;
import graphql.execution.ExecutionStepInfo;
import graphql.language.Field;
import graphql.language.FragmentDefinition;
import graphql.schema.*;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.Map;

public class MockDFE implements DataFetchingEnvironment {
    @Override
    public <T> T getSource() {
        return null;
    }

    @Override
    public Map<String, Object> getArguments() {
        return null;
    }

    @Override
    public boolean containsArgument(String name) {
        return false;
    }

    @Override
    public <T> T getArgument(String name) {
        return null;
    }

    @Override
    public <T> T getContext() {
        User user = new User();
        user.setId(1337);
        user.setFirstName("unit");
        user.setLastName("test");
        return (T) new AuthContext(user, null, null);
    }

    @Override
    public <T> T getRoot() {
        return null;
    }

    @Override
    public GraphQLFieldDefinition getFieldDefinition() {
        return null;
    }

    @Override
    public List<Field> getFields() {
        return null;
    }

    @Override
    public Field getField() {
        return null;
    }

    @Override
    public GraphQLOutputType getFieldType() {
        return null;
    }

    @Override
    public ExecutionStepInfo getExecutionStepInfo() {
        return null;
    }

    @Override
    public GraphQLType getParentType() {
        return null;
    }

    @Override
    public GraphQLSchema getGraphQLSchema() {
        return null;
    }

    @Override
    public Map<String, FragmentDefinition> getFragmentsByName() {
        return null;
    }

    @Override
    public ExecutionId getExecutionId() {
        return null;
    }

    @Override
    public DataFetchingFieldSelectionSet getSelectionSet() {
        return null;
    }

    @Override
    public ExecutionContext getExecutionContext() {
        return null;
    }

    @Override
    public <K, V> DataLoader<K, V> getDataLoader(String dataLoaderName) {
        return null;
    }
}
