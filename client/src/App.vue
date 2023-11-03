<template>
  <h1>Departamentos</h1>
  <p v-if="error">Alguma coisa esta errada...</p>
  <p v-if="loading">Carregando...</p>
  <p v-else v-for="character in result.departments" :key="character.id">
    {{ character.name }}
  </p>
  <div></div>
</template>

<script lang="ts">
import { gql } from '@apollo/client';
import { useQuery } from '@vue/apollo-composable';

export default {
  name: 'App',
  setup() {
    const GET_DEPARTMENTS = gql`
      {
        departments {
          id
          name
        }
      }
    `
    const { result, loading, error } = useQuery(GET_DEPARTMENTS);
    return {
      result,
      loading,
      error
    }
  }
}
</script>


<style scoped>
</style>
