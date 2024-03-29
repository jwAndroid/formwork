package com.jwandroid.android_global_kit.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

sealed class UtilityBase{
    open class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment(){

        lateinit var binding: T

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
            binding.onCreateView()
//            binding.onViewCreated()
            return binding.root
        }

        open fun T.onCreateView() = Unit
//        open fun T.onViewCreated() = Unit
    }

   open class BaseAppCompatActivity<T: ViewDataBinding>(@LayoutRes val layoutRes: Int) : AppCompatActivity(){

        lateinit var binding: T

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, layoutRes)
            binding.onCreate()
        }

        open fun T.onCreate() = Unit

        fun loadFragment(container : FrameLayout, target : Fragment){
            supportFragmentManager.commit {
                replace(container.id , target)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

    }


}