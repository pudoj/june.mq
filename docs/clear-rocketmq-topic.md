## 清空rocketmq消息方法

1. 停止rocketmq

可以使用kill -9 命令强制停止rocketmq进程

2. 删除rocketmq日志

```sh
rm -rf /root/logs/rocketmqlogs/*

```

3. 删除rocketmq中topic等信息

```sh
rm -rf /root/store/*

```

4. [可选] 手工清空MQ里剩余的topic，示例命令如下：

```sh
cd alibaba-rocketmq/bin
sh mqadmin topicList -n 192.168.1.167:9876
sh mqadmin deleteTopic -c HOSTNAME -n 192.168.1.167:9876 -t orderTopic
```

5. 重新启动rocketmq，示例命令如下：

```sh
cd alibaba-rocketmq/bin
nohup sh mqnamesrv -n 192.168.1.167:9876 > /dev/null 2>&1 &
nohup sh mqbroker -n 192.168.1.167:9876 > /dev/null 2>&1 &

```

**PS: **

1. 顺序必须是先停止rocketmq,再清空数据；如果顺序错误，可能会导致消息无法正常清除、rocketmq无法启动等问题

2. 删除Topic不是必须的，但是删除的时候需要指定 -c，可以用服务器的hostname试试