import { DocumentNode, GraphQLError } from 'graphql';

interface IntrospectionResult {
    types: Array<{
        name: string;
        fields: Array<{
            name: string;
            args: Array<{
                name: string;
            }>;
        }>;
    }>;
}

const findField = (typeName: string, fieldName: string, introspectionResult: IntrospectionResult) => {
    const type = introspectionResult.types.find(t => t.name === typeName);

    if (!type) {
        throw new Error(`Tipo "${typeName}" não encontrado no resultado da introspecção.`);
    }

    const field = type.fields.find(f => f.name === fieldName);

    if (!field) {
        throw new Error(`Campo "${fieldName}" não encontrado no tipo "${typeName}".`);
    }

    return field;
};

export const isValidQuery = (query: DocumentNode, introspectionResult: IntrospectionResult): boolean => {
    try {
        query.definitions.forEach(definition => {
            if (definition.kind === 'OperationDefinition') {
                definition.selectionSet?.selections?.forEach(selection => {
                    if (selection.kind === 'Field') {
                        const operationType = definition.operation;
                        const typeName = operationType.charAt(0).toUpperCase() + operationType.slice(1); // Capitalize first letter

                        const fieldName = selection.name?.value;

                        if (!fieldName) {
                            throw new Error('Nome do campo não encontrado na seleção.');
                        }

                        findField(typeName, fieldName, introspectionResult);

                        // Verifique os argumentos
                        if (selection.arguments) {
                            selection.arguments.forEach(argument => {
                                const field = findField(typeName, fieldName, introspectionResult);
                                const arg = field.args.find(a => a.name === argument.name.value);

                                if (!arg) {
                                    throw new Error(
                                        `Argumento "${argument.name.value}" não encontrado para o campo "${fieldName}".`,
                                    );
                                }
                            });
                        }
                    }
                });
            }
        });

        return true;
    } catch (error) {
        if (error instanceof GraphQLError) {
            console.error('Erro ao validar a query:', error.message);
        } else {
            console.error('Erro inesperado ao validar a query:', error);
        }
        return false;
    }
};
