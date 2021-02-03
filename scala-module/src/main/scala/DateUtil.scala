import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.{Date, Locale}

import org.apache.commons.lang3.time.FastDateFormat

/**
  * 日期处理工具类
  */
object DateUtil {
 
  //输入文件日期时间格式
  // 18/Nov/2018:00:00:17 +0000
  val YYYYMMDDHHMM_TIME = FastDateFormat.getInstance("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)
 
  //目标日期格式
  val TARGET = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")

  val YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
 
 
  /**
    * 获取时间：yyyy-MM-dd HH:mm:ss
    */
  def parse(time: String) = {
    TARGET.format(new Date(getTime(time)))
  }
 
  /**
    * 获取输入日志时间：long类型
    *
    * time: [18/Nov/2018:00:00:17 +0000]
    */
  def getTime(time: String) = {
    try {
      YYYYMMDDHHMM_TIME.parse(time).getTime
    } catch {
      case e: Exception => {
        e.printStackTrace()
        0l
      }
    }
  }

  def now() = {
    LocalDateTime.now().format(YYYYMMDDHHMMSS)
  }
 
}