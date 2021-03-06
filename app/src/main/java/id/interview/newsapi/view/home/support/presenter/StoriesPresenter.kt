package id.interview.newsapi.view.home.support.presenter

import android.content.Context
import com.google.gson.Gson
import id.interview.newsapi.repository.AppApiClient
import id.interview.newsapi.repository.NetworkingState
import id.interview.newsapi.repository.ViewNetworkState
import id.interview.newsapi.repository.api.ResponseCode
import id.interview.newsapi.view.home.modules.StoriesModels
import id.interview.newsapi.view.home.support.IStoriesPresenter
import id.interview.newsapi.view.home.support.iteractor.StoriesIteractor
import org.json.JSONObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoriesPresenter(var context: Context, var view: ViewNetworkState) :
    IStoriesPresenter {

    private val iteractor by lazy { StoriesIteractor(AppApiClient.mainClient()) }

    val storiesParam = "stories.param"

    override fun getStoriesNews(apiKey: String) {
        view.networkState = NetworkingState.ShowLoading(Pair(storiesParam, true))
        GlobalScope.launch {
            val response = iteractor.getStoriesNews(apiKey)
            (view.networkState !is NetworkingState.Destroy).apply {
                view.networkState = NetworkingState.ShowLoading(Pair(storiesParam, false))
                when (response.first) {
                    ResponseCode.OK -> {
                        val json = JSONObject(response.second.toString())
                        val listData = Gson().fromJson(
                            json.getJSONArray("sources").toString(),
                            Array<StoriesModels>::class.java
                        ).toList()
                        view.networkState =
                            NetworkingState.ResponseSuccess(Pair(storiesParam, listData))
                    }
                    else -> view.networkState = NetworkingState.ResponseFailure(
                        Pair(
                            storiesParam,
                            Pair(response.first, response.second)
                        )
                    )
                }
            }
        }
    }
}