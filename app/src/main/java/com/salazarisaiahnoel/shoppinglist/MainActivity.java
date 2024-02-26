package com.salazarisaiahnoel.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.salazarisaiahnoel.shoppinglist.adapters.ShoppingListAdapter;
import com.salazarisaiahnoel.shoppinglist.data.ShoppingListData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ShoppingListAdapter.OnItemClickListener, ShoppingListAdapter.OnItemLongClickListener{

    RecyclerView rv;
    LinearLayoutManager llm;
    ShoppingListAdapter sla;

    ShoppingListData sd;
    List<String> name = new ArrayList<>();
    List<String> number = new ArrayList<>();
    List<String> price = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sd = new ShoppingListData(this);

        rv = findViewById(R.id.recycler_view_shpl);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        refreshData();
    }

    void refreshData(){
        name = sd.getName();
        number = sd.getNumber();
        price = sd.getPrice();

        BigInteger totalAmount = BigInteger.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int a = 0; a < name.size(); a++){
            totalAmount = totalAmount.add(new BigInteger(number.get(a)));
            totalPrice = totalPrice.add(new BigDecimal(price.get(a)).multiply(new BigDecimal(number.get(a))));
        }

        name.add(0, "Name");
        number.add(0, "Number");
        price.add(0, "Price");

        name.add("Total");
        number.add(totalAmount.toString());
        price.add(totalPrice.toString());

        sla = new ShoppingListAdapter(this, name, number, price, this, this);
        rv.setAdapter(sla);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shpl_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.shpl_add){
            LayoutInflater li = LayoutInflater.from(this);
            View v = li.inflate(R.layout.shpl_add_data, null);
            final EditText et = v.findViewById(R.id.edit_text_shpl_add_name);
            final EditText et1 = v.findViewById(R.id.edit_text_shpl_add_number);
            final EditText et2 = v.findViewById(R.id.edit_text_shpl_add_price);
            final Button b = v.findViewById(R.id.done_shpl_add);
            final Button b1 = v.findViewById(R.id.cancel_shpl_add);
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(v);
            AlertDialog ad = adb.create();
            Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b.setOnClickListener(v1 -> {
                if (TextUtils.isEmpty(et.getText().toString()) || TextUtils.isEmpty(et1.getText().toString()) || TextUtils.isEmpty(et2.getText().toString())){
                    if (TextUtils.isEmpty(et.getText().toString())){
                        et.setError("Cannot be empty.");
                    }
                    if (TextUtils.isEmpty(et1.getText().toString())){
                        et1.setError("Cannot be empty.");
                    }
                    if (TextUtils.isEmpty(et2.getText().toString())){
                        et2.setError("Cannot be empty.");
                    }
                } else {
                    sd.addData(et.getText().toString(), et1.getText().toString(), et2.getText().toString());
                    sd.rearrangePosition();
                    refreshData();
                    ad.cancel();
                }
            });
            b1.setOnClickListener(v2 -> ad.cancel());
        }
        if (item.getItemId() == R.id.shpl_clear){
            LayoutInflater li = LayoutInflater.from(this);
            View v = li.inflate(R.layout.shpl_clear_data, null);
            final Button b = v.findViewById(R.id.yes_shpl_clear);
            final Button b1 = v.findViewById(R.id.no_shpl_clear);
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(v);
            AlertDialog ad = adb.create();
            Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b.setOnClickListener(v1 -> {
                sd.clearData();
                refreshData();
                ad.cancel();
            });
            b1.setOnClickListener(v2 -> ad.cancel());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        if (position > 0 && position < name.size() - 1){
            LayoutInflater li = LayoutInflater.from(this);
            View v = li.inflate(R.layout.shpl_edit_data, null);
            final EditText et = v.findViewById(R.id.edit_text_shpl_edit_name);
            final EditText et1 = v.findViewById(R.id.edit_text_shpl_edit_number);
            final EditText et2 = v.findViewById(R.id.edit_text_shpl_edit_price);
            final Button b = v.findViewById(R.id.done_shpl_edit);
            final Button b1 = v.findViewById(R.id.cancel_shpl_edit);
            et.setText(name.get(position));
            et1.setText(number.get(position));
            et2.setText(price.get(position));
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(v);
            AlertDialog ad = adb.create();
            Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b.setOnClickListener(v1 -> {
                if (TextUtils.isEmpty(et.getText().toString()) || TextUtils.isEmpty(et1.getText().toString()) || TextUtils.isEmpty(et2.getText().toString())){
                    if (TextUtils.isEmpty(et.getText().toString())){
                        et.setError("Cannot be empty.");
                    }
                    if (TextUtils.isEmpty(et1.getText().toString())){
                        et1.setError("Cannot be empty.");
                    }
                    if (TextUtils.isEmpty(et2.getText().toString())){
                        et2.setError("Cannot be empty.");
                    }
                } else {
                    sd.deleteData(position - 1);
                    sd.addData(et.getText().toString(), et1.getText().toString(), et2.getText().toString());
                    sd.rearrangePosition();
                    refreshData();
                    ad.cancel();
                }
            });
            b1.setOnClickListener(v2 -> ad.cancel());
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if (position > 0 && position < name.size() - 1){
            LayoutInflater li = LayoutInflater.from(this);
            View v = li.inflate(R.layout.shpl_delete_data, null);
            final Button b = v.findViewById(R.id.yes_shpl_delete);
            final Button b1 = v.findViewById(R.id.no_shpl_delete);
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(v);
            AlertDialog ad = adb.create();
            Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b.setOnClickListener(v1 -> {
                sd.deleteData(position - 1);
                sd.rearrangePosition();
                refreshData();
                ad.cancel();
            });
            b1.setOnClickListener(v2 -> ad.cancel());
        }
    }
}