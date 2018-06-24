package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.afollestad.materialdialogs.MaterialDialog;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class BaseFragment extends Fragment {

    MaterialDialog dialog;
    protected int navigation_item;

    public void showDialogWithMessage(String message){
        dialog = new MaterialDialog.Builder(getActivity())
                .content(message)
                .progress(true,0)
                .show();
    }

    public void dismissDialog(){
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showAlert(String message){
        Snackbar.make(getView().findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        menu.findItem(navigation_item).setChecked(true);
    }

}
