
import redis.clients.jedis.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class jedis_cluster_sample {
    public static void main(String[] args){

        String host1 = "172.30.5.186";
        String host2 = "172.30.5.167";
        int port1 = 1521;
        int port3 = 3950;
        int port2 = 6379;
//        String pw = "foobared";
        int timeout = 2000;
        int redirects = 2000;


        //Jedis Cluster will attempt to discover cluster nodes automatically
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            jedisClusterNodes.add(new HostAndPort(host1, port1));
            jedisClusterNodes.add(new HostAndPort(host2, port1));
            jedisClusterNodes.add(new HostAndPort(host1, port2));
            jedisClusterNodes.add(new HostAndPort(host2, port2));
            jedisClusterNodes.add(new HostAndPort(host1, port3));
            jedisClusterNodes.add(new HostAndPort(host2, port3));

//        JedisClusterInfoCache
//        public JedisClusterInfoCache(org.apache.commons.pool2.impl.GenericObjectPoolConfig, int timeout)
        // cluster를 사용하려면 commons-pool2-2.4.2.jar를 포함해야 한다. 그렇지 않으면 class not found 에러가 난다.(포함)
        JedisCluster cluster = new JedisCluster(jedisClusterNodes, timeout, redirects);

//        cluster.set("key_jedis", "2022-08-08");
//        String value = cluster.get("key_jedis");
//        System.out.println(value);

        for(int i = 0; i < 100; i++){
            LocalDateTime now = LocalDateTime.now();
            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss"));
            try{
                cluster.set("Jedis"+i, formatedNow);
                Thread.sleep(2000);
                System.out.println(i + " " +cluster.get("Jedis" + i));

            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}


