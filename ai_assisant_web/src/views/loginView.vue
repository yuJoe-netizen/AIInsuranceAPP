<template>
    <div class="login-page">
      <div class="login-box">
        <h2>系统登录</h2>
        <input type="text" v-model="phone" placeholder="手机号" />
        <input type="password" v-model="password" placeholder="密码" />
        <button @click="login">登录</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from '../axiosInstance';
  import { message } from 'ant-design-vue';
  import { useRouter } from 'vue-router';

  const router = useRouter();
  const phone = ref('');
  const password = ref('');
  
  const login = async () => {
    // 这里可以添加登录逻辑，比如发送请求到后端进行验证
    console.log('用户名：', phone.value);
    console.log('密码：', password.value);
    let resp = await axios.post('/api/user/login',{
      phone: phone.value,
      password: password.value
    })
    console.log('登录成功！',resp)
    if (resp.code !== 200) {
      message.error(resp.msg)
      return
    }
    router.push('/main');
  };
  </script>
  
  <style scoped>
  .login-page {
    background-image: url('@/assets/login-bg.jpeg');
    background-size: cover;
    background-position: center;
    height: 100vh;
    display: flex;
    width: 100%;
    justify-content: center;
    align-items: center;
  }
  
  .login-box {
    background-color: rgba(255, 255, 255, 0.8);
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  }
  
  h2 {
    text-align: center;
    color: #333;
    margin-bottom: 20px;
  }
  
  input {
    width: 100%;
    padding: 12px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    outline: none;
  }
  
  button {
    width: 100%;
    padding: 12px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  </style>