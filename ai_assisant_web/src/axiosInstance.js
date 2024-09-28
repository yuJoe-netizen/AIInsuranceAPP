// axiosInstance.js
import axios from 'axios';

const service = axios.create({
  // baseURL: process.env.VUE_APP_BASE_API, // 设置基础URL
   baseURL: "", // 设置基础URL
  timeout: 5000, // 请求超时时间
});

// 添加请求拦截器
service.interceptors.request.use(
  (config) => {
    // 在发送请求之前做些什么，例如加入 token
    return config;
  },
  (error) => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 添加响应拦截器
service.interceptors.response.use(
  (response) => {
    // 对响应数据做点什么
    return response.data;
  },
  (error) => {
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);

export default service;
