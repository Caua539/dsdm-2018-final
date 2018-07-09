package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class BaseActivity extends AppCompatActivity {

    MaterialDialog dialog;

    public String getStringFromEdit(int id){
        EditText input = (EditText) findViewById(id);
        return input.getText().toString();
    }

    public void showAlert(String message){
        Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).show();
    }

    public void initToolbar(int id, String name){
        Toolbar toolbar = (Toolbar) findViewById(id);
        toolbar.setTitle(name);
        toolbar.setTitleMarginStart(120);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.transportbusicon);
    }

    public void showFixedBottom(String message){
        Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_INDEFINITE).show();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setStringFromEdit(int id, String text){
        EditText input = (EditText) findViewById(id);
        input.setText(text);
    }

    public void showDialogWithMessage(String message){
        dialog = new MaterialDialog.Builder(this)
                .content(message)
                .progress(true,0)
                .show();
    }

    public void dismissDialog(){
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
