import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '@/components/home/Home'
import BookDetail from '@/components/detail/BookDetail'

Vue.use(VueRouter)

export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: Home
        },
        {
            path: '/home',
            component: Home
        },
        {
            path: '/detail',
            name: 'BookDetail',
            component: BookDetail,
            props: true
        }
    ]
})