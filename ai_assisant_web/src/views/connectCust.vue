<script setup>
// import { RouterLink, RouterView } from 'vue-router'
import axios from '../axiosInstance.js'
import { ref, reactive } from 'vue'
import userAvatar from '../assets/user.png'
import kefu from '../assets/kefu.png'
//import phone from '../assets/phone.png'
import rebot from '../assets/rebot.png'
import { onMounted } from 'vue'
const data = reactive({
  message: [],
  rightDatas: []
})
const pressurePoint = ref('')
const questionData = reactive({
  data: []
})
const cuiShouInfo = reactive({
  data:{}
})
onMounted(() => {
  // 获取施压点
  getPressurePoint()
  // 催收信息
  getCuiShouInfo()
  //
  const ws = new WebSocket('ws://172.22.229.128:8891/call?userId=yujiangjun')
  ws.onopen = function () {}
  ws.onerror = function (e) {
    console.log(`发生了websocket错误:${e}`)
  }
  ws.onmessage = function (e) {
    // getRightData()
    let mes = JSON.parse(e.data)
    if (mes.channelType === 'agent') {
      console.log(mes)
      console.log('发起请求获取问题点')
      getRightData(mes.text)
    }
    data.message.push(JSON.parse(e.data))
  }
  window.workbench = new window.WorkbenchSdk({
    dom: 'call',
    height: '50px',
    instanceId: 'aiHelpAgent',
    regionId: 'cn-shanghai',
    // ajaxOrigin: 'http://127.0.0.1:8891',
    ajaxPath: '/aliyun/ccc/api',
    onInit() {
      window.workbench.register() // 想实现自动上线在此注册
    },
    onRegister(config) {
      console.log('软电话注册')
      console.log(config)
    },
    onLogIn() {
      console.log('登录成功')
    },
    onLogOut() {
      console.log('登出')
    },
    onBreak() {
      console.log('小休回调')
    },
    onReady() {
      console.log('空闲回调')
    },
    onErrorNotify(error) {
      console.warn(error)
    },
    onCallDialing(callDetail) {
      console.log('拨打前的回调')
      console.log(callDetail)
    }
  })
})

async function getRightData(mesTxt) {
  let resp = await axios.get('/suggest/getSuggest', {
    params: {
      pressurePoint: '证据',
      questionPointKeyWords: mesTxt
    }
  })
  resp.data.forEach((e) => {
    questionData.data.push(e)
  })

  console.log(questionData.data)
}

async function getPressurePoint() {
  let resp = await axios.get('/suggest/getPressurePoint', {
    params: {}
  })
  console.log('获取施压点')
  console.log(resp)
  pressurePoint.value = resp.data
}

async function getCuiShouInfo() {
  let resp = await axios.get('/suggest/getCuiShouInfo',{
    params:{

    }
  })
  cuiShouInfo.data= resp.data
  console.log('催收信息：',cuiShouInfo.data)
}
</script>
<template>
  <div class="page-body">
    <div class="ai">
      <div class="cs-window">
        <div class="ai-header">
          <p style="font-size: 25px">催收方案</p>
        </div>

        <div class="left-body">

        
        <div class="sub-header">
          <div><span style="font-weight: bold;color: black;margin-left: 8px">历史逾期总结</span><span style="margin-left: 10px;">逾期{{cuiShouInfo.data.overdueTimes}}次</span></div>
        </div>

        <!--时间轴-->
        <div class="time">
          <div class="time-item" v-for="(item,index) in cuiShouInfo.data.overdueMonthList" :key="index">
            {{ item.overdueMonth }}
          </div>
          <!-- <div class="time-item">4/1/2024</div>
          <div class="time-item">2/1/2024</div>
          <div class="time-item">12/1/2023</div> -->
        </div>

        <!--逾期内容-->
        <div class="overdue">
          <div class="header"><span>最高逾期天数：  {{cuiShouInfo.data.highOverdueDay}}天</span></div>
          <div class="context"><span style="font-size: 14px;">{{ cuiShouInfo.data.overdueSumUp }}</span></div>
        </div>

        <!--建议策略-->
        <div class="suggest">
          <div class="header"><span style="font-weight: bold;">本次建议策略</span></div>
          <div class="context">
            <div>施压点: <span style="font-weight: bold;background-color: #fe864f;">{{ cuiShouInfo.data.pressurePoint }}</span></div>
            <div style="display: flex;font-size: 14px;">{{ cuiShouInfo.data.adviceStrategies }}</div>
          </div>
        </div>

        <!--沟通大纲-->
        <div class="cc">
          <div class="header"><span style="font-weight: bold;">沟通大纲</span></div>
          <div class="body" v-for="(item,index) in cuiShouInfo.data.communicateOutline" :key="index">
            <div class="body-item">
              <div style="width: 10px;height: 25px; background-color: #fe864f;"></div>
              <div class="desc"><span style="font-weight: bold;">{{ item.communicatePoint }}</span></div>
            </div>
            <div class="body-item">
              <div style="font-size: 14px;">{{ item.communicateContent }}</div>
            </div>
          </div>
          <!-- <div class="body">
            <div class="body-item">
              <div style="width: 10px;height: 25px; background-color: #fe864f;"></div>
              <div class="desc"><span style="font-weight: bold;">理解与协助</span></div>
            </div>
            <div class="body-item">
              <div>展示理解客户困难，提供专业建议，协助客户理解逾期后果，引导其采取行动</div>
            </div>
          </div> -->
        </div>
      </div>

      </div>
    </div>
    <div>
      <div class="chat-window">
        <div class="chat-header">
          <!-- <img :src="phone" @click="addData" /> -->
          <div id="call">
            <div></div>
          </div>
        </div>
        <div class="chat-messages">
          <div v-for="(item, index) in data.message" :key="index">
            <div class="message received-message" v-if="item.channelType === 'customer'">
              <img :src="userAvatar" alt="接收者头像" />
              <div class="message-boarder-send">
                <div class="message-text">{{ item.text }}</div>
              </div>
            </div>
            <div style="height: 10px"></div>
            <div class="message sent-message" v-if="item.channelType === 'agent'">
              <div class="message-boarder-rece">
                <div class="message-text">{{ item.text }}</div>
              </div>
              <img :src="kefu" alt="发送者头像" />
            </div>
            <div style="height: 10px"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="ai">
      <div class="chat-window">
        <div class="ai-header">
          <p style="font-size: 25px">AI助理</p>
        </div>
        <div class="user-point">
          <div>
            未完成:<span style="font-weight: 700; color: #fe864f">{{ pressurePoint }}</span>
          </div>
        </div>
        <div class="left-body">
          <div v-for="(item1, index1) in questionData.data" :key="index1">
            <div class="user-question">
              <img :src="userAvatar" alt="接收者头像" class="ai-user-avatar" />
              <div>
                <span style="">客户问题点：</span
                ><span style="font-size: 20px; font-weight: 700; color: #00877f">{{
                  item1.questionPoint
                }}</span>
              </div>
            </div>
            <div class="ai-create-header">
              <img :src="rebot" />
              <p><span style="font-weight: 700">AI 为您推荐一下三种话术</span></p>
            </div>

            <div v-for="(item2, index2) in item1.prompt" :key="index2">
              <div class="ai-create-content1" v-if="item2.lawTitle">
                <div class="content1-header">
                  {{ item2.title }}
                </div>
                <div class="content1-body">
                  {{ item2.content }}
                </div>
                <div class="content1-law">
                  <div class="law-name">
                    {{ item2.lawTitle }}
                  </div>
                  <div class="law-content">
                    {{ item2.lawContent }}
                  </div>
                </div>
              </div>

              <div class="ai-create-content2" v-else>
                <div class="content2-header">
                  <p>{{ item2.title }}</p>
                </div>
                <div class="content2-body">
                  <p>{{ item2.content }}</p>
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
.cc{
  width: 100%;
  height: 25px;
  background-color: #919295;
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-top: 150px;
}
.cc .header {
  width: 100%;
  margin-left: 15px;
}
.cc .body{
  display: flex;
  flex-direction: column;
  align-items: center;
}
.cc .body .body-item{
  display: flex;
  width: 90%;
  justify-self: start;
}

