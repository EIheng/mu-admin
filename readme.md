# mu-admin 基础权限管理系统

## 前言

前后端交互中鉴权操作是必须的，为此有很多权限认证方案。

~~本方案介绍最基础的token权限认证。~~

后端采用了`sa-token`认证框架。

## 前端

```
// 安装
pnpm install
// 运行
pnpm dev
```

## 后台

在数据库中执行sql文件夹（位于mu-admin-api）的中的`.sql`文件。

将项目使用IDEA打开，加载为Gradle项目，启动项目即可。
