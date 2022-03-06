import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '@/components/home/Home'
import BookDetail from '@/components/detail/BookDetail'
import Edit from "@/components/edit/Edit";

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
        },
        {
            path: '/edit',
            name: 'Edit',
            component: Edit
        }
    ]
})