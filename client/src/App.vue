<template>
  <div class="bg-blue-400 min-h-screen">
    <div class="flex items-center justify-center flex-col text-white">
      <h1 class="text-3xl font-bold my-5">Departamentos</h1>
      <p v-if="error">Alguma coisa está errada...</p>
      <p v-if="loading">Carregando...</p>
      <div v-else v-for="character in result?.departments" :key="character?.id">
        <p>{{ character?.name }}</p>
      </div>
    </div>
  </div>
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
