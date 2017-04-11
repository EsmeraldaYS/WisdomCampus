package demo.ysu.com.wisdomcampus;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import butterknife.ButterKnife;
import demo.ysu.com.wisdomcampus.utils.WeekUtils;


/**
 * Created by Administrator on 2016/11/26.
 */
public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //不含有星期的item
    private static final int NORMAL_ITEM = 0;
    //带星期的item
    private static final int WEEK_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_HEADER = 2;
    private List<CourseBean> startList;
    private List<Integer> imageslist;
    private List<String> titleslist;


    public CourseAdapter(List<CourseBean> startList, List<Integer> imageslist, List<String>titleslist) {
        this.imageslist = imageslist;
        this.titleslist = titleslist;
        this.startList = startList;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM) {
            return new NormalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal_course, parent, false));
        } else if (viewType == WEEK_ITEM) {
            return new WeekViewHOlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false));
        } else if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner, parent, false));
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseBean courseBean = startList.get(position-1);
        String week = WeekUtils.getWeek(startList.get(position-1).getCourseTime());
        if (holder instanceof WeekViewHOlder) {
            ((WeekViewHOlder) holder).course_name.setText(courseBean.getCourseName());
            ((WeekViewHOlder) holder).course_info.setText(courseBean.getCourstTimeDetail() + "，" + courseBean.getCourseLocation());
            ((WeekViewHOlder) holder).course_week.setText(week);

        } else if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).course_name.setText(courseBean.getCourseName());
            ((NormalViewHolder) holder).course_info.setText(courseBean.getCourstTimeDetail() + "，" + courseBean.getCourseLocation());
        }
        else if (holder instanceof BannerViewHolder) {
            ((BannerViewHolder) holder).banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置图片加载器
            ((BannerViewHolder) holder).banner.setImageLoader(new GlideImageLoader());
            //设置图片集合

            ((BannerViewHolder) holder).banner.setImages(imageslist);
            //设置banner动画效果
            ((BannerViewHolder) holder).banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            ((BannerViewHolder) holder).banner.setBannerTitles(titleslist);
            //设置自动轮播，默认为true
            ((BannerViewHolder) holder).banner.isAutoPlay(true);
            //设置轮播时间
            ((BannerViewHolder) holder).banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            ((BannerViewHolder) holder).banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            ((BannerViewHolder) holder).banner.start();;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return ITEM_VIEW_TYPE_HEADER;
        }
        if (position == 1) {
            return WEEK_ITEM;
        }

        String currentWeek = startList.get(position).getCourseTime();
        int prePosition = position - 1;
        boolean isDiff = startList.get(prePosition).getCourseTime().equals(currentWeek);
        return isDiff ? NORMAL_ITEM : WEEK_ITEM;
    }

    @Override
    public int getItemCount() {
        return startList.size()+1;
    }

    class WeekViewHOlder extends NormalViewHolder {

        TextView course_week;

        public WeekViewHOlder(View itemView) {
            super(itemView);
            course_week = ButterKnife.findById(itemView, R.id.course_week);
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView course_name, course_info;
        CardView course_card;

        public NormalViewHolder(View itemView) {
            super(itemView);
            course_name = ButterKnife.findById(itemView, R.id.course_name);
            course_info = ButterKnife.findById(itemView, R.id.course_info);
            course_card = ButterKnife.findById(itemView, R.id.card_course);
            course_card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.card_course) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }
    }
    class BannerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        com.youth.banner.Banner banner;


        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = ButterKnife.findById(itemView, R.id.banner);
            banner.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.banner) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }
    }
}
