package amirlabs.sapasemua.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class DevActivity<T extends ViewDataBinding> extends AppCompatActivity implements DevView, DevFragment.AttachListener {
    private final int layoutResId;
    public DevViewModel vm = new DevViewModel();
    public DevView devView = null;
    protected T binding;
    public DevActivity(@LayoutRes int layoutResId){
        this.layoutResId = layoutResId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutResId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        initUI();
        initAction();
        initObserver();
    }

    public abstract void initData();

    /**
     * Method for UI configuration and initialization
     */
    public abstract void initUI();

    /**
     * Method to action to do in fragment
     */
    public abstract void initAction();

    /**
     * Method to initialize observer
     */
    public abstract void initObserver();

    @Override
    public void onFragmentAttached(String tag) {
        Log.d(tag,"Attached");
    }

    @Override
    public void onFragmentDetached(String tag) {
        Log.d(tag,"Detached");
    }
}
