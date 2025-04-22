import java.io.Serializable;

public class Aluno implements Serializable {
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private byte[] fotoBytes; // Para armazenar a foto como array de bytes

    // Construtores
    public Aluno() {}

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public byte[] getFotoBytes() { return fotoBytes; }
    public void setFotoBytes(byte[] fotoBytes) { this.fotoBytes = fotoBytes; }

    @Override
    public String toString() {
        return nome; // Para exibir na ListView
    }
}