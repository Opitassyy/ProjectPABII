package com.example.mk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mk.client.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateContact extends AppCompatActivity {
    Button btn_submit;
    EditText et_nama,et_nomor,et_jk,et_alamat,et_deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        et_nama = findViewById(R.id.et_nama);
        et_nomor = findViewById(R.id.et_nomor);
        et_jk = findViewById(R.id.et_jk);
        et_alamat = findViewById(R.id.et_alamat);
        et_deskripsi = findViewById(R.id.et_deskripsi);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String nomor = et_nomor.getText().toString();
                String jk = et_jk.getText().toString();
                String alamat = et_alamat.getText().toString();
                String deskripsi = et_deskripsi.getText().toString();

                Contact contact = new Contact(nama,nomor,jk,alamat,deskripsi);
                postData(contact);
            }
        });
    }

    private void postData(Contact contact){
        Utility.getClient().create(APIService.class).postContact(contact).enqueue(new Callback<ValueData>() {
            @Override
            public void onResponse(Call<ValueData> call, Response<ValueData> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(CreateContact.this,"Data berhasil dikirim, tapi gagal ditambahkan", Toast.LENGTH_SHORT);
                }
                Toast.makeText(CreateContact.this, "Kontak berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ValueData> call, Throwable t) {
                    Toast.makeText(CreateContact.this,"Requets timeout (Connection error)", Toast.LENGTH_SHORT);
            }
        });
    }
}