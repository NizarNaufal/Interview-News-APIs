package id.interview.newsapi.repository.base

import androidx.fragment.app.Fragment
import id.interview.newsapi.repository.IView
import id.interview.newsapi.repository.NetworkingState
import id.interview.newsapi.repository.ViewNetworkState
import id.interview.newsapi.repository.api.ResponseCode
import kotlin.properties.Delegates

abstract class BaseFragment : Fragment(),
    ViewNetworkState,
    IView {
    override var networkState: NetworkingState by Delegates.observable<NetworkingState>(
        NetworkingState.Create()
    ) { _, _, newValue ->
        when (newValue) {
            is NetworkingState.ShowLoading -> showLoading(
                newValue.status.first,
                newValue.status.second
            )
            is NetworkingState.ResponseSuccess -> requestSuccess(
                newValue.response.first,
                newValue.response.second
            )
            is NetworkingState.ResponseFailure -> {
                requestFailure(
                    newValue.response.first,
                    newValue.response.second.first,
                    newValue.response.second.second
                )
            }
        }
    }

    override fun showLoading(key: String, status: Boolean) = Unit

    override fun requestSuccess(key: String, response: Any?) = Unit

    override fun requestFailure(key: String, code: Int, message: Any?) {
        activity?.runOnUiThread {
            when (code) {
                ResponseCode.UNAUTHORIZED -> {
//                    context?.let { AppSession(it).clearSession() }
//                    activity?.showActivityWithClearTop(Root::class.java)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkState =
            NetworkingState.Destroy()
    }

    abstract fun initView()
}