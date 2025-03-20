package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.databinding.ActivityWelcomeBinding
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB

class WelcomeActivity : AppCompatActivity() {
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private lateinit var layouts: IntArray

    private val binding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onStart() {
        super.onStart()

        window.statusBarColor =
            ContextCompat.getColor(this@WelcomeActivity, R.color.your_color)
    }

    override fun onResume() {
        super.onResume()

        window.statusBarColor =
            ContextCompat.getColor(this@WelcomeActivity, R.color.your_color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Checking for first time launch - before calling setContentView()
        setContentView(binding.root)

        val tinyDB = TinyDB(this)

        if (!tinyDB.getBoolean("ShowWelcomeScreen")) {
            tinyDB.putBoolean("ShowWelcomeScreen", true)
        }


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
            R.layout.welcome_slide1,
            R.layout.welcome_slide2,
            R.layout.welcome_slide3
        )


        // making notification bar transparent
        changeStatusBarColor()

        myViewPagerAdapter = MyViewPagerAdapter()
        binding.viewPager!!.adapter = myViewPagerAdapter
        binding.viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)


        binding.btnNext!!.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts.size) {
                // move to next screen
                binding.viewPager!!.currentItem = current
            } else {
                launchHomeScreen()
            }
        }
    }


    private fun getItem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }

    private fun launchHomeScreen() {
        startActivity(Intent(this@WelcomeActivity, PermissionActivity::class.java))
        finish()
    }

    //  viewpager change listener
    var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            // changing the next button text 'NEXT' / 'GOT IT'


            if (position == layouts.size - 1) {
                // last page. make button text to GOT IT
                binding.btnNext.text = "Get Started"
            } else {
                // still pages are left
                binding.btnNext.text = "Skip"
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
        }

        override fun onPageScrollStateChanged(arg0: Int) {
        }
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts[position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}