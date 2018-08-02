package com.jess.commons.api.util;

import com.jess.commons.api.config.RedisConfig;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 
 * <p>ClassName: RedisUtil</p>
 * <p>Description: Redis操作类</p>
 * <p>Author: LIUYL</p>
 * <p>Date: 2017年12月23日</p>
 */
@Component
public class RedisUtil {


	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	
	@Autowired
	private RedisConfig redisConfig;

	JedisPool jedisPool = null;

	/**
	 * 
	 * <p>Description: 初始化方法，当依赖的属性都注入完成后，开始调用</p>
	 */
	@PostConstruct
	public void init() {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(200);
		config.setMaxWaitMillis(10000);
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(), redisConfig.getPassword());
	}

	

	
	/**
	 * 
	 * <p>Description: 调用key进行自增操作</p>
	 * @param key  redis中检索的key
	 * @return  key不存在，会自动创建，返回1 ，否则加1后返回
	 */
	public Long inCr(String  key) {
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.incr(key);
		} catch (Exception e) {
			if("ERR increment or decrement would overflow".equals(e.getMessage())) {
				LOGGER.error("RedisUtil.inCr() 保存key={},已经到达了最大数据范围 {}", key,Long.MAX_VALUE );
			}
			LOGGER.error("RedisUtil.inCr() 保存key={},出现异常", key, e);
			return 0L;
		} finally {
			if (jedis != null) {
				jedis.close();
			}

		}
		
	}
	
	/**
	 * 
	 * <p>Description: 执行lua脚本</p>
	 * @param script 脚本内容
	 * @param keys redis的key参数列表
	 * @param args 其他的值参列表
	 * @return 执行完lua脚本的实际返回值
	 */
	public Object evalScript(String script,List<String> keys,List<String> args) {
		Jedis jedis = null;
		
		try {
			jedis = jedisPool.getResource();
			return jedis.eval(script, keys, args);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.evalScript() keys={},args={},出现异常", JSONUtil.toJson(keys),JSONUtil.toJson(args), e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}
	/**
	 * 
	 * <p>Description: 以k-v形式保存</p>
	 * @param key redis的键
	 * @param value 需要保存的值
	 * @return 1-key不存在，保存成功 0-key存在，保存失败 -1-出现异常
	 */
	public Long setNx(String key,int value) {
		
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			return jedis.setnx(key, Integer.toString(value));
		} catch (Exception e) {
			LOGGER.error("保存key={}，出现异常", key, e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return -1L;
	}
	
	
	/**
	 * 
	 * <p>Description: 根据一个key存入一个hash结构的数据</p>
	 * @param key  redis检索的key
	 * @param hash map结构
	 * @return  true-保存成功 false-失败 
	 */
	public boolean hmset(String key, Map<String, String> hash) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, hash);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hmset保存key={}，hash={}，出现异常", key,JSONUtil.toJson(hash), e);
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}
	
	
	/**
	 * 
	 * <p>Description: 根据一个key存入一个hash结构的数据</p>
	 * @param key  redis检索的key
	 * @param
	 * @return  true-保存成功 false-失败 
	 */
	public boolean hmget(String key,String ...fields ) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.hmget(key, fields);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hmset保存key={}，hash={}，出现异常", key, e);
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}
	
	
	
	public Map<String,String> hgetAll(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hgetAll 获取hash时，key={}，出现异常", key);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * 
	 * <p>Description: 将value设置到key中</p>
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public <T> boolean set(String key,T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, JSONUtil.toJson(value));
		} catch (Exception e) {
			LOGGER.error("ReidsUtil.set方法 key={}，value={} 出现异常", key,value, e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
		
	}

	
	/**
	 * 
	 * <p>Description: 向指定key中的hash结构中存入键为field，值为value</p>
	 * @param key redis检索的key
	 * @param field 字段
	 * @param value 值
	 * @return true-成功 false-失败
	 */
	public boolean hset(String key, String field, String value) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hset() 保存key={}，filed={},出现异常", key,field, e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}

	
	/**
	 * 
	 * <p>Description: 根据redis的key检索hash结构中键为field的值</p>
	 * @param key redis的key
	 * @param field 字段名
	 * @return key对应的hash结构中 field对应的值
	 */
	public String hget(String key, String field) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			return jedis.hget(key, field);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hget() 获取key={}，field={}，出现异常:{}", key, field, e.getMessage());
			return null;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	/**
	 * 
	 * <p>Description: 判断指定key下面的hash结构中是否存键为field的条目</p>
	 * @param key redis检索的key
	 * @param field 字段
	 * @return true-存在 false-不存在
	 */
	public boolean existshKey(String key, String field) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			return jedis.hexists(key, field);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.existshKey() 判断key={}，field={} 是否存在，出现异常:{}", key, field, e.getMessage());
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	
	
	/**
	 * 
	 * <p>Description: 以hash结构存入字节数组</p>
	 * @param key redis的键
     * @param field 字段
     * @param value 值
     * @param timeout 超时时间 （秒）
     * @return key下面的总field数
     */
	public Long hsetBytes(String key, String field, byte[] value, int timeout) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			Transaction ts = jedis.multi();
			byte[] keyBytes = StringUtils.getBytesUtf8(key);
			ts.hset(keyBytes, StringUtils.getBytesUtf8(field), value);
			ts.expire(keyBytes, timeout);
			Response<Long> resp = ts.hlen(keyBytes);
			ts.exec();
			return resp.get();
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hsetBytes() 保存key={},field={}，出现异常", key, field, e);
			return 0L;
		} finally {
			if (jedis != null) {
				jedis.close();
			}

		}
	}
	
	public <T> void hsetMap(String key,  String feild, T o, int timeout) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Transaction ts = jedis.multi();
			ts.hset(key,feild, JSONUtil.toJson(o));
			ts.expire(key, timeout);
			Response<Long> resp = ts.hlen(key);
			ts.exec();
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hsetMap() 保存key={},feild={}，出现异常", key, feild, e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	/**
	 * 
	 * <p>Description: 获取key下面的字节 Map<String,byte[]>map，配合hsetBytes使用</p>
	 * @param key redis的key
	 * @return 返回 key为String , value 为 byte [] 的 map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, byte[]> hgetAllBytes(String key) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Transaction ts = jedis.multi();
			byte[] keyBytes = StringUtils.getBytesUtf8(key);
			ts.hgetAll(keyBytes);
			ts.del(keyBytes);
			List<Object> objList = ts.exec();
			
			Map<byte[], byte[]> byteMap = (Map<byte[], byte[]>) objList.get(0);
			Map<String, byte[]> stringMap = new HashMap<>();
			for (Entry<byte[], byte[]> entry : byteMap.entrySet()) {
				stringMap.put(StringUtils.newStringUtf8(entry.getKey()), entry.getValue());
			}
			return stringMap;
		} catch (Exception e) {
			LOGGER.error("RedisUtil.hgetAllBytes() 获取key={}的值出现异常", key, e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}

		}
	}

	

	/**
	 * 
	 * <p>Description: 删除key下面的hash结构中指定键的条目</p>
	 * @param key redis检索的key
	 * @param fields 需要删除的键
	 * @return true-成功 false-失败
	 */
	public boolean delhKeys(String key, String... fields) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.hdel(key, fields);
		} catch (Exception e) {
			LOGGER.error("RedisUtil.delhKeys() 删除key={}，fields={} 出现异常", key, fields,e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;

	}

	/**
	 * 
	 * <p>Description: 批量保存多个hash结构</p>
	 * @param keys 多个redis的key
	 * @param fields 每个值对应的filed集合
	 * @param values  每个值的集合
	 * @return
	 */
	public boolean batchSaveHash(List<String> keys,List<String> fields,List<String> values) {
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Transaction ts = jedis.multi();
			if(keys.size() !=fields.size() || keys.size() !=values.size()) {
				LOGGER.error("keys,fields,values三个变量集合的长度不一致，分别为{},{},{}：",keys.size(),fields.size(),values.size());
				return false;
			}
			
			for(int i=0;i<keys.size();i++) {
				ts.hset(keys.get(i), fields.get(i), values.get(i));
			}
			ts.exec();
		} catch (Exception e) {
			LOGGER.error("RedisUtil.batchSaveHash() 获取keys={},fields={},values={}的值出现异常", JSONUtil.toJson(keys), JSONUtil.toJson(fields), JSONUtil.toJson(values), e);
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}

		}
		
		return true;
	} 
	
	
	/**
	 * 
	 * <p>Description: 批量保存多个hash结构</p>
	 * @param keys 多个redis的key
	 * @param fields 每个值对应的filed集合
	 * @param
	 * @return
	 */
	public List<String> batchGetHash(List<String> keys,List<String> fields) {
		List<String> list=new ArrayList<>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Transaction ts = jedis.multi();
			if(keys.size() !=fields.size()) {
				LOGGER.error("keys,fields三个变量集合的长度不一致，分别为{},{},{}：",keys.size(),fields.size());
				return new ArrayList<>();
			}
			
			for(int i=0;i<keys.size();i++) {
				list.add(ts.hget(keys.get(i), fields.get(i)).get());
			}
			ts.exec();
		} catch (Exception e) {
			LOGGER.error("RedisUtil.batchSaveHash() 获取keys={},fields={},values={}的值出现异常", JSONUtil.toJson(keys), JSONUtil.toJson(fields), e);
			return new ArrayList<>();
		} finally {
			if (jedis != null) {
				jedis.close();
			}

		}
		
		return list;
	} 
	
	
	/**
	 * 
	 * <p>Description: 将value设置到key中</p>
	 * @param key 键
	 * @param value 值
	 * @param seconds 超时时间
	 * @return
	 */
	public <T> boolean set(String key,T value,int seconds) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.setex(key, seconds, JSONUtil.toJson(value));
		} catch (Exception e) {
			LOGGER.error("ReidsUtil.set方法 key={}，value={} 出现异常", key,value, e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
		
	}
	
	public  boolean set(String key,String value,int seconds) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.setex(key, seconds, JSONUtil.toJson(value));
		} catch (Exception e) {
			LOGGER.error("ReidsUtil.set方法 key={}，value={} 出现异常", key,value, e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
		
	}
	
	/**
	 * 
	 * <p>Description: 根据key获取值</p>
	 * @param key
	 * @return
	 */
	public <T> T get(String key,Class<T> clz) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			LOGGER.error("ReidsUtil.get方法 key={} 出现异常={}", key,e);
			return null;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return JSONUtil.toBean(value, clz);
	}
	
	/**
	 * 
	 * <p>Description: 根据key删除值</p>
	 * @param key
	 * @return
	 */
	public boolean delete(String key) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			LOGGER.error("ReidsUtil.del方法 key={} 出现异常={}", key,e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}
	
	
	/**
	 * 
	 * <p>Description: 判断是否存在</p>
	 * @param key
	 * @return
	 */
	public boolean isExists(String key) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.exists(key);
		} catch (Exception e) {
			LOGGER.error("ReidsUtil.exists方法 key={} 出现异常={}", key,e);
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}
	
	/**
	 * 
	 * <p>Description: 根据hash中key获取值</p>
	 * @param key
	 * @param feild
	 * @param clazz
	 * @return
	 */
	public <T> T hGet(String key, String feild, Class<T> clazz) {
		try {
			Map<String, String> map = hgetAll(key);
			return JSONUtil.toBean(map.get(feild), clazz);
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
			return null;
		}
	}
}