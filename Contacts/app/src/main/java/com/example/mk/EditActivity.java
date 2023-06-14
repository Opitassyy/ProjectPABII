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

public class EditActivity extends AppCompatActivity {
    Contact contact;
    EditText  et_nama,et_nomor,et_jk,et_alamat,et_deskripsi;
    Button btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        contact = getIntent().getParcelableExtra("EXTRA_DATA");
        et_nama = findViewById(R.id.et_nama_edt);
        et_nomor = findViewById(R.id.et_nomor_edt);
        et_jk = findViewById(R.id.et_jk_edt);
        et_alamat = findViewById(R.id.et_alamat_edt);
        et_deskripsi = findViewById(R.id.et_deskripsi_edt);
        btn_edit = findViewById(R.id.btn_submit_edt);

        et_nama.setText(contact.getName());
        et_nomor.setText(contact.getPhone_number());
        et_jk.setText(contact.getGender());
        et_alamat.setText(contact.getAddress());
        et_deskripsi.setText(contact.getDescription());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String nomor = et_nomor.getText().toString();
                String jk = et_jk.getText().toString();
                String alamat = et_alamat.getText().toString();
                String deskripsi = et_deskripsi.getText().toString();

                Contact newContact = new Contact(nama,nomor,jk,alamat,deskripsi);
                updateContact(newContact);
            }
        });
    }

    private void updateContact(Contact newContact){
        Utility.getClient().create(APIService.class).putContact(contact.get_id(),newContact).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(EditActivity.this, "Update terkirim tapi gagal dieksekusi", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditActivity.this, "Update berhasil.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Request timeout (Connection error)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}