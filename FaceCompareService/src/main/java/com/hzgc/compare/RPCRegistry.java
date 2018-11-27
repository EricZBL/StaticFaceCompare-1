package com.hzgc.compare;

import com.hzgc.common.rpc.server.RpcServer;
import com.hzgc.common.rpc.server.zk.ServiceRegistry;
import com.hzgc.common.rpc.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RPCRegistry implements Runnable{
    private String servicePathOnZk;
    private ServiceRegistry registry;
    private int port;

    public RPCRegistry(int serviceId, String port){
        this.servicePathOnZk = "worker" + serviceId;
        this.port = Integer.parseInt(port);
        Constant constant = new Constant(Config.SERVICE_PATH, servicePathOnZk, CreateMode.EPHEMERAL);
        Map<String, String> param = new HashMap<>();
        param.put("port", port);
        constant.setParam(param);
        constant.setExitIfFaild(true);
        registry = new ServiceRegistry(Config.ZK_ADDRESS, constant);
        log.info("To Create node on zookeeper , node name " + servicePathOnZk + " , port " + port);
    }

    @Override
    public void run() {
        log.info("Registry the service.");
        if(Config.ZK_ADDRESS == null){
            log.error("Get local ip address faild .");
            System.exit(1);
        }
        RpcServer rpcServer = new RpcServer(Config.WORKER_ADDRESS,
                port, registry);
        rpcServer.start();
        System.out.println("To create zookeeper node : " + servicePathOnZk);
    }

    /**
     * 检查是否注册成功
     * @return
     */
    public boolean checkJob(){
        List<String> children;
        try {
            children = registry.getConnect().getChildren().forPath(Config.SERVICE_PATH);
            log.info("The FaceCompareWorker on Zookeeper : " + children.toString());
            return children.contains(servicePathOnZk);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
