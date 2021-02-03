import java.sql.{Connection, DriverManager, Statement}

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 页面 浏览量（pv）指标统计
  */
object PageVisitJob {

  /**
    * 页面访问域名
    */
  val PURCHASE_URL = "http://www.baidu.com"

  /**
    * 时间大小(秒)
    */
  val TIME_WINDOW = 30

  /**
    * 消息队列地址配置
    */
  val KAFKA_ADDRESS = ""

  /**
    * 消息队列主题
    */
  val KAFKA_TOPIC = "group-test-topic"

  /**
    * 消息队列分组ID
    */
  val KAFKA_GROUP_ID = "test1"

  /**
    * 每次访问PV数
    */
  val EVERY_PV = 1

  def main(args: Array[String]): Unit = {
    // 创建 Spark Conf
    val conf = new SparkConf().setMaster("local[2]").setAppName("NumberOfPageVisitFromKafkaJob")
    // 定义时间窗口大小
    val streamingContext = new StreamingContext(conf, Seconds(TIME_WINDOW))
    // 创建 Streaming 流
    val recordDStream = createKafkaRecordDStreamFromStreamingContext(streamingContext)
    // 处理 Record Dstream
    recordDStream.foreachRDD(computeRecordRDD(_))
    // 启动进程
    streamingContext.start()
    // 停止进程
    streamingContext.awaitTermination()
  }

  /**
    * 通过流上下文创建 Kafka Record DStream
    *
    * @param streamingContext
    * @return
    */
  def createKafkaRecordDStreamFromStreamingContext(streamingContext: StreamingContext) = {
    // 定义 Kafka Topic
    val topics = Array(KAFKA_TOPIC)
    // 定义 Kafka 参数
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> KAFKA_ADDRESS,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> KAFKA_GROUP_ID,
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )
    // 创建直接连接流
    KafkaUtils.createDirectStream[String, String](
      streamingContext,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )
  }

  /**
    * 处理时间窗口内 Record RDD
    *
    * @param recordRDD
    */
  def computeRecordRDD(recordRDD: RDD[ConsumerRecord[String, String]]) {
    recordRDD.foreach(record => {
      println(record.value())
      // 解析数据
      val recordJson = JsonUtil.gson(record.value())
      val referer = recordJson.get(Fields.referer).getAsString
      val timeLocal = recordJson.get(Fields.time_local).getAsString
      val remoteAddress = recordJson.get(Fields.r_addr).getAsString
      // 采购需求域名PV数据落库
      if (!referer.isEmpty && referer.contains(PURCHASE_URL)) {
        saveDataToPageVisit(remoteAddress, DateUtil.parse(timeLocal), PURCHASE_URL, EVERY_PV)
      }
    })
  }

  /**
    * 存储到MySQL数据库
    *
    * @param ip
    * @param date
    * @param url
    * @param pv
    */
  def saveDataToPageVisit(ip: String, date: String, url: String, pv: Int) {
    val sql = ""
    var connection: Connection = null
    var statement: Statement = null
    try {
      connection = createConnection()
      statement = connection.createStatement()
      statement.execute(sql)
    } catch {
      case t: Throwable => t.printStackTrace()
    } finally {
      try {
        if (null != connection) {
          connection.close()
        }
        if (null != statement) {
          statement.close()
        }
      } finally {
        connection = null
        statement = null
      }
    }
  }

  /**
    * 获取MySQL数据库连接
    *
    * @return
    */
  def createConnection() = {
    val url = "jdbc:mysql://xxx.xxx.xxx.xxx:xxxx/xxx?characterEncoding=utf8&useSSL=true"
    val user = "xxx"
    val password = "xxx"
    DriverManager.getConnection(url, user, password)
  }
}