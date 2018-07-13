package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.view.CardForm;

import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.util.Random;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EncUtil;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseActivity;

public class NewCardActivity extends BaseActivity implements OnCardFormSubmitListener{


    protected CardForm mCardForm;
    private Button gravar;
    private EditText nomeedit;
    private TheDatabase busdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        initToolbar(R.id.toolbar2, "Adicionar Novo Cartão");


        mCardForm = findViewById(R.id.card_form);
        mCardForm.cardRequired(true)
                .maskCardNumber(true)
                .maskCvv(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .actionLabel(getString(R.string.GravarCartao))
                .setup(this);
        mCardForm.setOnCardFormSubmitListener(this);

        busdb = createDb(getApplicationContext());

        nomeedit = (EditText) findViewById(R.id.CartaoNomeEditText);
        gravar = (Button) findViewById(R.id.buttonGravar);
        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardFormSubmit();
            }
        });

    }

    @Override
    public void onCardFormSubmit() {
        if (mCardForm.isValid()) {
            String cardN = EncUtil.encryptString(mCardForm.getCardNumber());
            String aux = mCardForm.getCardNumber();
            String end = aux.substring(aux.length() - 4);
            String cvv = EncUtil.encryptString(mCardForm.getCvv());
            String validade = EncUtil.encryptString(""+mCardForm.getExpirationMonth()+"/"+mCardForm.getExpirationYear());
            String nome = nomeedit.getText().toString();

            new CartaoAsyncTask(this, busdb, cardN, nome, end, cvv, validade).execute();

        } else {
            mCardForm.validate();
            Toast.makeText(this, R.string.CartaoInvalido, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initToolbar(int id, String message){
        super.initToolbar(id, message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public TheDatabase createDb(Context context){
        return (TheDatabase) TheDatabase.getDatabase(context);
    }


    private static class CartaoAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;
        private final TheDatabase db;
        private  String cardN;
        private String nome;
        private String end;
        private String cvv;
        private String validade;
        Random secure = new SecureRandom();

        public CartaoAsyncTask(Activity activity, TheDatabase busdb, String cardN, String nome, String end, String cvv, String validade) {
            weakActivity = new WeakReference<>(activity);
            this.db = busdb;
            this.cardN = cardN;
            this.nome = nome;
            this.end = end;
            this.cvv = cvv;
            this.validade = validade;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            if (db.cartaoDAO().getCartaoByNumero(cardN) == null){
                int id;
                do{
                    id = secure.nextInt(9997)+1;
                }
                while(db.cartaoDAO().getCartaoByCodigo(id) != null);

                Cartao cartao = new Cartao(id, cardN, nome, end, cvv, validade);
                db.cartaoDAO().insertCartao(cartao);
                return 0;
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }

            if (result > 0) {
                Toast.makeText(activity, "Cartao já cadastrado!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "Cartão gravado para compras!", Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }




}
