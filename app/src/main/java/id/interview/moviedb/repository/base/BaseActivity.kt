package id.interview.moviedb.repository.base

import androidx.appcompat.app.AppCompatActivity
import id.interview.moviedb.repository.IView
import id.interview.moviedb.repository.NetworkingState
import id.interview.moviedb.repository.ViewNetworkState
import id.interview.moviedb.repository.api.ResponseCode
import kotlin.properties.Delegates

abstract class BaseActivity : AppCompatActivity(), ViewNetworkState, IView {
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
        runOnUiThread {
            when (code) {
                ResponseCode.UNAUTHORIZED -> {
//                    AppSession(baseContext).clearSession()
//                    showActivityWithClearTop(Root::class.java)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkState = NetworkingState.Destroy()
    }

    abstract fun initView()
}