package akhmedoff.usman.data.utils.deserializers

import akhmedoff.usman.data.model.Catalog
import akhmedoff.usman.data.model.CatalogItem
import akhmedoff.usman.data.model.CatalogItemType
import akhmedoff.usman.data.model.ResponseCatalog
import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CatalogDeserializer : JsonDeserializer<ResponseCatalog> {

    override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
    ): ResponseCatalog {

        Log.d("DESERIALIZER catalog", "started")
        val jsonObject = json.asJsonObject["response"]?.asJsonObject
        val catalogs = mutableListOf<Catalog>()

        jsonObject?.let {
            val catalogsJsonArray = jsonObject["items"]?.asJsonArray

            catalogsJsonArray?.forEach { catalogElement ->
                val catalogJson = catalogElement?.asJsonObject
                val videoList = mutableListOf<CatalogItem>()

                val videosJsonArray = catalogJson?.get("items")?.asJsonArray


                var catalogId = ""
                catalogJson?.get("id")?.let { catalogId = it.asString }

                videosJsonArray?.forEach { videoElement ->
                    val videoJson = videoElement.asJsonObject

                    val item = CatalogItem()
                    with(item) {
                        id = videoJson["id"].asJsonPrimitive.asInt
                        ownerId = videoJson["owner_id"].asInt
                        title = videoJson["title"].asString
                        this.catalogId = catalogId

                        videoJson["duration"]?.let { duration = it.asInt }
                        videoJson["description"]?.let { description = it.asString }
                        videoJson["date"]?.let { date = it.asLong }
                        videoJson["comments"]?.let { comments = it.asInt }
                        videoJson["views"]?.let { views = it.asInt }
                        videoJson["access_key"]?.let { accessKey = it.asString }
                        videoJson["photo_130"]?.let { photo130 = it.asString }
                        videoJson["photo_320"]?.let { photo320 = it.asString }
                        videoJson["photo_640"]?.let { photo640 = it.asString }
                        videoJson["photo_800"]?.let { photo800 = it.asString }
                        videoJson["platform"]?.let { platform = it.asString }

                        canAdd = when (videoJson["can_add"]?.asInt) {
                            null -> false
                            0 -> false
                            else -> true
                        }

                        videoJson["type"]?.let {
                            when (it.asString) {
                                "video" -> type = CatalogItemType.VIDEO
                                "album" -> type = CatalogItemType.ALBUM
                            }
                        }
                    }


                    videoList.add(item)
                }

                val name = catalogJson?.get("name")?.asString


                val view = catalogJson?.get("view")?.asString
                val canHide = catalogJson?.get("can_hide")?.asBoolean
                val type = catalogJson?.get("type")?.asString

                if (videoList.isNotEmpty()) catalogs.add(
                        Catalog().apply {
                            id = catalogId
                            items = videoList
                            this.name = name
                            this.view = view
                            this.canHide = canHide
                            this.type = type
                        }

                )
            }
        }

        return ResponseCatalog(catalogs, jsonObject?.get("next")?.asString)
    }
}