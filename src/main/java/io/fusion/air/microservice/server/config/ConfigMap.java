/**
 * (C) Copyright 2021 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice.server.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Config Map
 *
 * @author arafkarsh
 *
 */
public class ConfigMap {

    private String apiDocPath;
    private String serviceName;
    private int buildNumber;
    private String buildDate;
    private String serverVersion;
    private int serverPort;
    private String remoteHost;
    private int remotePort;
    private String springCodecMaxMemory;
    private ArrayList<String> appPropertyList;
    private HashMap<String, String> appPropertyMap;

    public ConfigMap() {
    }

    public ConfigMap(String apiDocPath, String serviceName, int buildNumber, String buildDate,
                     String serverVersion, int serverPort, String remoteHost, int remotePort,
                     String springCodecMaxMemory, ArrayList<String> appPropertyList,
                     HashMap<String, String> appPropertyMap) {
        this.apiDocPath = apiDocPath;
        this.serviceName = serviceName;
        this.buildNumber = buildNumber;
        this.buildDate = buildDate;
        this.serverVersion = serverVersion;
        this.serverPort = serverPort;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.springCodecMaxMemory = springCodecMaxMemory;
        this.appPropertyList = appPropertyList;
        this.appPropertyMap = appPropertyMap;
    }

    public String getApiDocPath() {
        return apiDocPath;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public String getSpringCodecMaxMemory() {
        return springCodecMaxMemory;
    }

    public ArrayList<String> getAppPropertyList() {
        return appPropertyList;
    }

    public HashMap<String, String> getAppPropertyMap() {
        return appPropertyMap;
    }

    @Override
    public String toString() {
        return "ConfigMap{" +
                "serviceName='" + serviceName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigMap configMap = (ConfigMap) o;
        return serviceName.equals(configMap.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName);
    }
}
