<script setup>
// import { RouterLink, RouterView } from 'vue-router'
import axios from '../axiosInstance.js';
import { ref,reactive } from 'vue'
import userAvatar from '../assets/user.png'
import kefu from '../assets/kefu.png'
import phone from '../assets/phone.png'
import rebot from '../assets/rebot.png'
import { onMounted } from 'vue'
const data =reactive({
  message:[],
  rightDatas:[],
});
const scrollContainer = ref(null);
// const listUser = []
onMounted(() => {
  // setTimeout(() => {
  //     console.log('内容增加时', scrollContainer.value.scrollHeight);
  //     scrollContainer.value.scrollTo({ top: scrollContainer.value.scrollHeight });
  //   }, 20); // 注意这里需要延迟20ms正好可以获取到更新后的dom节点

  const ws = new WebSocket('ws://localhost:8891/call?userId=yujiangjun')
  ws.onopen = function () {
  }
  ws.onerror = function (e) {
    console.log(`发生了websocket错误:${e}`)
  }
  ws.onmessage = function (e) {
    getRightData()
    data.message.push(JSON.parse(e.data))
  }
  window.workbench = new window.WorkbenchSdk({
   dom:"call",
   height:"50px",
   instanceId:"aiHelpAgent",
   regionId:"cn-shanghai",
   ajaxOrigin:"http://127.0.0.1:8891",
   ajaxPath:"/aliyun/ccc/api",
    onInit() {
     window.workbench.register() // 想实现自动上线在此注册
  },
  onRegister(config){
    console.log('软电话注册')
    console.log(config)
  },
  onLogIn(){
    console.log('登录成功')

  },
  onLogOut(){
    console.log('登出')
  },
  onBreak(){
    console.log('小休回调')
  },
  onReady(){
    console.log('空闲回调')
  },
  onErrorNotify(error) {
    console.warn(error);
  },
  onCallDialing(callDetail){
    console.log('拨打前的回调')
    console.log(callDetail)
  }
  })
});

async function getRightData(){
 let resp = await axios.get('/suggest/getSuggest',{
    params:{
      context:"证据"
    }
  })
  data.rightDatas.push(resp.data)
  const uniqueArr = data.rightDatas.reduce((acc, curr) => {
    if (!acc[curr.pressurePoint]) {
      acc[curr.pressurePoint] = curr;
    }
    return acc;
  }, {});
  const result = Object.values(uniqueArr);
  data.rightDatas=[];
  result.forEach(t=>{
    data.rightDatas.push(t)
  })
  console.log('接收到右边的数据');
  console.log(data.rightDatas)
}
</script>
<template>
  <div class="page-body">
    <div>
      <div class="chat-window">
        <div class="chat-header">
          <img :src="phone" @click="addData"/>
          <div id="call">
            <div></div>
          </div>
        </div>
        <div class="chat-messages">
          <div v-for="(item,index) in data.message" :key="index">
            <div class="message received-message" v-if="item.channelType==='agent'">
              <img :src="userAvatar" alt="接收者头像" />
              <div class="message-boarder-send">
                <div class="message-text">{{ item.text }}</div>
              </div>
            </div>
            <div style="height: 10px;"></div>
            <div class="message sent-message" v-if="item.channelType==='customer'">
              <div class="message-boarder-rece">
                <div class="message-text">{{ item.text }}</div>
              </div>
              <img :src="kefu" alt="发送者头像" />
            </div>
            <div style="height: 10px;"></div>
          </div>
          
        </div>
      </div>
    </div>
    <div class="ai">
      <div class="chat-window">
        <div class="ai-header">
          <p style="font-size: 25px;">AI助理</p>
        </div>
        <div v-for="(item,index) in data.rightDatas" :key="index" class="left-body" ref="scrollContainer">
          <div class="user-point">
          <div>未完成:<span style="font-weight: 700; color: #fe864f;">{{ item. pressurePoint}}</span></div>
        </div>
        <div v-for="(item1,index1) in item.promptList" :key="index1">
          <div class="user-question">
            <img :src="userAvatar" alt="接收者头像" class="ai-user-avatar" />
            <div><span style="">客户问题点：</span><span style="font-size: 20px;font-weight: 700;color:#00877f">{{ item1.questionPoint}}</span></div>
          </div>
          <div class="ai-create-header">
            <img :src="rebot">
              <p><span style="font-weight: 700;">AI 为您推荐一下三种话术</span></p>
          </div>

          <div v-for="(item2,index2) in item1.prompt" :key="index2">
            <div class="ai-create-content1" v-if="item2.lawTitle">
              <div class="content1-header">
                {{ item2. title}}
              </div>
              <div class="content1-body">
                {{ item2. content}}
              </div>
              <div class="content1-law">
                  <div class="law-name">
                    {{ item2. lawTitle}}
                  </div>
                  <div class="law-content">
                    {{ item2. lawContent}}
                  </div>
              </div>
            </div>

            <div class="ai-create-content2" v-else>
              <div class="content2-header">
                <p>{{ item2. title}}</p>
              </div>
              <div class="content2-body">
                <p>{{ item2. content}}</p>
              </div>
            </div>
          </div>
          
        </div>
        
        </div>
        
      </div>
    </div>
  </div>
