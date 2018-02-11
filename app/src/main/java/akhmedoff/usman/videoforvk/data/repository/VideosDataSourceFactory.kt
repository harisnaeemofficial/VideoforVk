package akhmedoff.usman.videoforvk.data.repository

import akhmedoff.usman.videoforvk.data.api.VkApi
import akhmedoff.usman.videoforvk.model.Video
import android.arch.paging.DataSource


class VideosDataSourceFactory(
    private val vkApi: VkApi,
    private val ownerId: String?,
    private val videoId: String?,
    private val albumId: String?
) : DataSource.Factory<Int, Video> {
    override fun create() = VideosDataSource(vkApi, ownerId, videoId, albumId)
}