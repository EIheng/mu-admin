import { InjectionKey } from 'vue'
import { createStore, useStore as baseUseStore, Store } from 'vuex'

export interface State {
    user: {
        username: string,
    }
}

export const key: InjectionKey<Store<State>> = Symbol()

export const store = createStore<State>({
    state: {
        user: {
            username: "",
        }
    }
})

export function useStore() {
    return baseUseStore(key)
}