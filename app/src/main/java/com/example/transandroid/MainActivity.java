package com.example.transandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQ;
    private String TK ="1uegFMw4OBH5IgYt3SMNePVEG4tV4bN_ZMK5OYJtuJfdXH3hNNcbf2KLCglvKzb23G7iBIJ_e-vbH0WCS-6jmCnhTi-Kt5j3cSkQecVciWG8UpEGclClmWgG0vpJgpCpKnQnAZ248XFidAK0KT_gApSI_-wMW7QQZCeHFkH9oLZhonjboL9gXA1ST_mfUe-NH7ZhxeJJ6-CZ3XgEsA628T6JJJp5d86g6AyUK_cguAxQzhFQ3APbT-XEjhSjUrCDW0a8Z-32eCOdR78EwJAwCq7jUlK9MTrCdem5fZbo9nYSQTzkVy2qpIy4EbvmWnyq3bd7K8N4QgEwCHdPe9UuwOo21iA";
    private String URL = "https://pay.payphonetodoesposible.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        producto[0] = (TextView)findViewById(R.id.txtproducto1);
        producto[1] = (TextView)findViewById(R.id.txtproducto2);
        producto[2] = (TextView)findViewById(R.id.txtproducto3);

        valor[0]=(TextView)findViewById(R.id.txtValor);
        valor[1]=(TextView)findViewById(R.id.txtValor2);
        valor[2]=(TextView)findViewById(R.id.txtValor3);

        productos1 = new Productos("LAPTOP AAA \n"+
                "2.6 GHz Intel Core i7-10750H Six-Core \n"+
                "16GB DDR4 RAM | 512GB NVMe SSD \n"+
                "NVIDIA GeForce GTX 1660 Ti \n"+
                "1920 x 1080, 144 Hz IPS",1900.5);
        producto[0].setText(productos1.getInfo());
        valor[0].setText(String.valueOf(productos1.getValor()));

        productos2 = new Productos("LAPTOP BBB \n"+
                "2.4 GHz Intel Core i5 Quad-Core \n"+
                "16GB DDR4 RAM | 512GB NVMe SSD \n"+
                "Integrated Intel Iris Xe Graphics \n"+
                "15.6\" FHD 1920 x 1080 IPS Display\n",800.9);
        producto[1].setText(productos2.getInfo());
        valor[1].setText(String.valueOf(productos1.getValor()));

        productos3 = new Productos("LAPTOP CCC \n"+
                "2.5 GHz Intel Core i5-10300H \n"+
                "8GB DDR4 RAM | 256GB SSD + 1TB HDD \n"+
                "15.6\" 1920 x 1080 FHD IPS Display \n",500.5);
        producto[2].setText(productos2.getInfo());
        valor[2].setText(String.valueOf(productos2.getValor()));

    }
    Productos productos1 = new Productos();
    Productos productos2 = new Productos();
    Productos productos3 = new Productos();
    TextView[] producto = new TextView[3];
    TextView[] valor = new TextView[3];

    public void idTransaction(String stringResponse) throws JSONException {

        JSONObject obJson=new JSONObject(stringResponse);

    }

    public void compra1(View view)
    {
        iva=(int) (0.12*productos1.getValor());
        monto=productos1.getValor()*(0.12+100);
        monto_con_Iva=productos1.getValor()*100;
        Random rn=new Random();
        id_Cliente_Transaccion=rn.nextInt(10000);
        cobroEnvio();

        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);
    }

    public void compra2(View view)
    {
        iva=(int) (0.12*productos2.getValor());
        monto=productos2.getValor()*(0.12+100);
        monto_con_Iva=productos2.getValor()*100;
        Random rn=new Random();
        id_Cliente_Transaccion=rn.nextInt(10000);
        cobroEnvio();

        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);
    }

    public void compra3(View view)
    {
        iva=(int) (0.12*productos3.getValor());
        monto=productos3.getValor()*(0.12+100);
        monto_con_Iva=productos3.getValor()*100;
        Random rn=new Random();
        id_Cliente_Transaccion=rn.nextInt(10000);
        cobroEnvio();

        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);
    }

    public  void  cobroEnvio(){
    StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            URL+ "api/sale/",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        idTransaction(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("phoneNumber", numeroTelefono);
            params.put("countryCode", cod_Pais);
            params.put("clientUserId", cedula);
            params.put("reference", "none");
            params.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            params.put("amount",""+(int) monto+"");
            params.put("amountWithTax",""+(int) (monto+monto*0.12)+"");
            params.put("amountWithoutTax", ""+(int)monto_sin_Iva+"");
            params.put("tax",""+iva+"");
            params.put("clientTransactionId", ""+id_Cliente_Transaccion+"");
            return params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<String, String>();

            headers.put("Authorization", TK);
            return headers;

        }
    };
    requestQ = Volley.newRequestQueue(this);
    requestQ.add(stringRequest);
}


    String numeroTelefono="0994477216";
    String cod_Pais="593";
    String cedula="1207925767";
    double monto;
    double monto_con_Iva;
    double monto_sin_Iva=0;
    int iva;
    int id_Cliente_Transaccion;
}