package moun.com.wimf.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import moun.com.wimf.fragment.MenuBurgersFragment;
import moun.com.wimf.fragment.MenuDessertsFragment;
import moun.com.wimf.fragment.MenuDrinksFragment;
import moun.com.wimf.fragment.MenuPizzaFragment;
import moun.com.wimf.fragment.MenuSaladsFragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Friends_Fragment;
import moun.com.wimf.fragment.WIMF_UserProfil_Info_Fragment;

/**
 * Class define a view pager adapter for the swipe tabs feature.
 */

public class WIMF_MenuPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public WIMF_MenuPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // Create a new fragment
                WIMF_UserProfil_Info_Fragment tab1 = new WIMF_UserProfil_Info_Fragment();
                return tab1;
            case 1:
                WIMF_UserProfil_Info_Fragment tab2 = new WIMF_UserProfil_Info_Fragment();
                return tab2;
            case 2:
                WIMF_UserProfil_Friends_Fragment tab3 = new WIMF_UserProfil_Friends_Fragment();
                return tab3;
            case 3:
                WIMF_UserProfil_Friends_Fragment tab4 = new WIMF_UserProfil_Friends_Fragment  ();
                return tab4;
            case 4:
                MenuDessertsFragment tab5 = new MenuDessertsFragment();
                return tab5;
            case 5:
                MenuDrinksFragment tab6 = new MenuDrinksFragment();
                return tab6;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
