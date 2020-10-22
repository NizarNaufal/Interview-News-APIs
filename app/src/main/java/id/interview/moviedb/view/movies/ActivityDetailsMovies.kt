package id.interview.moviedb.view.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.interview.moviedb.R
import id.interview.moviedb.repository.IView
import id.interview.moviedb.repository.ViewNetworkState
import id.interview.moviedb.repository.base.BaseActivity
import id.interview.moviedb.support.baseUrl
import id.interview.moviedb.view.movies.modules.MoviesModels
import id.interview.moviedb.view.movies.support.MoviesPresenter
import kotlinx.android.synthetic.main.activity_movie.*
import id.interview.moviedb.support.*
import kotlinx.android.synthetic.main.include_news_details_content.*


class ActivityDetailsMovies: BaseActivity(), ViewNetworkState, IView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        initView()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    override fun initView() {
        val data = intent?.getParcelableExtra<MoviesModels>("data")
        image_news_content?.displayImage(baseContext, data?.url_to_image)
       title_news_ku?.text = data?.title_news
        content_news?.text = data?.desc_news

    }
}