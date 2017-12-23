### 配置文件说明

- log4j.properties 日志配置文件

- iceBoxConfig.properties IceBox配置加载文件，在初始化IceBox时加载
- iceBoxRegistryConfig.porperties IceGridRegistry配置加载文件，在初始化IceGridRegistry时加载
- iceRegistry.cfg IceGridRegistry服务配置文件
- nodeX.cfg IceGridNode节点配置文件
- iceGridNode.xml iceGridNode配置文件
- iceStormBox.prpoerties 启动IceBox配置文件
- iceStormBoxConfig.properties IceBox中加载IceStorm配置
    
### 常用命令
- icebox启动
    - icebox --Ice.Config=iceStormBox.properties
- icegridregistry启动
    - icegridregistry --Ice.Config=iceRegistry.cfg
- icegridnode启动
    - icegridnode --Ice.Config=nodeX.cfg
- icegridadmin启动
    - icegridadmin -u test -p test --Ice.Default.Locator="IceGrid/Locator:tcp -h localhost -p 4061"
- icegridadmin加载icegridnode配置
    - application add iceGridNode.xml