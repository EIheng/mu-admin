import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'

import App from './app.vue'
import router from './router'
import { createPinia } from 'pinia'

const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component("Element" + key, component)
}
app.use(router)
app.use(createPinia())

app.mount('#app')