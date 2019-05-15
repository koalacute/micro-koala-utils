package com.koalacute.micro.koala.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class IPUtil {

    private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

    /**
     * 获取本地IP地址
     */
    public static String getLocalIP() {
        try {
            if (isWindowsOS()) {
                return InetAddress.getLocalHost().getHostAddress();
            } else {
                return getLinuxLocalIp();
            }
        } catch (Exception e) {
            try {
                return InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e1) {
                return "127.0.0.1";
            }
        }
    }

    /**
     * 判断操作系统是否是Windows
     */
    private static boolean isWindowsOS() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return true;
        }
        return false;
    }

    /**
     * 获取Linux下的IP地址
     */
    private static String getLinuxLocalIp() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipAddress = inetAddress.getHostAddress();
                            if (!ipAddress.contains("::") && !ipAddress.contains("0:0:") && !ipAddress.contains("fe80")) {
                                ip = ipAddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            logger.error("获取ip地址异常:{}", ex.getMessage(), ex);
            ip = "127.0.0.1";
        }

        return ip;
    }
}
