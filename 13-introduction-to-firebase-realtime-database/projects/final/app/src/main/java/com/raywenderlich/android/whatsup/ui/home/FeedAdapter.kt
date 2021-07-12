/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.whatsup.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.android.whatsup.R
import com.raywenderlich.android.whatsup.model.Post
import 	androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_item.view.*

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.PostViewHolder>() {

  private val posts = mutableListOf<Post>()
  private val onItemClickLiveData = MutableLiveData<Post>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
    return PostViewHolder(view, onItemClickLiveData)
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.setItem(posts[position])

  override fun getItemCount(): Int = posts.size

  fun onFeedUpdate(posts: List<Post>) {
    this.posts.clear()
    this.posts.addAll(posts)
    notifyDataSetChanged()
  }

  fun onPostItemClick(): LiveData<Post> = onItemClickLiveData

  class PostViewHolder(private val view: View, private val onItemClickLiveData: MutableLiveData<Post>) : RecyclerView.ViewHolder(view) {

    private lateinit var post: Post

    init {
      view.setOnClickListener { onItemClickLiveData.postValue(post) }
    }

    fun setItem(post: Post) {
      this.post = post
      with(view) {
        author.text = post.author
        content.text = post.content
      }
    }
  }
}