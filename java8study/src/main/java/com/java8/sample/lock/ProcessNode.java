///**
// *******************************************************************************
// *                       Continental Confidential
// *                  Copyright (c) Continental, AG. 2016
// *
// *      This software is furnished under license and may be used or
// *      copied only in accordance with the terms of such license.
// *******************************************************************************
// * @file   ProcessNode.java
// * @brief  Provide function for check lock result.
// *******************************************************************************
// */
//
//
//package com.ygomi.common.lock;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.Callable;
//
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.Watcher.Event.EventType;
//
//public class ProcessNode implements Callable<String> {
////
////    private String leaderElectionRootNode;
////    private static final String PROCESS_NODE_PREFIX = "/p_";
////
////    private long timeout = Long.MAX_VALUE;
////
////    // private static final int TIMEDURATION = 3*1000;
////
////    // private String id = " [ "+Thread.currentThread().getName()+" ] ";
////
////    private final ZooKeeperService zooKeeperService;
////
////    private String processNodePath;
////    private String watchedNodePath;
////
////    // public ProcessNode(final int id, final String zkURL) throws IOException {
////    // zooKeeperService = new ZooKeeperService(zkURL, new ProcessNodeWatcher());
////    // }
////    //
////
////    // public ProcessNode(final String zkURL) throws IOException {
////    // zooKeeperService = new ZooKeeperService(zkURL, new ProcessNodeWatcher());
////    // }
////
////    public ProcessNode(final String zkUrl, String lock) throws IOException {
////        this.leaderElectionRootNode = lock;
////        zooKeeperService = new ZooKeeperService(zkUrl, new ProcessNodeWatcher());
////    }
////
////    public ProcessNode(final String zkUrl, String lock, long timeout) throws IOException {
////        this.leaderElectionRootNode = lock;
////        this.timeout = timeout;
////        zooKeeperService = new ZooKeeperService(zkUrl, new ProcessNodeWatcher());
////    }
////
////    private boolean attemptForLeaderPosition() {
////
////        final List<String> childNodePaths = zooKeeperService.getChildren(leaderElectionRootNode, false);
////
////        Collections.sort(childNodePaths);
////
////        int index = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf('/') + 1));
////        if (index == 0) {
////            // logger.debug("[Process: " + id + "] I am the new leader!");
////            return true;
////        } else {
////            final String watchedNodeShortPath = childNodePaths.get(index - 1);
////
////            watchedNodePath = leaderElectionRootNode + "/" + watchedNodeShortPath;
////
////            // logger.debug("[Process: " + id + "] - Setting watch on node with
////            // path: " + watchedNodePath);
////            zooKeeperService.watchNode(watchedNodePath, true);
////        }
////        return false;
////    }
////
////    /**
////     * Lock.
////     * */
////    public boolean trylock() {
////        // logger.info("Process with id: " + id + " has started!");
////        final String rootNodePath = zooKeeperService.createNode(leaderElectionRootNode, false, false);
////        if (rootNodePath == null) {
////            throw new IllegalStateException(
////                    "Unable to create/access leader election root node with path: " + leaderElectionRootNode);
////        }
////
////        processNodePath = zooKeeperService.createNode(rootNodePath + PROCESS_NODE_PREFIX, false, true);
////        if (processNodePath == null) {
////            throw new IllegalStateException(
////                    "Unable to create/access process node with path: " + leaderElectionRootNode);
////        }
////        // logger.debug("[Process: " + id + "] Process node created with path: "
////        // + processNodePath);
////
////        return attemptForLeaderPosition();
////
////    }
////
////    /**
////     * Create node and watch.
////     * */
////    public boolean trylockNum() {
////        final String rootNodePath = zooKeeperService.createNode(leaderElectionRootNode, false, false);
////        if (rootNodePath == null) {
////            throw new IllegalStateException(
////                    "Unable to create/access leader election root node with path: " + leaderElectionRootNode);
////        }
////
////        processNodePath = zooKeeperService.createNode(rootNodePath + PROCESS_NODE_PREFIX, false, true);
////        if (processNodePath == null) {
////            throw new IllegalStateException(
////                    "Unable to create/access process node with path: " + leaderElectionRootNode);
////        }
////
////        return attemptForLeaderPositionNum();
////    }
////
////    private boolean attemptForLeaderPositionNum() {
////
////        final List<String> childNodePaths = zooKeeperService.getChildren(leaderElectionRootNode, false);
////
////        Collections.sort(childNodePaths);
////
////        int index = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf('/') + 1));
////        if (index < 5 && index >= 0) {
////            return true;
////        } else {
////            final String watchedNodeShortPath = childNodePaths.get(index - 1);
////
////            watchedNodePath = leaderElectionRootNode + "/" + watchedNodeShortPath;
////
////            // logger.debug("[Process: " + id + "] - Setting watch on node with
////            // path: " + watchedNodePath);
////            zooKeeperService.watchNode(watchedNodePath, true);
////        }
////        return false;
////    }
////
////    public class ProcessNodeWatcher implements Watcher {
////
////        public void process(WatchedEvent event) {
////            // logger.debug("[Process: " + id + "] Event received: " + event);
////
////            final EventType eventType = event.getType();
////            if (EventType.NodeDeleted.equals(eventType)) {
////                if (event.getPath().equalsIgnoreCase(watchedNodePath)) {
////                    attemptForLeaderPosition();
////                }
////            }
////
////        }
////    }
////
////    public boolean unlock() throws KeeperException, InterruptedException {
////        zooKeeperService.delete(processNodePath);
////        return true;
////    }
////
////    public boolean close() throws KeeperException, InterruptedException {
////        zooKeeperService.close();
////        return true;
////    }
////
////    /**
////     * Create zookeeper node.
////     * */
////    public String call() throws Exception {
////        String result = null;
////        long startTime = System.currentTimeMillis();
////        // logger.info("Process with id: " + id + " has started!");
////
////        final String rootNodePath = zooKeeperService.createNode(leaderElectionRootNode, false, false);
////        if (rootNodePath == null) {
////            throw new IllegalStateException(
////                    "Unable to create/access leader election root node with path: " + leaderElectionRootNode);
////        }
////
////        processNodePath = zooKeeperService.createNode(rootNodePath + PROCESS_NODE_PREFIX, false, true);
////        if (processNodePath == null) {
////            throw new IllegalStateException(
////                    "Unable to create/access process node with path: " + leaderElectionRootNode);
////        }
////
////        // logger.debug("[Process: " + id + "] Process node created with path: "
////        // + processNodePath);
////
////        while (true) {
////            if (attemptForLeaderPosition()) {
////                result = processNodePath;
////                break;
////            } else {
////                // Thread.sleep(TIMEDURATION);
////                // logger.debug("looping wait lock");
////                if (System.currentTimeMillis() - startTime > timeout) {
////                    throw new RuntimeException("wait lock timeout");
////                }
////            }
////        }
////        return result;
////    }
//}
