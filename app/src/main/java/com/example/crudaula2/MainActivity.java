package com.example.crudaula2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editNome;
    private EditText editCpf;
    private EditText editTelefone;
    private Button buttonSalvar;
    private Button buttonListar;
    private ImageView imageView; // Novo campo para ImageView
    private AlunoDao dao;
    private Aluno aluno = null;
    private static final int REQUEST_CAMERA_PERMISSION = 200; // Constante para permissão
    private ActivityResultLauncher<Intent> cameraLauncher; // Launcher para câmera

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Carregando layout R.layout.activity_main");
        try {
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "MainActivity iniciada", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: Iniciando MainActivity");

            // Inicializar componentes
            editNome = findViewById(R.id.editNome);
            editCpf = findViewById(R.id.editCpf);
            editTelefone = findViewById(R.id.editTelefone);
            buttonSalvar = findViewById(R.id.buttonSalvar);
            buttonListar = findViewById(R.id.buttonListar);
            imageView = findViewById(R.id.imageView); // Vincular ImageView
            Button btnTakePhoto = findViewById(R.id.btnTakePhoto); // Vincular botão Tirar Foto

            // Verificar componentes
            if (editNome == null) {
                Log.e(TAG, "onCreate: editNome é nulo");
                Toast.makeText(this, "Erro: Campo Nome não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (editCpf == null) {
                Log.e(TAG, "onCreate: editCpf é nulo");
                Toast.makeText(this, "Erro: Campo CPF não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (editTelefone == null) {
                Log.e(TAG, "onCreate: editTelefone é nulo");
                Toast.makeText(this, "Erro: Campo Telefone não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (buttonSalvar == null) {
                Log.e(TAG, "onCreate: buttonSalvar é nulo");
                Toast.makeText(this, "Erro: Botão Salvar não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (buttonListar == null) {
                Log.e(TAG, "onCreate: buttonListar é nulo");
                Toast.makeText(this, "Erro: Botão Listar não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (imageView == null) {
                Log.e(TAG, "onCreate: imageView é nulo");
                Toast.makeText(this, "Erro: ImageView não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (btnTakePhoto == null) {
                Log.e(TAG, "onCreate: btnTakePhoto é nulo");
                Toast.makeText(this, "Erro: Botão Tirar Foto não encontrado", Toast.LENGTH_LONG).show();
                return;
            }

            // Inicializar DAO
            dao = new AlunoDao(this);
            if (dao == null) {
                Log.e(TAG, "onCreate: AlunoDao é nulo");
                Toast.makeText(this, "Erro: Não foi possível inicializar o banco de dados", Toast.LENGTH_LONG).show();
                return;
            }
            Log.d(TAG, "onCreate: AlunoDao inicializado com sucesso");

            // Verificar modo (cadastro ou atualização)
            Intent it = getIntent();
            if (it != null && it.hasExtra("aluno")) {
                aluno = (Aluno) it.getSerializableExtra("aluno");
                if (aluno != null) {
                    Log.d(TAG, "onCreate: Aluno recebido para atualização: ID=" + aluno.getId() + ", Nome=" + aluno.getNome() + ", CPF=" + aluno.getCpf() + ", Telefone=" + aluno.getTelefone());
                    editNome.setText(aluno.getNome());
                    editCpf.setText(aluno.getCpf());
                    editTelefone.setText(aluno.getTelefone());
                    // Carregar foto se existir (não implementado no PDF, mas pode ser adicionado)
                } else {
                    Log.e(TAG, "onCreate: Aluno recebido é nulo");
                    Toast.makeText(this, "Erro: Aluno não recebido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "onCreate: Modo cadastro");
            }

            // Configurar cameraLauncher
            cameraLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Log.d(TAG, "cameraLauncher: Foto capturada com sucesso");
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            Bitmap imagemCorrigida = corrigirOrientacao(imageBitmap);
                            imageView.setImageBitmap(imagemCorrigida);
                            Toast.makeText(this, "Foto capturada", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "cameraLauncher: Captura de foto cancelada ou falhou");
                        }
                    });

            // Configurar clique do botão Salvar
            buttonSalvar.setOnClickListener(v -> {
                Log.d(TAG, "buttonSalvar: Clique detectado");
                Toast.makeText(this, "Botão Salvar clicado", Toast.LENGTH_SHORT).show();
                salvar(v);
            });

            // Configurar clique do botão Listar
            buttonListar.setOnClickListener(v -> {
                Log.d(TAG, "buttonListar: Abrindo ListarAlunosActivity");
                Intent intent = new Intent(this, ListarAlunosActivity.class);
                startActivity(intent);
            });

            Log.d(TAG, "onCreate: Inicialização concluída com sucesso");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Erro durante inicialização: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao iniciar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void tirarFoto(View view) {
        Log.d(TAG, "tirarFoto: Iniciando processo de captura de foto");
        checkCameraPermissionAndStart();
    }

    private void checkCameraPermissionAndStart() {
        Log.d(TAG, "checkCameraPermissionAndStart: Verificando permissão da câmera");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkCameraPermissionAndStart: Solicitando permissão da câmera");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            Log.d(TAG, "checkCameraPermissionAndStart: Permissão concedida, iniciando câmera");
            startCamera();
        }
    }

    private void startCamera() {
        Log.d(TAG, "startCamera: Iniciando câmera");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
            Log.d(TAG, "startCamera: Intent de câmera lançada");
        } else {
            Log.e(TAG, "startCamera: Nenhuma atividade para lidar com a captura de imagem");
            Toast.makeText(this, "Nenhum aplicativo de câmera disponível", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: Processando resultado de permissão");
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Permissão da câmera concedida");
                startCamera();
            } else {
                Log.w(TAG, "onRequestPermissionsResult: Permissão da câmera negada");
                Toast.makeText(this, "A permissão da câmera é necessária para tirar fotos.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap corrigirOrientacao(Bitmap bitmap) {
        Log.d(TAG, "corrigirOrientacao: Corrigindo orientação da imagem");
        if (bitmap == null) {
            Log.w(TAG, "corrigirOrientacao: Bitmap é nulo");
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap correctedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Log.d(TAG, "corrigirOrientacao: Imagem corrigida");
        return correctedBitmap;
    }

    public void salvar(View view) {
        try {
            Log.d(TAG, "salvar: Iniciando salvamento");
            Toast.makeText(this, "Salvar chamado", Toast.LENGTH_SHORT).show();

            String nomeDigitado = editNome.getText().toString().trim();
            String cpfDigitado = editCpf.getText().toString().trim();
            String telefoneDigitado = editTelefone.getText().toString().trim();

            Log.d(TAG, "salvar: Dados digitados - Nome: " + nomeDigitado + ", CPF=" + cpfDigitado + ", Telefone=" + telefoneDigitado);

            if (nomeDigitado.isEmpty() || cpfDigitado.isEmpty() || telefoneDigitado.isEmpty()) {
                Log.w(TAG, "salvar: Campos vazios detectados");
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar CPF
            if (!dao.validaCpf(cpfDigitado)) {
                Log.w(TAG, "salvar: CPF inválido: " + cpfDigitado);
                Toast.makeText(this, "CPF inválido. Use 11 dígitos numéricos (ex.: 12345678901).", Toast.LENGTH_SHORT).show();
                return;
            }

            if (aluno == null) {
                // Verificar CPF duplicado para novos cadastros
                if (dao.cpfExistente(cpfDigitado)) {
                    Log.w(TAG, "salvar: CPF duplicado: " + cpfDigitado);
                    Toast.makeText(this, "CPF já cadastrado. Insira um CPF diferente.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Aluno novoAluno = new Aluno();
                novoAluno.setNome(nomeDigitado);
                novoAluno.setCpf(cpfDigitado);
                novoAluno.setTelefone(telefoneDigitado);

                // Adicionar foto, se disponível
                if (imageView.getDrawable() != null) {
                    Log.d(TAG, "salvar: Convertendo imagem para bytes");
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] fotoBytes = stream.toByteArray();
                    novoAluno.setFotoBytes(fotoBytes);
                    Log.d(TAG, "salvar: Foto convertida para bytes, tamanho: " + fotoBytes.length);
                }

                long id = dao.inserir(novoAluno);
                if (id != -1) {
                    Log.d(TAG, "salvar: Aluno inserido com id: " + id);
                    Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
                    // Limpar campos após salvar
                    editNome.setText("");
                    editCpf.setText("");
                    editTelefone.setText("");
                    imageView.setImageDrawable(null); // Limpar ImageView
                } else {
                    Log.e(TAG, "salvar: Erro ao inserir aluno");
                    Toast.makeText(this, "Erro ao inserir aluno.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                // Verificar CPF duplicado para atualizações (exceto se for o mesmo CPF do aluno atual)
                if (!cpfDigitado.equals(aluno.getCpf()) && dao.cpfExistente(cpfDigitado)) {
                    Log.w(TAG, "salvar: CPF duplicado: " + cpfDigitado);
                    Toast.makeText(this, "CPF já cadastrado. Insira um CPF diferente.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "salvar: Atualizando aluno com ID: " + aluno.getId());
                aluno.setNome(nomeDigitado);
                aluno.setCpf(cpfDigitado);
                aluno.setTelefone(telefoneDigitado);

                // Adicionar foto, se disponível
                if (imageView.getDrawable() != null) {
                    Log.d(TAG, "salvar: Convertendo imagem para bytes");
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] fotoBytes = stream.toByteArray();
                    aluno.setFotoBytes(fotoBytes);
                    Log.d(TAG, "salvar: Foto convertida para bytes, tamanho: " + fotoBytes.length);
                } else {
                    aluno.setFotoBytes(null); // Limpar foto se não houver imagem
                }

                dao.atualizar(aluno);
                Log.d(TAG, "salvar: Aluno atualizado com sucesso");
                Toast.makeText(this, "Aluno atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                // Limpar campos após atualizar
                editNome.setText("");
                editCpf.setText("");
                editTelefone.setText("");
                imageView.setImageDrawable(null); // Limpar ImageView
                aluno = null; // Resetar para modo cadastro
            }
            // Removido finish() para manter MainActivity na pilha
        } catch (Exception e) {
            Log.e(TAG, "salvar: Erro ao salvar/atualizar: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}