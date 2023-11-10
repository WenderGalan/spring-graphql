<template>
  <div class="bg-blue-400 min-h-screen">
    <div class="flex items-center justify-center flex-col text-white">
      <h1 class="text-3xl font-bold my-5">Departamentos</h1>
      <p v-if="error">Alguma coisa está errada...</p>
      <p v-if="loading">Carregando...</p>
      <div v-else v-for="department in departments" :key="department?.id">
        <p>{{ department?.name }}</p>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ApolloError, gql } from '@apollo/client';
import { provideApolloClient, useQuery } from '@vue/apollo-composable';
import { isValidQuery } from "@/Query";
import { onMounted, ref } from "vue";
import { apolloClient } from "@/main";

let error = ref<ApolloError | null>(null);
let loading = ref(true);
let departments = ref([]);
provideApolloClient(apolloClient);

/**
 */
async function getIntrospectionResult () {
  const introspectionQuery = `
      query IntrospectionQuery {
    __schema {
      queryType { name }
      mutationType { name }
      subscriptionType { name }
      types {
        ...FullType
      }
      directives {
        name
        description
        args {
          ...InputValue
        }
        onOperation
        onFragment
        onField
      }
    }
  }

  fragment FullType on __Type {
    kind
    name
    description
    fields(includeDeprecated: true) {
      name
      description
      args {
        ...InputValue
      }
      type {
        ...TypeRef
      }
      isDeprecated
      deprecationReason
    }
    inputFields {
      ...InputValue
    }
    interfaces {
      ...TypeRef
    }
    enumValues(includeDeprecated: true) {
      name
      description
      isDeprecated
      deprecationReason
    }
    possibleTypes {
      ...TypeRef
    }
  }

  fragment InputValue on __InputValue {
    name
    description
    type { ...TypeRef }
    defaultValue
  }

  fragment TypeRef on __Type {
    kind
    name
    ofType {
      kind
      name
      ofType {
        kind
        name
        ofType {
          kind
          name
        }
      }
    }
  }
    `;

  return await fetch('http://localhost:8080/graphql', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      query: introspectionQuery,
    }),
  })
      .then((response) => response.json())
      .then((result) => result.data.__schema);
}

/**
 */
onMounted(async () => {
  const GET_DEPARTMENTS = gql`
      {
        departments {
          id
          name
        }
      }
    `

  const resultQuery = isValidQuery(GET_DEPARTMENTS, await getIntrospectionResult());
  if (resultQuery) {
    const { onResult, onError } = useQuery(GET_DEPARTMENTS);

    onResult((result) => {
      departments.value = result.data?.departments || [];
      loading.value = false;
    });
    onError((er) => {
      error.value = er
      loading.value = false;
    });
  }
})
</script>

<style scoped>
</style>
