//Faça um programa que conecte numa base Redis® e chame as funções Ping e Echo. Utilize a linguagem Java™ com a biblioteca Jedis.

package exercicio;
 // normalmente é lettuce ou jedis. 
 import redis.clients.jedis.Jedis;
  import java.util.Set;
   public class Exercicio1 {
        public static void main(String[] args) {

            Jedis redis = new Jedis("localhost", 6379);

            for (int i = 0; i <= 10; i++) {
            redis.set("chave" + i, "valor" + i); 
            } 

            Set<String> keys = redis.keys("*");

            for (String key : keys){
                System.out.println(key + ": " + redis.get(key)); 
            } 
            
        redis.close();0}
    }
            
