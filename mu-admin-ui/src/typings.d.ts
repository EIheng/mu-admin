// typings.d.ts or router.ts
import 'vue-router'

declare module 'vue-router' {
    interface RouteMeta {
        icon: string;
        disabled: boolean;
    }
}