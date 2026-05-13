package com.example;

import redis.clients.jedis.Jedis;

public class Exercicio1 {

    public static void main(String[] args) {

        Jedis redis = new Jedis("localhost", 6379);

        Long valor = redis.incr("programa:execucoes");

        System.out.println("Execuções: " + valor);
        
        redis.close();
    }
}