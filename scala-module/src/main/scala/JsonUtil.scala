import com.google.gson.{JsonObject, JsonParser}

object JsonUtil {
  def gson(str: String) = {
    val json = new JsonParser()
    json.parse(str).asInstanceOf[JsonObject]
  }
}
