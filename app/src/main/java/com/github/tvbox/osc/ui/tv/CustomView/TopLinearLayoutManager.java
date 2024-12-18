package com.github.tvbox.osc.ui.tv.CustomView;

import android.content.Context;
import android.graphics.PointF;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
public class TopLinearLayoutManager extends LinearLayoutManager {

    public class a extends LinearSmoothScroller {
        public a(Context context) {
            super(context);
        }

        @Override
        public PointF computeScrollVectorForPosition(int i) {
            return computeScrollVectorForPosition(i);
        }

        @Override
        public int getVerticalSnapPreference() {
            return -1;
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        a aVar = new a(recyclerView.getContext());
        aVar.setTargetPosition(i);
        startSmoothScroll(aVar);
    }
}