</template>
<style lang="css">
.chat-window {
  width: 400px;
  height: 600px;
  margin: 0 auto;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden;
}

.chat-header {
  background-color: #4285f4;
  color: black;
  padding: 10px;
  justify-content: flex-end;
  display: flex;
}

.ai-header {
  background-color: #919295;
  color: #fffdfd;
  padding: 10px;
  display: flex;
  justify-content: center;
}



.chat-header img {
  margin: 0;
  width: 12%;
}

.chat-messages {
  height: 80%;
  padding: 10px;
  overflow-y: scroll;
  background-color: beige;
}

.left-body{
  height: 560px;
  padding: 10px;
  overflow-y: scroll;
}
.message {
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.sent-message {
  justify-content: flex-end;
}

.received-message {
  justify-content: flex-start;
}

.message img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.message-text {
  padding: 10px;
  border-radius: 5px;
  color: #000;
}
.message-boarder-rece {
  border: 1px red;
  background-color: cornflowerblue;
  border-radius: 10px;
  margin-right: 5px;
}
.message-boarder-send {
  border: 1px red;
  background-color: #fff;
  border-radius: 10px;
  margin-left: 5px;
}
.sent-message.message-text {
  background-color: #dcf8c6;
  color: #000;
}

.received-message.message-text {
  background-color: #87ceeb;
  color: #000;
}

.chat-input {
  padding: 10px;
  background-color: #f1f1f1;
}

.chat-input input {
  width: 80%;
  padding: 5px;
  border: none;
  border-radius: 5px;
}

.chat-input button {
  width: 15%;
  padding: 5px 10px;
  background-color: #4285f4;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.page-body{
  display: flex;
  flex-direction: row;
  /* flex-wrap: wrap; */
  gap: 50px; /* 设置垂直和水平间隔为 20 像素 */
}
.ai{
  /* margin-left: 200px; */
}
.user-point{
  width: 100%;
  background-color: hwb(34 53% 8%);
  color: #000;
  display: flex;
  align-items: center;
}

.user-question{
  width: 100%;
  height: 60px;
  margin-top: 20px;
  background-color: #87ceeb;
  color: #000;
  display: flex;
  /* justify-content: center; */
  align-items: center;
}
.ai-user-avatar{
  height: 35px;
}
.ai-create-header{
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ai-create-header img{
  height: 20px;
}
.ai-create-content1{
  width: 100%;
  background-color: lightgoldenrodyellow;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.ai-create-content1 .content1-header{
  font-weight: 600;
  color: #87a8eb;
}
.ai-create-content1 .content1-law{
  width: 95%;
  /* height: 60px; */
  border: 1px solid red;
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.ai-create-content1 .content1-law .law-name{
  background-color: red;
  color: #ccc;
  font-weight: 600;
  margin-top: 10px;
}
.ai-create-content1 .content1-law .law-name .law-content{
  padding: 10px 10px 10px 10px;
}

.ai-create-content2{
  width: 100%;
  background-color: rgb(244, 242, 240);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 20px;
}

.ai-create-content2 .content2-header p{
  color: #2181b6;
  font-size: 14px;
  font-weight: 700;
}

.ai-create-content2 .content2-body p{
  color: #000;
  font-size: 12px;
  /* font-weight: 700; */
}
</style>

