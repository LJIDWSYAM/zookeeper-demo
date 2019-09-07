package com.atcdu.zookeeper;

import org.apache.zookeeper.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liujun
 * \* Date: 2019/8/21
 * \* Time: 15:50
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class TestZookeeper {
    private String connectString="120.27.251.36:2181,120.27.251.36:2182,120.27.251.36:2183";
    private int sessionTimeout=10000;
    private ZooKeeper zooKeeper;
    @Before
    public void init() throws IOException {
           zooKeeper=new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("---------------start------------------------");
                List<String> children= null;
                try {
                    children = zooKeeper.getChildren("/",true );
                    for (String child:children){
                        System.out.println(child);
                    }
                    System.out.println("---------------end------------------------");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Test
    public void createNode() throws KeeperException, InterruptedException {

        String path=zooKeeper.create("/atcdu", "liujun".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }
    @Test
    public void getDataAndWatch() throws KeeperException, InterruptedException {
        List<String> children=zooKeeper.getChildren("/",true );
        for (String child:children){
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}