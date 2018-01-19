/**
 *******************************************************************************
 *                       Continental Confidential
 *                  Copyright (c) Continental, AG. 2016
 *
 *      This software is furnished under license and may be used or
 *      copied only in accordance with the terms of such license.
 *******************************************************************************
 * @file   DistributeLock.java
 * @brief  The distribute lock main class
 *******************************************************************************
 */

package com.ygomi.common.lock;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.zookeeper.KeeperException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributeLock {

    private static final Logger logger = LoggerFactory.getLogger(DistributeLock.class);

    // private String zkURL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private final ExecutorService service;
    private ProcessNode processNode;

    /**
     * 
     * @param zkUrl  zookeeper server address.
     */
    public DistributeLock(String zkUrl, String lock) {

        this.service = Executors.newSingleThreadExecutor();
        try {
            processNode = new ProcessNode(zkUrl, lock);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param zkUrl
     *            : zookeeper server address
     * @param timeout
     *            : get lock wait max time.
     */
    public DistributeLock(String zkUrl, String lock, long timeout) {

        this.service = Executors.newSingleThreadExecutor();
        try {
            processNode = new ProcessNode(zkUrl, lock, timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public DistributeLock(String zkURL,String lock) throws IOException{
    //
    // this.service = Executors.newSingleThreadExecutor();
    // processNode = new ProcessNode(zkURL, lock);
    // }

    public boolean lock() throws InterruptedException, ExecutionException {
        final Future<String> status = service.submit(processNode);
        try {
            logger.info("start create lock ");

            String path = status.get();

            logger.info("get lock: " + path);
            return true;
        } finally {
            service.shutdownNow();
        }
    }

    public boolean unlock() throws KeeperException, InterruptedException {
        return processNode.unlock();
    }

    public void close() throws KeeperException, InterruptedException {
        processNode.close();
    }

}
