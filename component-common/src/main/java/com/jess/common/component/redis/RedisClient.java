package com.jess.common.component.redis;

import com.jess.common.util.ExceptionUtil;
import com.jess.common.util.JSONUtil;
import com.jess.common.util.LogUtil;
import com.jess.common.util.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>ClassName: RedisService</p>
 * <p>Description: RedisTemplate常用方法类</p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年11月18日</p>
 */
@Service
public class RedisClient {

	public RedisClient() {
		super();
	}

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	/**
	 * 
	 * <p>Title: 新增POJO对象</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean set(final String key, final T value) {
		return set(key, JSONUtil.toJson(value));
	}

	/**
	 * 
	 * <p>Title: 新增POJO对象带过期时间</p> 
	 * <p>Description: 单位秒</p> 
	 * @param key
	 * @param value
	 * @param timeout 秒
	 * @return
	 */
	public <T> boolean set(String key, T value, long timeout) {
		return set(key, JSONUtil.toJson(value), timeout);
	}

	/**
	 * 
	 * <p>Title: 从缓存取出一个POJO类</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param clz
	 * @return
	 */
	public <T> T get(final String key, Class<T> clz) {
		return JSONUtil.toBean(get(key), clz);
	}

	/**
	 * 
	 * <p>Title: 新增字符串</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final String value) {
		checkRedisTemplate();
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				return true;
			}
		});

		return result;
	}

	/**
	 * 
	 * <p>Title: 新增字符串带过期时间</p> 
	 * <p>Description: 单位秒</p> 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean set(String key, String value, long timeout) {
		checkRedisTemplate();
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				return true;
			}
		});

		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		return result;
	}

	/**
	 * 
	 * <p>Title: 获取字符串</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		checkRedisTemplate();
		String result = redisTemplate.execute(new RedisCallback<String>() {

			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] value = connection.get(serializer.serialize(key));
				return serializer.deserialize(value);
			}
		});
		return result;
	}

	/**
	 * 
	 * <p>Title: 判断是否存在</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @return
	 */
	public boolean isExists(final String key) {
		checkRedisTemplate();
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				return connection.exists(serializer.serialize(key));
			}
		});
	}

	/**
	 * 
	 * <p>Title: 指定节点过期时间</p> 
	 * <p>Description: 单位 秒</p> 
	 * @param key
	 * @param expire
	 * @return
	 */
	public boolean expire(final String key, long expire) {
		checkRedisTemplate();
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * <p>Title: 删除一个节点</p> 
	 * <p>Description: </p> 
	 * @param key
	 */
	public void evict(String key) {
		checkRedisTemplate();
		redisTemplate.delete(key);
	}

	/**
	 * 
	 * <p>Title: 删除节点</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @return 是否删除成功
	 */
	public boolean delete(String key) {
		try {
			evict(key);
			return true;
		} catch (Exception e) {
			LogUtil.error(() -> e.getMessage() + "===>>>" + e.getCause());
			return false;
		}
	}

	/**
	 * 
	 * <p>Title: 查询数据库节点数</p> 
	 * <p>Description: </p> 
	 * @return
	 */
	public Long dbSize() {
		checkRedisTemplate();
		return redisTemplate.execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});

	}

	/**
	 * 
	 * <p>Title: 清空数据</p> 
	 * <p>Description: </p> 
	 * @return
	 */
	public Boolean flushDB() {
		checkRedisTemplate();
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
	}

	/**
	 * 
	 * <p>Title: 存入列表</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> boolean setList(String key, List<T> list) {
		String value = JSONUtil.toJson(list);
		return set(key, value);
	}

	/**
	 * 
	 * <p>Title: 获取列表</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param clz
	 * @return
	 */
	public <T> List<T> getList(String key, Class<T> clz) {
		List<T> list = new ArrayList<>();
		String json = get(key);
		if (json != null) {
			list = JSONUtil.toList(json, clz);
		}
		return list;
	}

	/**
	 * 
	 * <p>Title: 清空所有缓存</p> 
	 * <p>Description: </p> 
	 * @return
	 */
	public Boolean flushAll() {
		checkRedisTemplate();
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushAll();
				return true;
			}
		});
	}

	// --操作hash-------------------------------
	/**
	 * 
	 * <p>Title: 将一个对象放入hash结构中</p> 
	 * <p>Description: </p> 
	 * @param key	redis节点标识
	 * @param feild	主键值（或者联合主键拼接的字符串）
	 * @param //value		对象
	 * @param timeout	超时时间 ，单位：秒
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> void hset(String key, String feild, T o, long timeout) {
		checkRedisTemplate();
		try {
			if (o != null) {
				@SuppressWarnings("rawtypes")
				BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(key);
				boundHashOperations.put(feild, JSONUtil.toJson(o));
				boundHashOperations.expire(timeout, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
	}

	/** 
	 * @Title 检查redis连接情况
	 * @Description   
	 */
	public void checkRedisTemplate() {
		if (redisTemplate == null) {
			LogUtil.warn(() -> "redis连接断开");
			//RuleManager.hystrix(CommonRule.REDIS_CONN_DOWN);
		}
	}

	/**
	 * 
	 * <p>Title: 将一个对象放入hash结构中，没有过期期限</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param feild
	 * @param object
	 */
	public <T> void hSetNoExpire(String key, String feild, T object) {
		checkRedisTemplate();
		try {
			BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
			boundHashOperations.put(feild, JSONUtil.toJson(object));
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
	}

	/** 
	 * 将map写入缓存 
	 * @param key 
	 * @param map 有主键作为feildkey的map （包含多个对象）
	 * @param
	 */
	public <T> void hSetMap(String key, Map<String, T> map) {
		checkRedisTemplate();
		try {
			Map<String, String> newMap = new HashMap<>();
			for (Map.Entry<String, T> entry : map.entrySet()) {
				newMap.put(entry.getKey(), JSONUtil.toJson(entry.getValue()));
			}
			redisTemplate.opsForHash().putAll(key, newMap);
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
	}

	/**
	 * 
	 * <p>Title: 存入数据库查询的结果集</p> 
	 * <p>Description: </p> 
	 * @param key	    缓存节点名称
	 * @param pojos	    对象列表
	 * @param pkNames 主键（包括联合主键）字段名称 （在数据库的中存储方式是 pkValue1-pkValue2-...）
	 */
	public <T> void hSetListPOJO(String key, List<T> pojos, String... pkNames) {
		// 创建联合主键
		Map<String, T> map = pojos.stream().collect(Collectors.toMap(t -> {
			LinkedList<String> lls = new LinkedList<>();
			for (String pk : pkNames) {
				lls.add(String.valueOf(ReflectionUtils.getFieldValue(t, pk)));
			}
			return StringUtils.join(lls, "-");
		}, f -> f));
		hSetMap(key, map);
	}

	/**
	 * 
	 * @Title <p>Title: 将POJO列表放入文件夹下的hash结构中</p> 
	 * @Description <p>Description: </p> 
	 * @param folderName 文件夹名
	 * @param key 主键
	 * @param pojos POJO列表
	 * @param pkNames 主键名称 String类型 比如 "uid"
	 */
	public <T> void hSetListPOJOToFolder(String folderName, String key, List<T> pojos, String... pkNames) {
		hSetListPOJO(folderName + ":" + key, pojos, pkNames);
	}

	/** 
	 * 根据hashkeys列表获取缓存列表 
	 *  
	 * @param key 		主节点标识
	 * @param feilds 	建议使用ArrayList<String> 类型
	 * @param targetClass 对象类型
	 * @return 
	 */
	public <T> List<T> hGetListPOJO(String key, Collection<String> feilds, Class<T> targetClass) {
		checkRedisTemplate();
		try {
			HashOperations<String, String, String> opt = redisTemplate.opsForHash();
			List<String> jsonList = opt.multiGet(key, feilds);
			if (jsonList != null && !jsonList.isEmpty()) {
				return JSONUtil.toList(jsonList.toString(), targetClass);
			}
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
		return new ArrayList<>();
	}

	/**
	 * 
	 * @Title <p>Title: 根据主键值数组获取hash缓存内容</p> 
	 * @param key 主节点标识
	 * @param targetClass
	 * @param pkValues 主键字段值 feilds => pkValues 
	 * @return
	 */
	public <T> List<T> hGetListPOJO(String key, Class<T> targetClass, String... pkValues) {
		return hGetListPOJO(key, Arrays.asList(pkValues), targetClass);
	}

	/**
	 * 
	 * @Title <p>Title: 根据文件夹名、主键、目标对象类型 查询所有列表信息</p> 
	 * @Description <p>Description: </p> 
	 * @param folderName 文件夹名
	 * @param key 主键
	 * @param targetClass 目标类
	 * @return 
	 */
	public <T> List<T> hGetAHashListPOJOInFolder(String folderName, String key, Class<T> targetClass) {
		checkRedisTemplate();
		BoundHashOperations<String, String, String> boundHashOperations = redisTemplate
				.boundHashOps(folderName + ":" + key);
		Map<String, String> jsonMaps = boundHashOperations.entries();
		List<T> pojos = new ArrayList<>();
		for (Map.Entry<String, String> entry : jsonMaps.entrySet()) {
			pojos.add(JSONUtil.toBean(entry.getValue(), targetClass));
		}
		return pojos;
	}

	/**
	 * 
	 * @Title <p>Title: 根据文件夹名称、主键值获取hash缓存内容</p> 
	 * @Description <p>Description: </p> 
	 * @param folderName 文件夹名
	 * @param key 主键名
	 * @param targetClass 目标POJO数据类型
	 * @param pkValues 主键值
	 * @return 目标POJO格式的列表内容
	 */
	public <T> List<T> hGetListPOJOFromFolder(String folderName, String key, Class<T> targetClass, String... pkValues) {
		if (pkValues == null) {
			return hGetAHashListPOJOInFolder(folderName, key, targetClass);
		} else {
			return hGetListPOJO(folderName + ":" + key, targetClass, pkValues);
		}
	}

	/** 
	 * 向key对应的map中添加缓存对象 
	 * @param key   cache对象key 
	 * @param field map对应的key 
	 * @param obj   对象 
	 */
	public <T> void hAddMap(String key, String field, T obj) {
		try {
			redisTemplate.opsForHash().put(key, field, JSONUtil.toJson(obj));
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
	}

	/** 
	 * 获取map缓存 (所有数据)
	 * @param key 
	 * @return 
	 */
	public Map<String, String> hGetAll(String key) {
		checkRedisTemplate();
		try {
			BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
			return boundHashOperations.entries();
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
			return null;
		}
	}

	/** 
	 * 获取map缓存 
	 * @param key 
	 * @param clazz 
	 * @return 
	 */
	public <T> T hGet(String key, String feild, Class<T> clazz) {
		try {
			Map<String, String> map = hGetAll(key);
			return JSONUtil.toBean(map.get(feild), clazz);
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
			return null;
		}
	}

	/** 
	 * 删除map中的某个对象 
	 * @date 2016年8月10日 
	 * @param key   map对应的key 
	 * @param field map中该对象的key 
	 */
	public void delMapField(String key, String... field) {
		checkRedisTemplate();
		try {
			BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
			boundHashOperations.delete(field);
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
	}

	/** 
	 * 模糊匹配删除redis某些key的缓存 
	 *  
	 * @param fuzzyRedisKey 
	 */
	public void deleteFuzzy(String fuzzyRedisKey) {
		checkRedisTemplate();
		try {
			redisTemplate.delete(redisTemplate.keys(fuzzyRedisKey));
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
		}
	}

	/**
	 * 
	 * <p>Title: 自增</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param feild
	 * @param delta 自增长度
	 * @return
	 */
	public Long increment(String key, String feild, long delta) {
		checkRedisTemplate();
		return redisTemplate.opsForHash().increment(key, feild, delta);
	}

	/** 
	 * 重置redis的过期时间 
	 * @param key 
	 * @return 
	 */
	public boolean resetExpireTime(String key, long timeout, TimeUnit unit) {
		checkRedisTemplate();
		try {
			return redisTemplate.boundValueOps(key).expire(timeout, unit);
		} catch (Exception e) {
			ExceptionUtil.throwAndLogErr(e);
			return false;
		}
	}

}