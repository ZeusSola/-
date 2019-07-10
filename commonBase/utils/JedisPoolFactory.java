package com.yuchen.catalog.common.utils;

import com.yuchen.catalog.common.config.Global;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolFactory {
  public static JedisPool pool;

  static{
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(10);
    pool = new JedisPool(jedisPoolConfig, Global.getConfig("redis.host"));
  }

}
