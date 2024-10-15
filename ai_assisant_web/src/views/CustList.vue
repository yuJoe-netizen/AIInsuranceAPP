<template>
    <a-table :columns="columns" :data-source="data" :pagination="pageObj" @change="handleChange">
  
      <template #bodyCell="{ column }">
        <template v-if="column.key === 'action'">
          <span>
            <a>盘点</a>
            <a>电话</a>
          </span>
        </template>
      </template>
    </a-table>
  </template>
  <script setup>
import { onMounted, reactive } from 'vue';
import axios from '../axiosInstance'

  const columns = [
    {
      title: '申请号',
      dataIndex: 'applyNo',
      key: 'applyNo',
    },
    {
      title: '客户名称',
      dataIndex: 'customer',
      key: 'customer',
    },
    {
      title: '逾期天数',
      dataIndex: 'overdueDay',
      key: 'overdueDay',
    },
    {
      title: '剩余本金',
      key: 'remainingPrincipal',
      dataIndex: 'remainingPrincipal',
    },
    {
      title: '案件状态',
      key: 'caseStatus',
      dataIndex: 'caseStatus',
    },
    {
      title: '今日进展',
      key: 'todayProgress',
      dataIndex: 'todayProgress',
    },
    {
      title: '跟进要求',
      key: 'followUpRequest',
      dataIndex: 'followUpRequest',
    },
    {
      title: '执行策略',
      key: 'executionStrategy',
      dataIndex: 'executionStrategy',
    },
    {
      title: 'Action',
      key: 'action',
    },
  ];
  const data = reactive([]);
  const pageObj = reactive({
    total: 0,
    current: 1,
    pageSize: 10,
    defaultPageSize:10,
  })


  onMounted(async () => {
    console.log('mounted')
    console.log('请求后端')
    let res = await axios.post('/api/case/getCaseList',{
        pageNum:1,
        pageSize:10
    })
    console.log('res:',res.data)
    data.splice(0, data.length, ...res.data.records)
    pageObj.total = res.data.total
    pageObj.current=res.data.current
  });

  const handleChange = async (pagination, filters, sorter, { action, currentDataSource }) => {
    if (action === 'paginate') {
      pageObj.pageSize=pagination.pageSize
      pageObj.current = pagination.current
      let res = await axios.post('/api/case/getCaseList',{
        pageNum:pagination.current,
        pageSize:10
      })
      data.splice(0, data.length, ...res.data.records)
        pageObj.total = res.data.total
        pageObj.current=res.data.current
    }

    console.log('pagination:', pagination)
    console.log('filters:', filters)
    console.log('sorter:', sorter)
    console.log('action:', action)
    console.log('currentDataSource:', currentDataSource)
  }
  </script>