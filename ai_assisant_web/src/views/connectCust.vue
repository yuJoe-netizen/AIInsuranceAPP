<script setup>
import { RouterLink, RouterView } from 'vue-router'
import defaultAvatar from '../assets/default_receiver_avatar.jpg'
import defaultSendAvatar from '../assets/default_sender_avatar.jpg'
import phone from '../assets/phone.png'
</script>
<template>
    <div class="chat-window">
    <div class="chat-header">
      <img :src="phone">
    </div>
    <div class="chat-messages">
      <div class="message received-message">
        <img :src="defaultAvatar" alt="接收者头像">
        <div class="message-boarder-send">
          <div class="message-text">你好，今天过得怎么样？</div>
        </div>
      </div>
      <div class="message sent-message">
        <div class="message-boarder-rece">
          <div class="message-text">还不错，你呢？</div>
        </div>
        
        <img :src="defaultSendAvatar" alt="发送者头像">
      </div>
      <!-- 更多消息 -->
    </div>
    <!-- <div class="chat-input">
      <input type="text" placeholder="输入消息...">
      <button>发送</button>
    </div> -->
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

  .chat-header img {
      margin: 0;
      width: 12%;
    }

  .chat-messages {
      height: 480px;
      padding: 10px;
      overflow-y: scroll;
      background-color: beige;
    }

  .message {
      margin-bottom: 10px;
      display: flex;
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
  .message-boarder-rece{
    border: 1px red;
    background-color: cornflowerblue;
    border-radius: 10px;
  }
  .message-boarder-send{
    border: 1px red;
    background-color: #fff;
    border-radius: 10px;
  }
  .sent-message.message-text {
      background-color: #dcf8c6;
      color: #000;
    }

  .received-message.message-text {
      background-color: #87CEEB; 
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
</style>