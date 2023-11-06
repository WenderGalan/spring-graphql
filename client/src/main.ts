import {createApp, h, provide} from 'vue'
import App from './App.vue'
import {DefaultApolloClient} from '@vue/apollo-composable'
import {ApolloClient, InMemoryCache} from "@apollo/client/core";
import './index.css'

const apolloClient = new ApolloClient({
    uri: 'http://localhost:8080/graphql',
    cache: new InMemoryCache(),
});

const app = createApp({
    setup() {
        provide(DefaultApolloClient, apolloClient)
    },
    render: () => h(App)
});

app.mount('#app');

