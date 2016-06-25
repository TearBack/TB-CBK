package com.test.zjj.tb_cbk.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.test.zjj.httplib.HttpHelper;
import com.test.zjj.httplib.Request;
import com.test.zjj.httplib.StringRequest;
import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.adapter.InfoListAdapter;
import com.test.zjj.tb_cbk.beans.Info;
import com.test.zjj.tb_cbk.ui.activity.DetailActivity;
import com.test.zjj.tb_cbk.utils.AnimaUtils;
import com.test.zjj.tb_cbk.utils.JSON_Utils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ContentFragment extends Fragment {
    private PtrClassicFrameLayout mRefreshView;
    private PullToRefreshListView ptrListView;
    private String url;
    private int currentPage = 1;
    private List<Info> infoList;
    private String channelID;
    private final int PULL_STATE_DOWN = 0;
    private final int PULL_STATE_UP = 1;
    private InfoListAdapter listAdapter;
    private ImageView imageView;
    private boolean isShowData = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_content, null);
        imageView = (ImageView) view.findViewById(R.id.frag_img_loading);
        Log.i("testi", "LineNum:57-->ContentFragment-->onCreateView: :" + "------");
        initView(view);
        channelID = getArguments().getString("channelID");
        if (channelID != null) {
            url = getUrl(channelID, currentPage);
        }
        //如果是保存果状态，那么直接使用保持的数据
        //saveInstance不为空，再判断数据size是否为0，不为零则直接从save里面读取数据内容，为0或者saveInstance为空，那么进行网络获取状态
        if (savedInstanceState != null && (infoList = savedInstanceState.getParcelableArrayList("save")) != null && infoList.size() != 0) {
            ptrListView.setAdapter(listAdapter = new InfoListAdapter(infoList, getActivity()));
            imageView.clearAnimation();
            imageView.setVisibility(View.GONE);
            isShowData = true;
            Log.i("testi", "LineNum:70-->ContentFragment-->onCreateView: savedInstanceState--isShowData:" + isShowData + ",size:" + infoList.size());
        } else {
            Log.i("testi", "LineNum:75-->ContentFragment-->onCreateView: getNetData:" + "---");
            getDataFromNetwork(url, PULL_STATE_DOWN, new RefreshListener() {
                @Override
                public void onCompeleteRefresh() {
                    isShowData = true;
                    Log.i("testi", "LineNum:78-->ContentFragment-->onCompeleteRefresh: getNetData--isShownData:" + isShowData);
                    imageView.clearAnimation();
                    imageView.setVisibility(View.GONE);
                }
            });
        }
        return view;
    }

    private String getUrl(String channelID, int currentPage) {
        String murl = null;
        if (!TextUtils.isEmpty(channelID)) {
            murl = " http://apis.baidu.com/showapi_open_bus/channel_news/search_news?channelId=" + channelID + "&page=" + currentPage;
        }
        return murl;
    }

    private String getUrlPageChange(String murl, int currentPage) throws Exception {
        if (murl == null) {
            throw new Exception("murl is null");
        }
        murl = murl.substring(0, murl.length() - 1) + currentPage;
        Log.i("test", "-->getUrlPageChange: murl:" + murl);
        return murl;
    }

    private void initView(View view) {
        ptrListView = (PullToRefreshListView) view.findViewById(R.id.frag_content_lv);
        ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到详细页面，把当前的info传递过去
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", infoList.get(position - 1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                Log.i("atest", "-->onPullDownToRefresh: Thread:" + Thread.currentThread().getName());
                try {
                    currentPage = 0;
                    getDataFromNetwork(getUrlPageChange(url, currentPage), PULL_STATE_DOWN, new RefreshListener() {
                        @Override
                        public void onCompeleteRefresh() {
                            refreshView.onRefreshComplete();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                Log.i("atest", "-->onPullUpToRefresh: Thread:" + Thread.currentThread().getName());
                try {
                    getDataFromNetwork(getUrlPageChange(url, ++currentPage), PULL_STATE_UP, new RefreshListener() {
                        @Override
                        public void onCompeleteRefresh() {
                            refreshView.onRefreshComplete();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getDataFromNetwork(String murl, final int state, final RefreshListener listener) {
        StringRequest req = new StringRequest(murl, "GET", new Request.CallBack() {
            @Override
            public void onResoponse(Object o) {
                String json = (String) o;
                try {
                    String jsonArr = JSON_Utils.getInfoJsonArr(json);
                    Log.i("testi", "LineNum:159-->ContentFragment-->onResoponse: jsonArr:" + jsonArr);
                    //如果原来已经有数据，那么根据下拉还是上拉，选择清空重置数据，或者添加数据
                    List<Info> temInfoList = JSON_Utils.parseJson2List(jsonArr);
                    if (temInfoList == null) {
                        throw new Exception("network data is null");
                    }
                    if (state == PULL_STATE_DOWN) {    //下拉状态时候
                        if (infoList != null) {    //不为空则清空重置数据
                            infoList.clear();
                            infoList.addAll(temInfoList);
                        } else {
                            infoList = temInfoList; //为空则为第一次加载数据
                        }
                    } else if (state == PULL_STATE_UP) {
                        if (infoList != null) {    //直接往后面添加数据
                            infoList.addAll(temInfoList);
                        }
                    } else {
                        throw new Exception("Pull state is error,state only can be PULL_STATE_DOWN(0) or PULL_STATE_UP(1)");
                    }
                    //更新listview
                    Log.i("testi", "LineNum:178-->ContentFragment-->onResoponse: getActivity:" + getActivity());
                    if (getActivity() != null) {    //为了处理快速滑动导致的空
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (listAdapter == null) {  //第一次设置数据
                                    listAdapter = new InfoListAdapter(infoList, getActivity());
                                    ptrListView.setAdapter(listAdapter);
                                } else {
                                    listAdapter.notifyDataSetChanged();
                                }
                                listener.onCompeleteRefresh();  //调用刷新完成方法
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        HttpHelper.addRequest(req);
    }

    public interface RefreshListener {
        void onCompeleteRefresh();
    }

    @Override
    public void onResume() {
        if (!isShowData) {
            AnimaUtils.setLoadingAnima(imageView);
        }
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("testi", "-->onSaveInstanceState:id :" + this.toString() + "00000");
        if (infoList == null || infoList.size() == 0) return;
        Log.i("testi", "-->onSaveInstanceState: id:" + this.toString() + "1111111");
        outState.putParcelableArrayList("save", (ArrayList<? extends Parcelable>) infoList);
    }
}