.cc .body .body-item .desc{
  width: 90%;
  margin-left: 5px;
}
.suggest{
  width: 100%;
  height: 25px;
  background-color: #919295;
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-top: 5px;
}

.suggest .header{
  width: 100%;
  margin-left: 15px;
}

.suggest .context{
  width: 90%;
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  margin-top: 7px;
}
.overdue{
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 5px;
  flex-direction: column;
}
.overdue .header{
  width: 100%;
}
.overdue .context{
  width: 100%;
  background-color: #2181b6;
  margin-top: 3px;
}
.time-item{
  width: 30%;
  height: 100%;
  color: #000;
}
.time{
  width: 100%;
  height: 20px;
  margin-top: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.sub-header{
  display: flex;
  width: 100%;
  height: 30px;
  background-color: #bbbdc2;
  /* justify-content: center; */
  align-items: center;
}
.cs-window{
  width: 400px;
  height: 600px;
  margin: 0 auto;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.chat-window {
  width: 400px;
  height: 600px;
  display: flex;
  flex-direction: column;
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
  justify-content: center;
  display: flex;
}

.ai-header {
  background-color: #919295;
  color: #fffdfd;
  padding: 5px;
  display: flex;
  justify-content: center;
}

.chat-header img {
  margin: 0;
  width: 12%;
}

.chat-messages {
  height: 90%;
  padding: 10px;
  overflow-y: scroll;
  background-color: beige;
}

.left-body {
  height: 560px;
  padding: 10px;
  overflow-y: hidden;
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
.page-body {
  display: flex;
  flex-direction: row;
  /* flex-wrap: wrap; */
  gap: 3px; /* 设置垂直和水平间隔为 20 像素 */
}
.ai {
  /* margin-left: 200px; */
}
.user-point {
  width: 100%;
  background-color: hwb(34 53% 8%);
  color: #000;
  display: flex;
  align-items: center;
}

.user-question {
  width: 100%;
  height: 60px;
  margin-top: 20px;
  background-color: #87ceeb;
  color: #000;
  display: flex;
  /* justify-content: center; */
  align-items: center;
}
.ai-user-avatar {
  height: 35px;
}
.ai-create-header {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ai-create-header img {
  height: 20px;
}
.ai-create-content1 {
  width: 100%;
  background-color: lightgoldenrodyellow;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.ai-create-content1 .content1-header {
  font-weight: 600;
  color: #87a8eb;
}
.ai-create-content1 .content1-law {
  width: 95%;
  /* height: 60px; */
  border: 1px solid red;
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.ai-create-content1 .content1-law .law-name {
  background-color: red;
  color: #ccc;
  font-weight: 600;
  margin-top: 10px;
}
.ai-create-content1 .content1-law .law-name .law-content {
  padding: 10px 10px 10px 10px;
}

.ai-create-content2 {
  width: 100%;
  background-color: rgb(244, 242, 240);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 20px;
}

.ai-create-content2 .content2-header p {
  color: #2181b6;
  font-size: 14px;
  font-weight: 700;
}

.ai-create-content2 .content2-body p {
  color: #000;
  font-size: 12px;
  /* font-weight: 700; */
}
</style>
