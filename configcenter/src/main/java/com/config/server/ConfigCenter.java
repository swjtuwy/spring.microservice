/**
 * ****************************************************************************
 * Continental Confidential
 * Copyright (c) Continental, AG. 2017
 * <p>
 * This software is furnished under license and may be used or
 * copied only in accordance with the terms of such license.
 * ****************************************************************************
 *
 * @file ConfigCenter.java
 * @brief The config center for services.
 * ****************************************************************************
 */

package com.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCenter {

    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter.class, args);
    }
}
