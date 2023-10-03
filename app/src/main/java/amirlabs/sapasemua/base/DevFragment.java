package amirlabs.sapasemua.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class DevFragment<T extends ViewDataBinding> extends Fragment implements DevView {
    public DevViewModel vm = new DevViewModel();
    public DevView devView = null;
    protected DevActivity currentActivity = null;
    private int layoutResId;
    public T binding;

    public DevFragment(@LayoutRes int layoutResId) {
        this.layoutResId = layoutResId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initUI();
        initAction();
        initObserver();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DevActivity) {
            currentActivity = (DevActivity) context;
            currentActivity.onFragmentAttached(this.getClass().getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        currentActivity.onFragmentDetached(this.getClass().getSimpleName());
        currentActivity = null;
        super.onDetach();
    }

    /**
     * Method to init global variable or data from intent
     */
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

    public interface AttachListener {
        /**
         * Action when fragment attached
         */
        void onFragmentAttached(String tag);

        /**
         * Action when fragment detached
         */
        void onFragmentDetached(String tag);
    }
}
