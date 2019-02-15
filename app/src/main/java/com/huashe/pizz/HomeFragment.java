package com.huashe.pizz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button homeBtnAboutUs, homeBtnHallcase, homeBtnModuleproduct;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeBtnAboutUs = view.findViewById(R.id.home_btn_aboutus);
        homeBtnHallcase = view.findViewById(R.id.home_btn_hallcase);
        homeBtnModuleproduct = view.findViewById(R.id.home_btn_moduleproduct);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.home_btn_aboutus:
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.content_fragment, aboutUsFragment);
                fragmentTransaction.commit();
                break;
            case R.id.home_btn_hallcase:
                HallCaseFragment hallCaseFragment = new HallCaseFragment();
                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.add(R.id.content_fragment, hallCaseFragment);
                fragmentTransaction2.commit();
                break;
            case R.id.home_btn_moduleproduct:
                ModuleProductFragment moduleProductFragment = new ModuleProductFragment();
                FragmentTransaction fragmentTransaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.add(R.id.content_fragment, moduleProductFragment);
                fragmentTransaction3.commit();
                break;
            default:
                break;
        }
    }
}
