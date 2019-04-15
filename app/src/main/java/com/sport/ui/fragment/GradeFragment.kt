package com.sport.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sport.common.adapter.CommomAdapter
import com.sport.databinding.FragmentGradeBinding
import com.sport.databinding.ListSportPlanBinding
import com.sport.model.Sport
import com.sport.utilities.InjectorUtils
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.USER_CURRENT_ITEM
import com.sport.viewmodels.GradeViewModel
import java.util.*
import android.graphics.*
import android.text.TextPaint
import android.graphics.Typeface
import timber.log.Timber

/**
 * User: bizehao
 * Date: 2019-03-29
 * Time: 下午4:48
 * Description: 俯卧撑的等级
 */
class GradeFragment : Fragment() {

    private lateinit var viewModel: GradeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentGradeBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideGradeViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(GradeViewModel::class.java)
        subscribeUi(binding, context)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentGradeBinding, context: Context) {
        val adapter = Adapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(ItemDecoration({
            if (adapter.getList().isNotEmpty()) {
                adapter.getList()[it].isStartOfGroup
            } else {
                false
            }
        }, {
            if (adapter.getList().isNotEmpty()) {
                adapter.getList()[it].grade
            } else {
                -1
            }
        }))

        viewModel.getSportData().observe(viewLifecycleOwner, Observer {
            if (it.status == 0 && it.data != null) {
                viewModel.setSportViewData(it.data)
            }
        })

        viewModel.sportMapOfLiveData.observe(viewLifecycleOwner, Observer {
            adapter.map1 = it
        })

        viewModel.getSportViewData().observe(viewLifecycleOwner, Observer {
            adapter.lise = it
            adapter.notifyDataSetChanged()
        })
    }


    private inner class Adapter : CommomAdapter<Sport, ListSportPlanBinding>() {

        lateinit var map1: HashMap<Int, Int> //从哪一行隔开
        private var indexOfSelectedOnBefore = -1

        override fun onSetBinding(parent: ViewGroup): ListSportPlanBinding {
            return ListSportPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        override fun convert(holder: ViewHolder, t: Sport, position: Int) {

            if (t.isStartOfGroup) {
                holder.getBinding().topLayout.visibility = View.GONE
            } else {
                holder.getBinding().topLayout.visibility = View.GONE
            }

            if (t.current) { //当前项
                holder.getBinding().nowItem.visibility = View.VISIBLE
                indexOfSelectedOnBefore = position
            } else {
                holder.getBinding().nowItem.visibility = View.GONE
            }

            val grade = "等级" + t.grade //等级
            holder.getBinding().grade.text = grade
            val days = map1[t.grade].toString() + "天" //一共多少天
            holder.getBinding().days.text = days
            val dayNum = "第${position + 1}天" //第几天
            holder.getBinding().dayNum.text = dayNum
            if (t.dateTime == "") {
                holder.getBinding().date.visibility = View.GONE
            } else {
                holder.getBinding().date.visibility = View.VISIBLE
                val date = t.dateTime //日期
                holder.getBinding().date.text = date
            }
            holder.getBinding().pushUpNum.text = t.pushUpNum

            holder.getBinding().layoutTouch.setOnClickListener {
                if (position != indexOfSelectedOnBefore) {
                    SharePreferencesUtil.save(USER_CURRENT_ITEM, position)
                    viewModel.setSportViewData(null)
                }
            }
        }
    }

    inner class ItemDecoration(
        private val isStartOfGroup: (position: Int) -> Boolean,
        private val getGroupId: (position: Int) -> Int
    ) : RecyclerView.ItemDecoration() {

        private val mPaint = Paint()
        private var textPaint = Paint()
        private val mGroupHeight = 200

        init {
            mPaint.color = Color.WHITE
            //文字画笔
            textPaint = TextPaint()
            textPaint.typeface = Typeface.DEFAULT_BOLD
            textPaint.isAntiAlias = true
            textPaint.textSize = 65f
            textPaint.color = Color.WHITE
        }

        //设置ItemView的内嵌偏移长度（inset）
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            if (getGroupId(position) < 0) return
            if (isStartOfGroup(position)) {
                outRect.top = mGroupHeight
            } else {
                outRect.top = 0
            }
        }

        // 在子视图上设置绘制范围，并绘制内容
        // 绘制图层在ItemView以下，所以如果绘制区域与ItemView区域相重叠，会被遮挡
        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(c, parent, state)
        }

        //同样是绘制内容，但与onDraw（）的区别是：绘制在图层的最上层
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
            val pLeft = parent.left.toFloat()
            val pRight = parent.right.toFloat()
            val childCount = parent.childCount
            val itemCount = state.itemCount
            var previousGroupId = -1 // //上一个的组id
            var currentGroupId = -1 //当前的组id
            var nextGroupId = -1 //下一个的组id
            for (i in 0 until childCount) {
                // 获取每个Item的位置
                val childView = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(childView)

                if (position != itemCount - 1) {
                    mPaint.color = Color.WHITE
                    // 设置矩形(分割线)的宽度为10px
                    val mDivider = 10
                    // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
                    val cView = childView.findViewById<View>(com.sport.R.id.layout_touch)
                    val left = cView.left.toFloat()
                    val top = childView.bottom.toFloat()
                    // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
                    val right = cView.right.toFloat()
                    val bottom = top + mDivider
                    // 通过Canvas绘制矩形（分割线）
                    c.drawRect(left, top, right, bottom, mPaint)
                }

                currentGroupId = getGroupId(position)
                if (currentGroupId == previousGroupId) continue

                var yOfBottom = Math.max(mGroupHeight, childView.top).toFloat()
                if (position + 1 < itemCount) {
                    val viewBottom = childView.bottom.toFloat()
                    nextGroupId = getGroupId(position + 1)
                    if (currentGroupId != nextGroupId && viewBottom < yOfBottom) { //组内最后一个view进入了header
                        yOfBottom = viewBottom
                    }
                }
                //画背景面板
                mPaint.color = resources.getColor(com.sport.R.color.plan_list_background, null)
                c.drawRect(pLeft, yOfBottom - mGroupHeight, pRight, yOfBottom, mPaint)

                previousGroupId = currentGroupId

                //画星星
                val vectorDrawable = context!!.getDrawable(com.sport.R.drawable.ic_star_black) ?: return
                val bitmap = Bitmap.createBitmap(
                    vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
                vectorDrawable.draw(canvas)
                c.drawBitmap(bitmap, pLeft + 20, yOfBottom - mGroupHeight / 2 - bitmap.height / 2, mPaint)

                //写文字(等级)
                var grade = ""
                when (currentGroupId) {
                    1 -> grade = "等级一"
                    2 -> grade = "等级二"
                    3 -> grade = "等级三"
                }
                val fontMetrics = textPaint.fontMetrics
                val fTop = fontMetrics.top
                val fBottom = fontMetrics.bottom
                val fHeight = (fBottom - fTop) / 2 - fBottom
                textPaint.textAlign = Paint.Align.LEFT
                c.drawText(grade, pLeft + 50 + bitmap.width, yOfBottom - mGroupHeight / 2 + fHeight, textPaint)

                //写文字(天数)
                val map = viewModel.sportMapOfLiveData.value ?: return
                val days = "${map[currentGroupId]}天"
                textPaint.textAlign = Paint.Align.RIGHT
                c.drawText(days, pRight - 100, yOfBottom - mGroupHeight / 2 + fHeight, textPaint)
            }
        }
    }
}