
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class jedis_cluster_sample {
    public static void main(String[] args){
        JedisCluster cluster = null;

        String host1 = "172.30.5.186";
        String host2 = "172.30.5.167";
        int port = 6379;
        String pw = "foobared";
        int timeout = 2000;
        int redirects = 2000;


        //Jedis Cluster will attempt to discover cluster nodes automatically
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            jedisClusterNodes.add(new HostAndPort(host1, port));
            jedisClusterNodes.add(new HostAndPort(host2, port));

        // cluster를 사용하려면 commons-pool2-2.4.2.jar를 포함해야 한다. 그렇지 않으면 class not found 에러가 난다.
        cluster = new JedisCluster(jedisClusterNodes, timeout, redirects );

        cluster.set("key", "Hello Jedis Cluster");
        String value = cluster.get("key");
        System.out.println(value);
    }
}


