package com.example.domingas.teste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.domingas.teste.Modelo.membro;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CadastroMembro extends AppCompatActivity {
    EditText edtNome, edtEmail, edtSenha, edtEstado, edtMorada, edtNatural;
    ListView listV_dadosMembro;

    //private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<membro> listMembro = new ArrayList<membro>();
    private ArrayAdapter<membro> arrayAdapterMembro;
   // membro m = new membro();

    membro membroSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_membro);
        edtNome = (EditText) findViewById(R.id.editNome);
        edtEmail = (EditText) findViewById(R.id.editEmail);
        edtSenha = (EditText) findViewById(R.id.editSenha);
        edtEstado = (EditText) findViewById(R.id.editEstado);
        edtNatural = (EditText) findViewById(R.id.editNatural);
        edtMorada = (EditText) findViewById(R.id.editMorada);
        listV_dadosMembro = (ListView) findViewById(R.id.novo);

        inicializarFirebase();
        eventtoDatabase();


        listV_dadosMembro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                membroSelecionada = (membro) parent.getItemAtPosition(position);
                edtNome.setText(membroSelecionada.getNome());
                edtSenha.setText(membroSelecionada.getSenha(edtSenha.getText().toString()));
                edtEmail.setText(membroSelecionada.getEmail());
                edtEstado.setText(membroSelecionada.getEstado(edtEstado.getText().toString()));
                edtMorada.setText(membroSelecionada.getMorada(edtMorada.getText().toString()));
                edtNatural.setText(membroSelecionada.getNatural(edtNatural.getText().toString()));
            }

        });

    }
/*
    private void criarUser(String email,String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(CadastroMembro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            alert("Usuário cadastrado com Sucesso");
                            Intent i = new Intent(getApplicationContext(), BoasVidas.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            alert("Erro de Cadastro");
                        }
                    }
                });
    }
*/
    private void alert (String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }


    private void eventtoDatabase() {

        databaseReference.child("Membro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMembro.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    membro membro  = objSnapshot.getValue(membro.class);
                    listMembro.add(membro);
                }
                arrayAdapterMembro = new ArrayAdapter<membro>(CadastroMembro.this,android.R.layout.simple_list_item_1,listMembro);
                listV_dadosMembro.setAdapter(arrayAdapterMembro);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastroMembro.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseDatabase.setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_novo) {
            membro membro = new membro();
            membro.setUID(UUID.randomUUID().toString());
            membro.setNome(edtNome.getText().toString());
            membro.setEmail(edtEmail.getText().toString());
            membro.getSenha(edtSenha.getText().toString());
            membro.getMorada(edtMorada.getText().toString());
            membro.getEstado(edtEstado.getText().toString());
            membro.getNatural(edtNatural.getText().toString());

            databaseReference.child("Membro").child(membro.getUID()).setValue(membro);
            alert("Usuário cadastrado com Sucesso");
            limparcampos();
        }else if(id == R.id.menu_actualizar){
            membro membro = new membro();
            membro.setUID(membroSelecionada.getUID());
            membro.setNome(edtNome.getText().toString().trim());
            membro.setEmail(edtEmail.getText().toString().trim());
            membro.getEstado(edtEstado.getText().toString().trim());
            databaseReference.child("Membro").child(membro.getUID()).setValue(membro);
            limparcampos();
        }else if(id == R.id.menu_delete){

            membro membro = new membro();
            membro.setUID(membroSelecionada.getUID());
            databaseReference.child("Membro").child(membro.getUID()).removeValue();
            limparcampos();

        }

        return true;
    }

    private void limparcampos() {
        edtEmail.setText("");
        edtNome.setText("");
        edtSenha.setText("");
        edtEstado.setText("");
        edtMorada.setText("");
        edtNatural.setText("");

    }
}
