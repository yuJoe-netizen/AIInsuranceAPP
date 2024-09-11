<script setup>
// import { RouterLink, RouterView } from 'vue-router'
import {Popup} from 'vue3-popup-layer'
import { getCurrentInstance } from 'vue';
import axios from '../axiosInstance.js';
import { ref,reactive } from 'vue'
import userAvatar from '../assets/user.png'
import kefu from '../assets/kefu.png'
import phone from '../assets/phone.png'
import rebot from '../assets/rebot.png'
import { onMounted } from 'vue'
const data =reactive({
  message:[]
});
const turnServer=reactive({
  regionId:''
})
const showPop = ref(false)
const popupRef = ref(null)
// const listUser = []
const { ctx } = getCurrentInstance();
const tel = ref('')
onMounted(() => {


  const ws = new WebSocket('ws://localhost:8891/call?userId=yujiangjun')
  ws.onopen = function () {
  }
  ws.onerror = function (e) {
    console.log(e)
  }
  ws.onmessage = function (e) {
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
/* 
 const addData=()=>{
  console.log('开始拨打电话')
  // window.workbench.call('18521342762')
  popupRef.value.openPopup()
}
*/
/**
 * 获取获取云呼提供的turn服务接入点
 */
async function getTurnServerList(){
  let data={
    instanceId:"aiHelpAgent"
  }
  let resp = await axios.post('aliyun/ccc/getTurnServerList',data)
  // console.log(ctx.$axios)
  console.log("获取获取云呼提供的turn服务接入点")
  let list = JSON.parse(resp.data)
  turnServer.regionId=list[0].region
  console.log(turnServer.regionId)
}

/**
 * 查询坐席的技能组信息列表
 */
// async function listSkillLevelsOfUser(){
//   let data={
//     instanceId:"aiHelpAgent",
//     pageNumber:"1",
//     pageSize:"10"
//   }
//   let resp = await axios.post('aliyun/ccc/listSkillLevelsOfUser',data)
//   // listUser.users=resp.data.list
//   console.log("查询坐席的技能组信息列表")
  
//   console.log(resp.data.list)

//   listUser=resp.data.list
//   console.log(listUser)
//   return resp.data.list
// }

async function callPhone(){
  console.log(tel.value)
  window.workbench.call(tel.value)
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
            <div class="message received-message" v-if="item.role===1">
              <img :src="userAvatar" alt="接收者头像" />
              <div class="message-boarder-send">
                <div class="message-text">{{ item.message }}</div>
              </div>
            </div>
            <div style="height: 10px;"></div>
            <div class="message sent-message" v-if="item.role===2">
              <div class="message-boarder-rece">
                <div class="message-text">{{ item.message }}</div>
              </div>
              <img :src="kefu" alt="发送者头像" />
            </div>
            <div style="height: 10px;"></div>
          </div>
          
        </div>
      </div>
      <!-- <button @click="addData">开始拨打</button> -->
    </div>
    <div class="ai">
      <div class="chat-window">
        <div class="ai-header">
          <p style="font-size: 25px;">AI助理</p>
        </div>
        <div class="user-question">
          <img :src="userAvatar" alt="接收者头像" class="ai-user-avatar" />
          <div><span style="">客户问题点：</span><span style="font-size: 20px;font-weight: 700;color:#00877f">证据</span></div>
        </div>
        <div class="ai-create-header">
          <img :src="rebot">
            <p><span style="font-weight: 700;">AI 为您推荐一下三种话术</span></p>
        </div>
        <div class="ai-create-content1">
          <div class="content1-header">
            强调高风险账号逾期后果:移交总部监管并追责
          </div>
          <div class="content1-body">
            一旦标识为高风险账户，意味着您的案件会由总部直接运营直接监管，稍后也会召开线上风控会议，出具一份详细的“快递追责名单“，对于剩余本金大的或者还款期数段的都会同步更新至其他。
          </div>
          <div class="content1-law">
              <div class="law-name">
                《民法通则》第84条
              </div>
              <div class="law-content">
                债权人有权要求债务人按照合同约定或者依照法律的规定履行义务
              </div>
          </div>
        </div>

        <div class="ai-create-content2">
          <div class="content2-header">
            强调高风险账号逾期后果:移交总部监管并追责
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- <Popup v-model:visible="showPop" :animation="true" :auto-index="true" ref="popupRef" >
      <div>
          电话:<input type="number" v-model="tel"/>
         <button @click="callPhone">拨打 </button>
         页面
      </div>
   </Popup> -->
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
}
.ai{
  margin-left: 200px;
}
.user-question{
  width: 100%;
  height: 60px;
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
  background-color: antiquewhite;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>

