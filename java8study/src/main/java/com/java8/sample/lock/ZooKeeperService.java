///**
// *******************************************************************************
// *                       Continental Confidential
// *                  Copyright (c) Continental, AG. 2016
// *
// *      This software is furnished under license and may be used or
// *      copied only in accordance with the terms of such license.
// *******************************************************************************
// * @file   ZooKeeperService.java
// * @brief  Provide function for connect, close, or delete node with zookeep server.
// *******************************************************************************
// */
//
//package com.ygomi.common.lock;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
//import com.ygomi.common.lock.ProcessNode.ProcessNodeWatcher;
//
//public class ZooKeeperService {
//
//    private ZooKeeper zooKeeper;
//
//    public ZooKeeperService(final String url, final ProcessNodeWatcher processNodeWatcher) throws IOException {
//        zooKeeper = new ZooKeeper(url, 3000, processNodeWatcher);
//    }
//
//    /**
//     * Create node.
//     * */
//    public String createNode(final String node, final boolean watch, final boolean ephimeral) {
//        String createdNodePath;
//        try {
//
//            final Stat nodeStat = zooKeeper.exists(node, watch);
//
//            if (nodeStat == null) {
//                createdNodePath = zooKeeper.create(node, new byte[0], Ids.OPEN_ACL_UNSAFE,
//                        (ephimeral ? CreateMode.EPHEMERAL_SEQUENTIAL : CreateMode.PERSISTENT));
//            } else {
//                createdNodePath = node;
//            }
//
//        } catch (KeeperException | InterruptedException e) {
//            throw new IllegalStateException(e);
//        }
//
//        return createdNodePath;
//    }
//
//    public void delete(String path) throws KeeperException, InterruptedException {
//        if (!exist(path)) {
//            return;
//        }
//        if (zooKeeper != null) {
//            zooKeeper.delete(path, -1);
//        }
//    }
//
//    public void close() throws KeeperException, InterruptedException {
//        if (zooKeeper != null) {
//            zooKeeper.close();
//        }
//
//    }
//
//    public boolean exist(String path) throws KeeperException, InterruptedException {
//        if (zooKeeper != null) {
//            return zooKeeper.exists(path, true) != null;
//        }
//        return false;
//    }
//
//    /**
//     * Watch node.
//     * */
//    public boolean watchNode(final String node, final boolean watch) {
//        boolean watched = false;
//        try {
//            final Stat nodeStat = zooKeeper.exists(node, watch);
//
//            if (nodeStat != null) {
//                watched = true;
//            }
//
//        } catch (KeeperException | InterruptedException e) {
//            throw new IllegalStateException(e);
//        }
//
//        return watched;
//    }
//
//    public List<String> getChildren(final String node, final boolean watch) {
//
//        List<String> childNodes;
//
//        try {
//            childNodes = zooKeeper.getChildren(node, watch);
//        } catch (KeeperException | InterruptedException e) {
//            throw new IllegalStateException(e);
//        }
//
//        return childNodes;
//    }
//
//}
