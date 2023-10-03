package amirlabs.sapasemua.ui.menu;

import android.view.View;
import android.view.animation.TranslateAnimation;

import androidx.navigation.fragment.NavHostFragment;

import amirlabs.sapasemua.R;
import amirlabs.sapasemua.base.DevFragment;
import amirlabs.sapasemua.databinding.FragmentContainerMenuBinding;

public class ContainerMenuFragment extends DevFragment<FragmentContainerMenuBinding> {

    public ContainerMenuFragment() {
        super(R.layout.fragment_container_menu);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        NavHostFragment nav = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_menu);
        if (nav != null) {
//            NavigationUI.setupWithNavController(binding.bnMenu, nav.getNavController());
            nav.getNavController().addOnDestinationChangedListener((navController, destination, bundle) -> {
                if (destination.getId() == R.id.homeFragment ||
                        destination.getId() == R.id.moduleFragment ||
                        destination.getId() == R.id.forumFragment ||
                        destination.getId() == R.id.profileFragment) {
                    showBottomNav();
                } else {
                    hideBottomNav();
                }
            });
        }
    }

    @Override
    public void initAction() {

    }

    @Override
    public void initObserver() {

    }


    private void showBottomNav() {
        if (binding.bnMenu.getVisibility() == View.VISIBLE) return;
        binding.bnMenu.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0f, 0f, (float) binding.bnMenu.getHeight(), 0f);
        animate.setDuration(400);
        binding.bnMenu.startAnimation(animate);
    }

    private void hideBottomNav() {
        if (binding.bnMenu.getVisibility() == View.GONE) return;
        TranslateAnimation animate = new TranslateAnimation(0f, 0f, 0f, (float) binding.bnMenu.getHeight());
        animate.setDuration(400);
        binding.bnMenu.startAnimation(animate);
        binding.bnMenu.setVisibility(View.GONE);
    }
}