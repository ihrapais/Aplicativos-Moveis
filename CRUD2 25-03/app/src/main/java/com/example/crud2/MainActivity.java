import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private EditText nome, cpf, telefone;
    private ImageView imageView;
    private Aluno aluno = null;
    private AlunoDao dao;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.nome);
        cpf = findViewById(R.id.cpf);
        telefone = findViewById(R.id.telefone);
        imageView = findViewById(R.id.imageView);
        dao = new AlunoDao(this);

        // Configuração do cameraLauncher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap imagemCorrigida = corrigirOrientacao(imageBitmap);
                imageView.setImageBitmap(imagemCorrigida);
            }
        });

        // Carregar dados do aluno para atualização
        Intent it = getIntent();
        if (it.hasExtra("aluno")) {
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome());
            cpf.setText(aluno.getCpf());
            telefone.setText(aluno.getTelefone());
            if (aluno.getFotoBytes() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(aluno.getFotoBytes(), 0, aluno.getFotoBytes().length);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void tirarFoto(View view) {
        checkCameraPermissionAndStart();
    }

    private void checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissão da câmera negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap corrigirOrientacao(Bitmap bitmap) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public void salvar(View view) {
        String nomeDigitado = nome.getText().toString().trim();
        String cpfDigitado = cpf.getText().toString().trim();
        String telefoneDigitado = telefone.getText().toString().trim();

        if (nomeDigitado.isEmpty() || cpfDigitado.isEmpty() || telefoneDigitado.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dao.validaCpf(cpfDigitado)) {
            Toast.makeText(this, "CPF inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dao.validaTelefone(telefoneDigitado)) {
            Toast.makeText(this, "Telefone inválido! Use (XX) 9XXXX-XXXX", Toast.LENGTH_SHORT).show();
            return;
        }

        if (aluno == null || !cpfDigitado.equals(aluno.getCpf())) {
            if (dao.cpfExistente(cpfDigitado)) {
                Toast.makeText(this, "CPF duplicado!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (aluno == null) {
            aluno = new Aluno();
        }

        aluno.setNome(nomeDigitado);
        aluno.setCpf(cpfDigitado);
        aluno.setTelefone(telefoneDigitado);

        if (imageView.getDrawable() != null) {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] fotoBytes = stream.toByteArray();
            aluno.setFotoBytes(fotoBytes);
        }

        if (aluno.getId() == null) {
            long id = dao.inserir(aluno);
            Toast.makeText(this, "Aluno inserido com ID: " + id, Toast.LENGTH_SHORT).show();
        } else {
            dao.atualizar(aluno);
            Toast.makeText(this, "Aluno atualizado!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}