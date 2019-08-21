package perusudroid.baseproject.common.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.trisysit.baseproject_mvvm.R;


public class ViewSwitcher extends android.widget.ViewSwitcher {
    public ViewSwitcher(Context paramContext) {
        super(paramContext);
        init();
    }

    public ViewSwitcher(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        // Declare in and out animations and load them using AnimationUtils class
        setInAnimation(getContext(), R.anim.fast_fade_in);
        setOutAnimation(getContext(), R.anim.fast_fade_out);
    }

    public void setChildVisible() {

        if (getDisplayedChild() != 1) {
            setDisplayedChild(1);
        }
    }

    public void setParentVisible() {
        if (getDisplayedChild() != 0) {
            setDisplayedChild(0);
        }
    }
}
