package com.isabella.dechat.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;  
  
    public SpacesItemDecoration(int space) {  
        this.space = space;  
    }  
  
  
    @Override  
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;  
        outRect.right = space/2;
        outRect.bottom = space;  
      //  if (parent.getChildAdapterPosition(view) == 0) {
            //是最上面的  
            outRect.top = space;  
      //  }
    }  
}  