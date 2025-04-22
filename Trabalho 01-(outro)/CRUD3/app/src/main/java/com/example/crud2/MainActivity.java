package com.example.crud2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNome, editTextCpf, editTextTelefone;
    private Button btnSalvar, btnListar, btnTirarFoto;
    private ImageView imageViewFoto;
    private AlunoDao alunoDao;
    private Aluno alunoParaAtualizar = null;
    private Bitmap fotoBitmap = null;

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        imageViewFoto = findViewById(R.id.imageViewFoto);
        btnTirarFoto = findViewById(R.id.btnTirarFoto);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnListar = findViewById(R.id.btnListar);

        alunoDao = new AlunoDao(this);

        Intent intent = getIntent();
        if (intent.hasExtra("aluno")) {
            alunoParaAtualizar = (Aluno) intent.getSerializableExtra("aluno");
            if (alunoParaAtualizar != null) {
                editTextNome.setText(alunoParaAtualizar.getNome());
                editTextCpf.setText(alunoParaAtualizar.getCpf());
                editTextTelefone.setText(alunoParaAtualizar.getTelefone());
                byte[] fotoBytes = alunoParaAtualizar.getFoto();
                if (fotoBytes != null && fotoBytes.length > 0) {
                    fotoBitmap = Aluno.converterBytesParaBitmap(fotoBytes);
                    imageViewFoto.setImageBitmap(fotoBitmap);
                }
                setTitle("Atualizar Aluno");
            }
        } else {
            setTitle("Cadastrar Aluno");
        }

        btnTirarFoto.setOnClickListener(v -> tirarFoto());
        btnSalvar.setOnClickListener(v -> salvar());
        btnListar.setOnClickListener(v -> {
            Intent intentListar = new Intent(this, ListarAlunosActivity.class);
            startActivity(intentListar);
        });
    }

    private void tirarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            abrirCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamera();
            } else {
                Toast.makeText(this, "Permissão de Câmera necessária!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void abrirCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Nenhum app de câmera encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                fotoBitmap = (Bitmap) extras.get("data");
                imageViewFoto.setImageBitmap(fotoBitmap);
            } else {
                Toast.makeText(this, "Erro ao obter imagem da câmera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void salvar() {
        String nome = editTextNome.getText().toString().trim();
        String cpf = editTextCpf.getText().toString().trim();
        String telefone = editTextTelefone.getText().toString().trim();

        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!alunoDao.validaCpf(cpf)) {
            Toast.makeText(this, "CPF inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] fotoBytes = null;
        if (fotoBitmap != null) {
            fotoBytes = Aluno.converterBitmapParaBytes(fotoBitmap);
        }

        if (alunoParaAtualizar == null) {
            Aluno novoAluno = new Aluno();
            novoAluno.setNome(nome);
            novoAluno.setCpf(cpf);
            novoAluno.setTelefone(telefone);
            novoAluno.setFoto(fotoBytes);
            long id = alunoDao.inserir(novoAluno);
            if (id != -1) {
                Toast.makeText(this, "Aluno cadastrado com ID: " + id, Toast.LENGTH_SHORT).show();
                limparCampos();
            } else {
                Toast.makeText(this, "Erro ao cadastrar aluno!", Toast.LENGTH_SHORT).show();
            }
        } else {
            alunoParaAtualizar.setNome(nome);
            alunoParaAtualizar.setCpf(cpf);
            alunoParaAtualizar.setTelefone(telefone);
            alunoParaAtualizar.setFoto(fotoBytes);
            alunoDao.atualizar(alunoParaAtualizar);
            Toast.makeText(this, "Aluno atualizado!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void limparCampos() {
        editTextNome.setText("");
        editTextCpf.setText("");
        editTextTelefone.setText("");
        imageViewFoto.setImageResource(R.drawable.ic_avatar_placeholder);
        fotoBitmap = null;
        editTextNome.requestFocus();
    }
}