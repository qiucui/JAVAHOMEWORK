package 数据循环.java;
package 数据循环.java;

import com.zgd.demo.thread.ThreadPoolBuilder;
import com.zgd.demo.thread.test.MyBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/
@Author: zgd
@Date: 2019/5/8 16:30
@Description:
/
@Slf4j
public class MiaoShaTestMiaoShaTest extends 
MyBaseTestMyBaseTest {

@Autowired
private MiaoShaServiceImpl miaoShaService;
@Autowired
private MiaoShaInitServiceImpl miaoShaInitService;

@Test
public void fun01() throws InterruptedException {
//模拟商品id
long goodsId = 12345L;
log.info("初始化库存");

miaoShaInitService.initStock(goodsId, 100);
}
}

package 数据循环.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/
MiaoShaInitServiceImpl

@author zgd
@date 2019/7/30 16:59
/
@Service
public class MiaoShaInitServiceImplMiaoShaInitServiceImpl {

public final static String REDIS_STOCK_COUNT_KEY = "MIAOSHA:STOCK:GOODS_COUNT:";

//活动持续30分钟
public final static int MIAO_SHA_TIME_OUT = 60 30;

@Autowired
private JedisPool jedisPool;
public class 数据循环 {

}
