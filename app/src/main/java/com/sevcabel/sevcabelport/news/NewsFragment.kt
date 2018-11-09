package com.sevcabel.sevcabelport.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.sevcabel.sevcabelport.R

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
