package com.github.tvbox.osc.ui.tv.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.tvbox.osc.Rstyleable;
import java.util.ArrayList;
public class CustomRecyclerView extends RecyclerView implements View.OnFocusChangeListener {
    public boolean c;
    public boolean d;
    public c e;
    public b f;
    public int g;
    public d h;
    public int i;

    public class a implements Runnable {
        public final int c;

        public a(int i) {
            this.c = i;
        }

        @Override
        public void run() {
            int i;
            if (getAdapter() == null || (i = this.c) < 0 || i >= getItemCount()) {
                return;
            }
            View findViewByPosition = getLayoutManager() != null ? getLayoutManager().findViewByPosition(this.c) : null;
            if (findViewByPosition != null) {
                if (!hasFocus()) {
                    onFocusChanged(true, 130, null);
                }
                findViewByPosition.requestFocus();
            }
        }
    }

    public interface b {
        void a(View view, View view2);
    }

    public interface c {
        void a(View view, int i);
    }

    public interface d {
        void a(CustomRecyclerView customRecyclerView, View view, int i);
    }

    public CustomRecyclerView(Context context) {
        super(context);
        this.c = true;
        this.d = true;
        this.g = 0;
        b(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = true;
        this.d = true;
        this.g = 0;
        b(context, attributeSet);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = true;
        this.d = true;
        this.g = 0;
        b(context, attributeSet);
    }

    @Override
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        View findViewByPosition;
        if (hasFocus() || this.g < 0 || (findViewByPosition = getLayoutManager().findViewByPosition(this.g)) == null || !findViewByPosition.isFocusable()) {
            super.addFocusables(arrayList, i, i2);
        } else {
            arrayList.add(findViewByPosition);
        }
    }

    public void b(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Rstyleable.CustomRecyclerView);
        this.i = obtainStyledAttributes.getLayoutDimension(0, this.i);
        obtainStyledAttributes.recycle();
        setItemViewCacheSize(20);
        setItemAnimator(null);
        setHasFixedSize(true);
        setDescendantFocusability(262144);
        setChildrenDrawingOrderEnabled(true);
        setFocusable(true);
        setLayoutManager(new CustomLayoutManager(context));
    }

    public boolean c() {
        return getScrollState() != 0;
    }

    public void d(int i) {
        View findViewByPosition;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null || (findViewByPosition = layoutManager.findViewByPosition(i)) == null) {
            return;
        }
        scrollBy(0, ((findViewByPosition.getHeight() / 2) + findViewByPosition.getTop()) - (getHeight() / 2));
    }

    public void e(int i) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i, 0);
        }
    }

    @Override
    public View focusSearch(int i) {
        return super.focusSearch(i);
    }

    @Override
    public View focusSearch(View view, int i) {
        View focusSearch = super.focusSearch(view, i);
        if (view != null && focusSearch != null && findContainingItemView(focusSearch) == null) {
            if (!this.c && (i == 130 || i == 33)) {
                return view;
            }
            if (!this.d && (i == 17 || i == 66)) {
                return view;
            }
            c cVar = this.e;
            if (cVar != null) {
                cVar.a(view, i);
            }
        }
        return focusSearch;
    }

    @Override
    public int getChildDrawingOrder(int i, int i2) {
        View focusedChild = getFocusedChild();
        if (focusedChild == null) {
            return super.getChildDrawingOrder(i, i2);
        }
        int indexOfChild = indexOfChild(focusedChild);
        return i2 == i + (-1) ? indexOfChild : i2 < indexOfChild ? i2 : i2 + 1;
    }

    public int getItemCount() {
        if (getAdapter() != null) {
            return getAdapter().getItemCount();
        }
        return 0;
    }

    @Override
    public void onChildAttachedToWindow(View view) {
        if (view.isFocusable() && view.getOnFocusChangeListener() == null) {
            view.setOnFocusChangeListener(this);
        }
    }

    @Override
    public void onFocusChange(View view, boolean z) {
        d dVar;
        if (view != null) {
            int childAdapterPosition = getChildAdapterPosition(view);
            boolean z2 = view instanceof RecyclerView;
            if (!z2) {
                view.setSelected(z);
            }
            if (!z || z2 || (dVar = this.h) == null) {
                return;
            }
            dVar.a(this, view, childAdapterPosition);
        }
    }

    @Override
    public void onMeasure(int i, int i2) {
        int makeMeasureSpec;
        int i3 = this.i;
        if (i3 != 0 && i2 > (makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE))) {
            i2 = makeMeasureSpec;
        }
        super.onMeasure(i, i2);
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        b bVar;
        if (!hasFocus() && (bVar = this.f) != null) {
            bVar.a(view, view2);
        }
        super.requestChildFocus(view, view2);
        this.g = getChildViewHolder(view).getAdapterPosition();
    }

    public void setCanFocusOutHorizontal(boolean z) {
        this.d = z;
    }

    public void setCanFocusOutVertical(boolean z) {
        this.c = z;
    }

    public void setFocusLostListener(c cVar) {
        this.e = cVar;
    }

    public void setGainFocusListener(b bVar) {
        this.f = bVar;
    }

    public void setOnItemListener(d dVar) {
        this.h = dVar;
    }

    public void setSelection(int i) {
        post(new a(i));
    }
}
