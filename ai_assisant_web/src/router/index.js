import { createRouter, createWebHistory } from 'vue-router'
import loginView from '@/views/loginView.vue'
import mainView from '@/views/mainView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: loginView
    },
    {
      path: '/main',
      name: 'main',
      component: mainView,
      children:[
        {
          path:'',
          name:'custList',
          component:()=>import('../views/CustList.vue')
        }
      ]
    },
    {
      path: '/connectCust',
      name: 'connectCust',
      component:()=>import('../views/connectCust.vue')
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }
    // {
    //   path:'/connect',
    //   name:'connectCust',
    //   component:()=>import('../views/connectCust.vue')
    // }
  ]
})

export default router
